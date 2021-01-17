package cn.bolin.web.servlet;

import cn.bolin.domain.Book;
import cn.bolin.domain.Borrow;
import cn.bolin.domain.Reader;
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
@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        // 为了防止空指针,默认方法是findAll
        if (method == null){
            method = "findAll";
        }
        // 从session中取到reader中的值
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("reader");

        switch (method){
            case "findAll":
                String pageStr = req.getParameter("page");
                Integer page = Integer.valueOf(pageStr);
                System.out.println(page);
                List<Book> list = bookService.findAll(page);
                req.setAttribute("list", list);
                req.setAttribute("dataPrePage", 6);// 每页6条
                req.setAttribute("currentPage", page);// 当前显示第一页
                req.setAttribute("pages",bookService.getCount()); // 获取总页数
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            case "addBorrow":
                System.out.println("执行了");
                String bookidStr = req.getParameter("bookid");
                Integer bookid = Integer.parseInt(bookidStr);
                // 添加借书请求
                bookService.addBorrow(bookid, reader.getId());
                resp.sendRedirect("/book?method=findAllBorrow&page=1");
                break;
            case "findAllBorrow":
                pageStr = req.getParameter("page");
                page = Integer.parseInt(pageStr);
                List<Borrow> borrowList = bookService.findAllBorrowByReaderId(reader.getId(),page);
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);// 每页6条
                req.setAttribute("currentPage", page);// 当前显示第一页
                req.setAttribute("pages",bookService.getBorrowPages(reader.getId())); // 获取总页数
                req.getRequestDispatcher("borrow.jsp").forward(req, resp);
                break;
        }
    }
}
