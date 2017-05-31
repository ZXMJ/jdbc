package com.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import com.model.User;
import com.util.JDBCUtil;

public class A124UserDaoImplTest {
	Connection con = null;
	A124UserDao userDao = new A124UserDao();

	@Test
	public void testBatch() {
		
	}

	@Test
	public void testGetScalar() {
		try {
			con = JDBCUtil.getC3p0Con();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "select count(id) from user";
		Long field = null;
		try {
			field = userDao.getScalar(con, sql,Long.class);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, null, con);
		}
		System.out.println(field);
	}

	@Test
	public void testGetBeanList() {
		try {
			con = JDBCUtil.getC3p0Con();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "select id,name,email,birth from user";
		List<User> users = null;
		try {
			users = userDao.getBeanList(con, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, null, con);
		}
		System.out.println(users);
	}

	@Test
	public void testGetBean() {
		try {
			con = JDBCUtil.getC3p0Con();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "select id,name,email,birth from user where id = ?";
		User user = null;
		try {
			user = userDao.getBean(con, sql, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, null, con);
		}
		System.out.println(user);
	}

	@Test
	public void testUpdate() {
		try {
			con = JDBCUtil.getC3p0Con();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "insert into user (name,email,birth) values (?,?,?)";
		try {
			userDao.update(con, sql,"AA","aa@163.com",new Date(new java.util.Date().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, null, con);
		}
	}
}