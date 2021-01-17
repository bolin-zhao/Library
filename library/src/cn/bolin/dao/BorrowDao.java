package cn.bolin.dao;


import cn.bolin.domain.Borrow;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface BorrowDao {
    // 插入借书记录
    public void insert(Integer bookid, Integer readerid, String borrowtime,String returntime,Integer adminid,Integer state);

    // 查询借书记录
    public List<Borrow> findAllByReaderId( Integer id,Integer index,Integer limit);

    public List<Borrow> findAllBorrowByState(Integer state,Integer index,Integer limit);

    int count(Integer readerid);
    int countByState(Integer state);

    void handleBorrow(Integer borrowId, Integer state, Integer adminId);
}
