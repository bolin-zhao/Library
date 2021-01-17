package cn.bolin.dao.impl;

import cn.bolin.dao.ReaderDao;
import cn.bolin.domain.Reader;
import cn.bolin.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create By Bolin on
 */
public class ReaderDaoImpl implements ReaderDao {
    // 登录功能
    @Override
    public Reader login(String username, String password) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select * from reader where username=? and password=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader reader = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()){
                reader = new Reader(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
