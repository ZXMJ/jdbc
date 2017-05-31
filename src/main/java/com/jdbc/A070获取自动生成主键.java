package com.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Test;
import com.util.JDBCUtil;

public class A070获取自动生成主键 {

	@Test
	public void testGetGeneratedKeys(){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql ="insert into user (name,email,birth) values(?,?,?)";
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "Jerry");
			ps.setString(2, "Jerrry@163.com");
			ps.setDate(3, new Date(new java.util.Date().getTime()));
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				System.out.println(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.close(null, null, ps, con);
		}
	}
}
