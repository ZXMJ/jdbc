package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.util.JDBCUtil;

public class A090JDBCBatch {

	@Test
	public void testBatchPreparedStatement() {
		long startTime = System.currentTimeMillis();
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			con.setAutoCommit(false);
			sql = "insert into student (id,name) values (?,?)";
			ps = con.prepareStatement(sql);
			for (int i = 0; i < 10000; i++) {
				ps.setInt(1, i + 1);
				ps.setString(2, "name_" + (i + 1));
				ps.addBatch();
				if ((i + 1) % 300 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			ps.executeBatch();
			ps.clearBatch();
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, ps, con);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("用时： " + (endTime - startTime));
	}

	@Test
	public void testPreparedStatement() {
		long startTime = System.currentTimeMillis();
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			con.setAutoCommit(false);
			sql = "insert into student values (?,?)";
			ps = con.prepareStatement(sql);
			for (int i = 0; i < 10000; i++) {
				ps.setInt(1, i + 1);
				ps.setString(2, "name_" + (i + 1));
				ps.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, ps, con);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("用时： " + (endTime - startTime));// 16610---2596
	}

	@Test
	public void testStatement() {
		long startTime = System.currentTimeMillis();
		Connection con = null;
		Statement st = null;
		String sql = null;
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			con.setAutoCommit(false);
			st = con.createStatement();
			for (int i = 0; i < 10000; i++) {
				sql = "insert into student (id,name) values (" + (i + 1)
						+ ",'name_+" + (i + 1) + "')";
				st.executeUpdate(sql);
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, st, null, con);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("用时： " + (endTime - startTime));// 20989---2626
	}

	@Test
	public void testBatchNumbers() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			for (int j = 50; j < 3000; j = j + 50) {
				int flag = 0;
				long startTime = System.currentTimeMillis();
				//使用jdbc获取数据库连接
				//con=JDBCUtil.getConnection();
				//使用c3p0连接池获取数据库连接
				con=JDBCUtil.getC3p0Con();
				sql = "insert into student (id,name) values (?,?)";
				ps = con.prepareStatement(sql);
				for (int i = 0; i < 1000; i++) {
					ps.setInt(1, i);
					ps.setString(2, "name_" + i);
					ps.addBatch();
					if ((i + 1) % j == 0) {
						flag = i;
						ps.executeBatch();
						ps.clearBatch();
					}
				}
				if (flag != 999) {
					ps.executeBatch();
					ps.clearBatch();
				}
				long endTime = System.currentTimeMillis();
				System.out.println(j + " 用时： " + (endTime - startTime));// 16798---2715
				sql = "delete from student";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();

				JDBCUtil.close(null, null, ps, con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, ps, con);
		}
	}
}
