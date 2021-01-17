package cn.bolin.service;

import cn.bolin.domain.Book;
import cn.bolin.domain.Borrow;

import java.util.List;

/**
 * Create By Bolin on 12.25
 */
public interface BookService {
    // 查询书籍列表
    List<Book> findAll(int page);

    // 获取书籍总数
    int getCount();

    // 借阅
    public void addBorrow(Integer bookid,Integer readerid);

    // 返回所有借书记录
    public List <Borrow> findAllBorrowByReaderId(Integer id,Integer page);

    // 展示给管理员得借阅列表
    public List<Borrow> findAllBorrowByState(Integer state,Integer page);

    // 获取借阅书籍总数量
    int getBorrowPages(Integer readerid);

    // 获取admin待处理借阅书籍总数量
    int getBorrowPagesByState(Integer state);

    void handleBorrow(Integer borrowId, Integer state, Integer adminId);
}
