package cn.bolin.web.servlet;

import cn.bolin.domain.Admin;

import cn.bolin.domain.Reader;

import cn.bolin.service.LoginService;

import cn.bolin.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Create By Bolin on ${DATA}
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
     * 登录 post 方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取页面传递的参数 用户名/密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type"); // 读者?管理员?

        // 1.创建LoginServiceImpl对象
        LoginService loginService = new LoginServiceImpl();

        // 多态的应用,根据type判断角色登录,用Object代替了admin/user,多角色用这种方法更好!
        Object object = loginService.login(username, password, type);
        if (object != null) {
            HttpSession session = request.getSession();
            switch (type) {
                case "reader":
                    Reader reader = (Reader) object;
                    session.setAttribute("reader", reader);
                    response.sendRedirect("/book?page=1");
                    // 跳转到读者页

                    break;
                case "admin":
                    Admin admin = (Admin) object;
                    session.setAttribute("admin", admin);
                    // Model
                    response.sendRedirect("/admin?method=findAllBorrow&page=1");
                    break;
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}

