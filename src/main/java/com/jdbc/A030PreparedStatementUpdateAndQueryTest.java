package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.util.JDBCUtil;
import com.model.User;

public class A030PreparedStatementUpdateAndQueryTest {
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String sql=null;
	@Test
	public void updateTest(){
		//sql="insert into user(name,email,birth) values(?,?,?)";
		sql="update user set name=? where id=?";
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			ps=con.prepareStatement(sql);
//			ps.setString(1, "tonny");
//			ps.setString(2, "tonny@163.com");
//			ps.setDate(3, new Date(new java.util.Date().getTime()));
			ps.setString(1, "Tonny");
			ps.setInt(2, 3);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, null, ps, con);
		}
	}
	@Test
	public void queryTest(){
		sql="select * from user";
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			ps=con.prepareStatement(sql);
			//ps.setInt(1, 1);
			rs=ps.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setBirth(rs.getDate(4));
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, null, ps, con);
		}
	}
}
