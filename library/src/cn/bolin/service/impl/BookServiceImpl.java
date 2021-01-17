package cn.bolin.service.impl;

import cn.bolin.dao.BookDao;
import cn.bolin.dao.BorrowDao;
import cn.bolin.dao.impl.BookDaoImpl;
import cn.bolin.dao.impl.BorrowDaoImpl;
import cn.bolin.domain.Book;

import cn.bolin.domain.Borrow;
import cn.bolin.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Create By Bolin on 12.25
 */
public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private final int LIMIT = 6;  // 每页显示6条,limit大写,(常量)
    private BorrowDao borrowDao = new BorrowDaoImpl();
    @Override
    public List<Book> findAll(int page) {
        int index = (page-1)*LIMIT;
        return bookDao.findAll(index,LIMIT);
    }

    @Override
    public int getCount() {
        int count = bookDao.getCount();
        int page = 0;
        if (count%LIMIT==0){
            page=count/LIMIT;
        }else {
            page=count/LIMIT+1;
        }
        return page;
    }

    // 借阅
    @Override
    public void addBorrow(Integer bookid, Integer readerid) {
        // 借书时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowTime = simpleDateFormat.format(date);

        // 还书时间
        Calendar calendar = Calendar.getInstance();
        int dates = calendar.get(Calendar.DAY_OF_YEAR) + 14;
        calendar.set(Calendar.DAY_OF_YEAR, dates); // 两周后自动归还
        Date date2 = calendar.getTime();
        String returnTime = simpleDateFormat.format(date2);

        borrowDao.insert(bookid,readerid,borrowTime,returnTime, null, 0);

    }

    @Override
    public List<Borrow> findAllBorrowByReaderId(Integer id,Integer page) {
        int index = (page-1)*LIMIT;
        return borrowDao.findAllByReaderId(id,index,LIMIT);
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state,Integer page) {
        int index = (page-1)*LIMIT;
        return borrowDao.findAllBorrowByState(state,index,LIMIT);
    }


    @Override
    public int getBorrowPages(Integer readerid) {
        int count = borrowDao.count(readerid);
        int page = 0;
        if (count%LIMIT==0){
            page=count/LIMIT;
        }else {
            page=count/LIMIT+1;
        }
        return page;
    }

    @Override
    public int getBorrowPagesByState(Integer state) {
        int count = borrowDao.countByState(state);
        int page = 0;
        if (count%LIMIT==0){
            page=count/LIMIT;
        }else {
            page=count/LIMIT+1;
        }
        return page;
    }

    @Override
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId) {
        borrowDao.handleBorrow(borrowId,state,adminId);
    }
}
