package com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.model.User;
import com.util.JDBCC3p0Util;
import com.util.JDBCUtil;

public class A110Dbutils {
    Connection con = null;
    QueryRunner qr = new QueryRunner();

    /**
     * @Description: 测试QueryRunner的查询操作，并且返回结果是查询结果的某一条记录
     *
     * @date 2016年7月26日,下午2:34:54
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testScalarHandler() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "select count(id),name from user";
        Object info = null;
        try {
            info = qr.query(con, sql, new ScalarHandler<Object>(2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, null, con);
        }
        System.out.println(info);
    }

    /**
     * @Description: 测试QueryRunner的查询操作，并且返回结果是一个存放一个对象属性和值的map对象的list集合
     *
     * @date 2016年7月26日,下午2:34:54
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testMapListHandler() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "select id,name,email,birth from user";
        List<Map<String, Object>> users = null;
        try {
            users = qr.query(con, sql, new MapListHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(users);
    }

    /**
     * @Description: 测试QueryRunner的查询操作，并且返回结果是一个存放一个对象属性和值的map对象
     *
     * @date 2016年7月26日,下午2:34:54
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testMapHandler() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "select id,name,email,birth from user where id=?";
        Map<String, Object> user = null;
        try {
            user = qr.query(con, sql, new MapHandler(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user);
    }

    /**
     * @Description: 测试QueryRunner的查询操作，并且返回结果是一个存放对象的list集合
     *
     * @date 2016年7月26日,下午2:34:54
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testBeanListHandler() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "select id,name,email,birth from user";
        List<User> users = null;
        try {
            users = qr.query(con, sql, new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, null, con);
        }
        System.out.println(users);
    }

    /**
     * @Description: 测试QueryRunner的查询操作，并且返回结果是一个对象
     *
     * @date 2016年7月26日,下午2:34:54
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testBeanHandler() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "select id,name,email,birth from user where id=?";
        User user = null;
        try {
            // 当结果为多条时，只返回第一条
            user = qr.query(con, sql, new BeanHandler<User>(User.class), 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, null, con);
        }
        System.out.println(user);
    }

    /**
     * @Description: 测试QueryRunner的查询操作,其中结果集ResultSetHandler的handle()方法中处理
     *
     * @date 2016年7月26日,下午1:59:51
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testQuery() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "select id,name,email,birth from user";
        class MyResultSetHandler implements ResultSetHandler<Object> {
            @Override
            public Object handle(ResultSet rs) throws SQLException {
                System.out.println("handle...");
                return "test";
            }
        }
        try {
            Object obj = qr.query(con, sql, new MyResultSetHandler());
            System.out.println(obj);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, null, con);
        }
    }

    /**
     * @Description: 测试QueryRunner的增删改操作
     *
     * @date 2016年7月26日,下午1:58:37
     * @author fanbaoshen
     * @version 5.0
     *
     */
    @Test
    public void testUpdate() {
        try {
            con = JDBCC3p0Util.getC3p0Con();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        // String sql = "insert into user (name,email,birth) values (?,?,?)";
        String sql = "update user set name=? where id=?";
        // String sql = "delete from user where id=?";
        try {
            // qr.update(con, sql, "AA","aa@qq.com", 9);
            qr.update(con, sql, "BB", 9);
            // qr.update(con, sql, 9);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, null, null, con);
        }
    }

}
