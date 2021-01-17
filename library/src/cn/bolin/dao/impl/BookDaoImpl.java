package cn.bolin.dao.impl;

import cn.bolin.dao.BookDao;
import cn.bolin.domain.Book;
import cn.bolin.domain.BookCase;
import cn.bolin.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Bolin on 12.25
 */
public class BookDaoImpl implements BookDao {
    @Override
    public List<Book> findAll(int index,int limit) {
        Connection conn = JDBCUtils.getConnection();
        // 分页查询
        String sql = "SELECT * FROM book,bookcase WHERE book.bookcaseid=bookcase.id limit ?,?;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Book> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, index);
            ps.setInt(2, limit);
            rs = ps.executeQuery();
            while (rs.next()){
                // 将第9,10列封装成一个bookcase对象传入到book表里,作为最后一列
                /*BookCase bookCase = new BookCase(rs.getInt(9), rs.getString(10));
                Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6),bookCase);
                list.add(book);*/
                // 强化写法, 不用开辟栈内存,直接开辟堆,节省空间
                list.add(new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getDouble(6),new BookCase(rs.getInt(9),rs.getString(10))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);
        }
        return list;
    }

    @Override
    public int getCount() {
        Connection conn = JDBCUtils.getConnection();
        // 获取总页数
        String sql = "SELECT count(*)FROM book,bookcase WHERE book.bookcaseid=bookcase.id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Book> list = new ArrayList<>();
        int count = 0;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
                System.out.println("count:"+count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,rs);

        }
        return count;
    }
}
