package cn.bolin.dao.impl;

import cn.bolin.dao.BorrowDao;
import cn.bolin.domain.Book;
import cn.bolin.domain.Borrow;
import cn.bolin.domain.Reader;
import cn.bolin.utils.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Bolin on
 */
public class BorrowDaoImpl implements BorrowDao {
    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into borrow (bookid,readerid,borrowtime,returntime,state)values(?,?,?,?,0)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bookid);
            ps.setInt(2, readerid);
            ps.setString(3, borrowtime);
            ps.setString(4, returntime);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection, ps, null);
        }
    }

    @Override
    public List<Borrow> findAllByReaderId(Integer id,Integer index,Integer limit) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select br.id,b.name,b.author,b.publish,br.borrowtime,br.returntime,r.name,r.tel,r.cardid,br.state from borrow br,book b,reader r where readerid=? and b.id=br.bookid and r.id=br.readerid limit ?,?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, index);
            ps.setInt(3, limit);
            rs = ps.executeQuery();
            while (rs.next()){
                // 取出所有的参数
                /*int borrowId = rs.getInt(1);
                String bookName = rs.getString(2);
                String author = rs.getString(3);
                String publish = rs.getString(4);
                String borrowTime = rs.getString(5);
                String returnTime = rs.getString(6);
                String readerName = rs.getString(7);
                String tel = rs.getString(8);
                String cardId = rs.getString(9);
                int state = rs.getInt(10);
                // 封装
                Book book = new Book(bookName, author, publish);
                Reader reader = new Reader(readerName, tel, cardId);
                Borrow borrow = new Borrow(borrowId, book, reader, borrowTime, returnTime, state);
                list.add(borrow);
                需要开辟很多的空间,浪费内存,需要优化
                */
                Borrow borrow = new Borrow(rs.getInt(1),
                        new Book(rs.getString(2), rs.getString(3), rs.getString(4)),
                        new Reader(rs.getString(7), rs.getString(8), rs.getString(9)),
                        rs.getString(5), rs.getString(6),rs.getInt(10));
                list.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection, ps, rs);
        }
        return list;
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state,Integer index,Integer limit) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select br.id,b.name,b.author,b.publish,br.borrowtime,br.returntime,r.name,r.tel,r.cardid,br.state from borrow br,book b,reader r where state=? and b.id=br.bookid and r.id=br.readerid limit ?,?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, index);
            ps.setInt(3, limit);
            rs = ps.executeQuery();
            while (rs.next()){
                // 取出所有的参数
                Borrow borrow = new Borrow(rs.getInt(1),
                        new Book(rs.getString(2), rs.getString(3), rs.getString(4)),
                        new Reader(rs.getString(7), rs.getString(8), rs.getString(9)),
                        rs.getString(5), rs.getString(6),rs.getInt(10));
                list.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection, ps, rs);
        }
        return list;
    }


    // 获取借阅列表数量
    @Override
    public int count(Integer readerid) {
        Connection connection = JDBCUtils.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "select count(*) from borrow br,book b,reader r where readerid=? and b.id=br.bookid and r.id=br.readerid ";
        int count = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, readerid);
            rs = ps.executeQuery();
            if (rs.next()){
                count=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection, ps, rs);
        }
        return count;
    }

    @Override
    public int countByState(Integer state) {
        Connection connection = JDBCUtils.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "select count(*) from borrow br,book b,reader r where state=? and b.id=br.bookid and r.id=br.readerid ";
        int count = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, state);
            rs = ps.executeQuery();
            if (rs.next()){
                count=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection, ps, rs);
        }
        return count;
    }

    @Override
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "update borrow set state =? ,adminid=? where id = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, adminId);
            ps.setInt(3, borrowId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(connection, ps, null);
        }
    }
}
