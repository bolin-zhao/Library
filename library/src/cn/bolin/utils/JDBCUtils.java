package cn.bolin.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Create By Bolin on 12.24
 */
public class JDBCUtils {
    private static DataSource dataSource;

    static{
        dataSource = new ComboPooledDataSource("testc3p0");
    }
    // 连接数据库
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 释放资源
    public static void release(Connection conn, PreparedStatement ps, ResultSet rs){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 测试连接
    public static void main(String[] args) {
        System.out.println(JDBCUtils.getConnection());
    }
}
