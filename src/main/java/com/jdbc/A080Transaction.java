package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.model.User;
import com.util.JDBCC3p0Util;
import com.util.JDBCUtil;

public class A080Transaction{

	/**
	 * @Description: 事务隔离级别(当一个事务对数据库进行write操作时，另外的事务只能进行read操作，不能进行write操作)
	 * http://blog.csdn.net/fg2006/article/details/6937413
	 * 事务隔离级别，及可能出现的问题
	 * 						脏读		不可重复读		幻读
	 * read uncommitted		√		√			√
	 * read committied		×		√			√
	 * repeatable read		×		×			√
	 * serializable			×		×			×
	 * 事务并发导致的问题		
	 * 脏读(Drity Read)：某个事务已更新一份数据，另一个事务在此时读取了同一份数据，由于某些原因，
	 * 			前一个RollBack了操作，则后一个事务所读取的数据就会是不正确的。
	 * 不可重复读(Non-repeatable read):在一个事务的两次查询之中数据不一致，这可能是两次查询过
	 * 			程中间插入了一个事务更新了原有的数据。
	 * 幻读(Phantom Read):在一个事务的两次查询中数据不一致，例如有一个事务查询了几列(Row)数据，
	 * 			而另一个事务却在此时插入了新的几列数据，先前的事务在接下来的查询中，就会发现有几列数
	 * 			据是它先前所没有的。
	 *	TODO
	 *关于事务隔离级别猜想：(待验证，多线程操作)
	 *repeatable read如何避免不可重复读发生：当某个事务write某条记录时，事务未提交，该记录不能被read
	 *serializable如何避免幻读：当write某个表时。该表不能被read
	 * @date 2016-7-13,上午10:02:45
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 */
	@Test
	public void testTransactionIsolation() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCC3p0Util.getC3p0Con();
			con.setAutoCommit(false);
			int isolation = con.getTransactionIsolation();
			System.out.println(isolation);
			con.setTransactionIsolation(4);
			sql="update user set name = ? where id = ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, "Sunnoy");
			ps.setInt(2, 2);
			ps.executeUpdate();
			
			sql="select * from user where id = ?";
			ps=con.prepareStatement(sql);
			ps.setInt(1,2);
			rs=ps.executeQuery();
			if(rs.next()){
				User user=new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setBirth(rs.getDate(4));
				System.out.println(user);
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, null, ps, con);
		}

	}

	
	
	
	@Test
	public void testTransaction() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCC3p0Util.getC3p0Con();
			con.setAutoCommit(false);
			sql = "update user set name=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, "Jerry");
			// ps.setInt(2, 1);
			ps.setInt(2, 4);
			ps.executeUpdate();

			// int i=10/0;
			// System.out.println(i);

			sql = "update user set name=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, "Tom");
			ps.setInt(2, 1);
			// ps.setInt(2, 4);
			ps.executeUpdate();
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
	}

}
