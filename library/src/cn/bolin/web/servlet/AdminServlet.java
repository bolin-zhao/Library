package cn.bolin.web.servlet;

import cn.bolin.domain.Admin;
import cn.bolin.domain.Borrow;
import cn.bolin.service.BookService;
import cn.bolin.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Create By Bolin on
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    // 2.创建对象bookServiceImpl对象
    BookService bookService = new BookServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method==null){
            method = "findAllBorrow";
        }
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        switch (method){
            case "findAllBorrow":
                String page = req.getParameter("page");
                List<Borrow> borrowList = bookService.findAllBorrowByState(0, Integer.parseInt(page));
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);// 每页6条
                req.setAttribute("currentPage", page);// 当前显示第一页
                req.setAttribute("pages",bookService.getBorrowPagesByState(0)); // 获取总页数

                // 跳转到管理员页面
                req.getRequestDispatcher("admin.jsp").forward(req, resp);
                break;
            case "handle":
                String id = req.getParameter("id");  // borrowId
                String state = req.getParameter("state");
                bookService.handleBorrow(Integer.parseInt(id),Integer.parseInt(state),admin.getId());
                // 处理完同意/拒绝后,再跳转到当前页,实现刷新页面
                if (Integer.parseInt(state)==1 || Integer.parseInt(state)==2){
                    resp.sendRedirect("/admin?page=1");
                }else{
                    resp.sendRedirect("/admin?method=getBorrowed&page=1");
                }
                break;
                // 还书,查询所有state=1的数据
            case "getBorrowed":
                page = req.getParameter("page");
                borrowList = bookService.findAllBorrowByState(1, Integer.parseInt(page));
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);// 每页6条
                req.setAttribute("currentPage", page);// 当前显示第一页
                req.setAttribute("pages",bookService.getBorrowPagesByState(1)); // 获取总页数
                req.getRequestDispatcher("return.jsp").forward(req, resp);
                break;
        }

    }
}
