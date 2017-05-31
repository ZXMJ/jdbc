package com.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.junit.Test;

import com.util.JDBCUtil;

public class A020StatementUpdateAndQueryTest {
	Connection con=null;
	Statement statement=null;
	ResultSet rs=null;
	@Test
	public void updateTest(){
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			statement=con.createStatement();
			String sql="update user set name='sun' where id=2";
					//"delete from user where name='sun'";
					//"INSERT INTO USER (NAME,email,birth) VALUES('sun','sun@163.com','1991-09-09')";
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, statement, null, con);
		}
	}
	@Test
	public void queryTest(){
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			statement=con.createStatement();
			String sql="select * from user";
					//"select * from user where id=1";
			rs=statement.executeQuery(sql);
			while(rs.next()){
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String email=rs.getString("email");
				Date birth=rs.getDate("birth");
//				int id=rs.getInt(1);
//				String name=rs.getString(2);
//				String email=rs.getString(3);
//				Date birth=rs.getDate(4);
				System.out.println("id:"+id+" name:"+name+" email:"+email+" birth:"+birth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, statement, null, con);
		}
	}
}
