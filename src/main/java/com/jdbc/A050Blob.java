package com.jdbc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import com.util.JDBCUtil;

public class A050Blob {

	@Test
	public void testWriteBolb() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into user (name,email,birth,picture) values (?,?,?,?)";
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			ps = con.prepareStatement(sql);
			ps.setString(1, "machael");
			ps.setString(2, "machael@163.com");
			ps.setDate(3, new Date(new java.util.Date().getTime()));
			InputStream inputStream = new FileInputStream("test.png");
			ps.setBlob(4, inputStream);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, ps, con);
		}
	}

	@Test
	public void testReadBolb() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select picture from user where id=?";
		try {
			//使用jdbc获取数据库连接
			//con=JDBCUtil.getConnection();
			//使用c3p0连接池获取数据库连接
			con=JDBCUtil.getC3p0Con();
			ps = con.prepareStatement(sql);
			ps.setInt(1, 8);
			rs = ps.executeQuery();
			while (rs.next()) {
				Blob picture = rs.getBlob(1);
				
				InputStream in = picture.getBinaryStream();
				System.out.println(in.available()); 
				
				OutputStream out = new FileOutputStream("black.jpg");
				
				byte [] buffer = new byte[1024];
				int len = 0;
				while((len = in.read(buffer)) != -1){
					out.write(buffer, 0, len);
				}
				
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, null, ps, con);
		}
	}
}
