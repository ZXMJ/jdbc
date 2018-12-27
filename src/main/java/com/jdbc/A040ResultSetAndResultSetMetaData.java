package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.model.User;
import com.util.JDBCC3p0Util;
import com.util.JDBCUtil;

public class A040ResultSetAndResultSetMetaData {

    @Test
    public void testGetInstance() throws Exception {
        String sql = "select id,name,email,birth from user where id=?";
        User user = getInstance(User.class, sql, "1");
        System.out.println(user);
    }

    @Test
    public void testGetInstance2() throws Exception {
        String sql = "select id,name,email,birth from user where id=?";
        User user = getInstance2(User.class, sql, "1");
        System.out.println(user);
    }

    @Test
    public void testGetInstances() throws Exception {
        String sql = "select id,name,email,birth from user";
        List<User> users = getInstances(User.class, sql);
        System.out.println(users);
    }

    @Test
    public void testGetInstances2() throws Exception {
        String sql = "select id,name,email,birth from user";
        List<User> users = getInstances2(User.class, sql);
        System.out.println(users);
    }

    /**
     * @Description: �����������class��sql����sql���� ��ȡһ����������ֵ�Ķ���
     * 
     * @date 2016-7-7,����9:24:39
     * @author fanbaoshen
     * @version 1.0
     * 
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T entity = null;
        try {
            // 使用jdbc获取数据库连接
            // con=JDBCUtil.getConnection();
            // 使用c3p0连接池获取数据库连接
            con = JDBCC3p0Util.getC3p0Con();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                entity = clazz.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                int length = rsmd.getColumnCount();
                for (int i = 0; i < length; i++) {
                    String key = rsmd.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    BeanUtils.setProperty(entity, key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, null, ps, con);
        }

        return entity;

    }

    /**
     * @Description: �����������class��sql����sql���� ��ȡһ����������ֵ�Ķ���
     * 
     * @date 2016-7-7,����9:30:13
     * @author fanbaoshen
     * @version 1.0
     * 
     * @param clazz
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public <T> T getInstance2(Class<T> clazz, String sql, Object... args) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T entity = null;

        try {
            // 使用jdbc获取数据库连接
            // con=JDBCUtil.getConnection();
            // 使用c3p0连接池获取数据库连接
            con = JDBCC3p0Util.getC3p0Con();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = null;
            Map<String, Object> map = null;
            while (rs.next()) {
                rsmd = rs.getMetaData();
                map = new HashMap<String, Object>();
                int num = rsmd.getColumnCount();
                for (int i = 0; i < num; i++) {
                    String key = rsmd.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    map.put(key, value);
                }
            }
            entity = clazz.newInstance();
            for (Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                BeanUtils.setProperty(entity, key, value);
                // ReflectUtil.setProperty(entry, key, value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, null, ps, con);
        }
        return entity;
    }

    /**
     * @Description: �����������class��sql����sql���� ��ȡһ����������ֵ�Ķ��󼯺�
     * 
     * @date 2016-7-7,����9:24:39
     * @author fanbaoshen
     * @version 1.0
     * 
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public <T> List<T> getInstances(Class<T> clazz, String sql, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> intances = new ArrayList<T>();
        try {
            // 使用jdbc获取数据库连接
            // con=JDBCUtil.getConnection();
            // 使用c3p0连接池获取数据库连接
            con = JDBCC3p0Util.getC3p0Con();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                T t = clazz.newInstance();
                ;
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String key = rsmd.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    BeanUtils.setProperty(t, key, value);
                }
                intances.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intances;
    }

    /**
     * 
     * @Description: �����������class��sql����sql���� ��ȡһ����������ֵ�Ķ��󼯺�
     * 
     * @date 2016-7-8,����9:39:40
     * @author fanbaoshen
     * @version 5.0
     * 
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public <T> List<T> getInstances2(Class<T> clazz, String sql, Object... args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> intances = new ArrayList<T>();
        List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();
        try {
            // 使用jdbc获取数据库连接
            // con=JDBCUtil.getConnection();
            // 使用c3p0连接池获取数据库连接
            con = JDBCC3p0Util.getC3p0Con();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                Map<String, Object> property = new HashMap<String, Object>();
                for (int i = 0; i < columnCount; i++) {
                    String key = rsmd.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    property.put(key, value);
                }
                if (property != null)
                    properties.add(property);
            }
            for (Map<String, Object> map : properties) {
                T t = clazz.newInstance();
                for (Entry<String, Object> entry : map.entrySet()) {
                    BeanUtils.setProperty(t, entry.getKey(), entry.getValue());
                }
                intances.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intances;
    }
}
