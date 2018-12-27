package com.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.junit.Test;

import com.util.JDBCC3p0Util;
import com.util.JDBCUtil;

public class A060GetDataBaseMessage {

    @Test
    public void testGetDataBaseMessage() {
        Connection con = null;
        ResultSet rs = null;
        try {
            // 使用jdbc获取数据库连接
            // con=JDBCUtil.getConnection();
            // 使用c3p0连接池获取数据库连接
            con = JDBCC3p0Util.getC3p0Con();
            DatabaseMetaData dbmd = con.getMetaData();
            int version = dbmd.getDatabaseMajorVersion();
            System.out.println("version: " + version);
            String user = dbmd.getUserName();
            System.out.println("user: " + user);
            String url = dbmd.getURL();
            System.out.println("url: " + url);
            rs = dbmd.getCatalogs();
            while (rs.next()) {
                System.out.println(rs.getObject(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, null, null, con);
        }
    }
}
