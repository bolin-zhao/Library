package cn.bolin.dao.impl;

import cn.bolin.dao.AdminDao;
import cn.bolin.domain.Admin;
import cn.bolin.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create By Bolin on 12.25
 */
public class AdminDaoImpl implements AdminDao {
    // 登录功能
    @Override
    public Admin login(String username, String password) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select * from bookadmin where username=? and password=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()){
                admin = new Admin(rs.getInt(1),rs.getString(2),rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
