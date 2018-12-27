package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {

    /**
     * 通过jdbc获取数据库连接
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */

    private static String DRIVERNAME = "driverName";
    private static String URL = "url";
    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private static Connection con = null;
    static {
        Properties info = new Properties();
        InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            info.load(in);
            DRIVERNAME = info.getProperty(DRIVERNAME);
            URL = info.getProperty(URL);
            USERNAME = info.getProperty(USERNAME);
            PASSWORD = info.getProperty(PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVERNAME);
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return con;
    }

    /**
     * 关闭连接
     */
    public static void close(ResultSet rs, Statement statement, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
