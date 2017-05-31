package com.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.Test;

public class A010DriverTest {
	@Test
	public void getConnection() throws SQLException, IOException {
		Driver driver = new com.mysql.jdbc.Driver();
		String url = "jdbc:mysql:///test";
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "fan123");
		Connection con = driver.connect(url, info);
		System.out.println(con);
		con.close();
	}

	@Test
	public void getConnertion2() throws Exception {
		Properties info = new Properties();
		InputStream inStream = this.getClass().getClassLoader()
				.getResourceAsStream("db.properties");
		info.load(inStream);
		String driverName = info.getProperty("driverName");
		String url = info.getProperty("url");
		Driver driver = (Driver) Class.forName(driverName).newInstance();
		// connect(String url,properties
		// info):info要求：配置文件中，用户名使用user，密码使用password
		Connection con = driver.connect(url, info);
		System.out.println(con);
		inStream.close();
		con.close();
	}

	@Test
	public void getConnection3() throws Exception {
		InputStream in = this.getClass().getResourceAsStream("/db.properties");
		Properties info = new Properties();
		info.load(in);
		String driverName = info.getProperty("driverName");
		String url = info.getProperty("url");
		String user = info.getProperty("user");
		String password = info.getProperty("password");
		Class.forName(driverName);
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println(con);
		in.close();
		con.close();
	}
}
