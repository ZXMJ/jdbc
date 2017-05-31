package com.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class A100ConnectPool {
	/**
	 * @Description: 通过配置文件获取C3P0连接池
	 *
	 * @date 2016年7月21日,上午10:15:37
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 * @throws Exception
	 */

	@Test
	public void testC3P0Pool2() throws Exception {
		DataSource dataSource = new ComboPooledDataSource("helloc3p0");
		Connection con = dataSource.getConnection();
		System.out.println(con);
		// 测试使用
		ComboPooledDataSource cpds = (ComboPooledDataSource) dataSource;
		System.out.println(cpds.getUser());
		System.out.println(cpds.getPassword());
		con.close();
	}

	/**
	 * @Description: 通过手动配置获取C3P0连接池
	 *
	 * @date 2016年7月21日,上午10:16:36
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 * @throws Exception
	 */
	@Test
	public void testC3P0Pool() throws Exception {
		DataSource dataSource = new ComboPooledDataSource();
		ComboPooledDataSource cpds = (ComboPooledDataSource) dataSource;
		cpds.setUser("root");
		cpds.setPassword("fan123");
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql:///test");
		cpds.setInitialPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMinPoolSize(5);
		cpds.setMaxPoolSize(10);
		cpds.setMaxStatements(20);
		cpds.setMaxStatementsPerConnection(5);
		Connection con = dataSource.getConnection();
		System.out.println(con);
		con.close();
	}
	/**
	 * @Description: 通过配置文件获取DBCP连接池
	 *
	 * @date 2016年7月21日,上午10:15:37
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 * @throws Exception
	 */
	@Test
	public void testDBCPPool2() throws Exception {
		Properties info=new Properties();
		InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("dbcp.properties");
		info.load(inputStream);
		DataSource dataSource=BasicDataSourceFactory.createDataSource(info);
		Connection con=dataSource.getConnection();
		System.out.println(con);
		con.close();
	}
	/**
	 * @Description: 通过手动配置获取DBCP连接池
	 *
	 * @date 2016年7月21日,上午10:16:36
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 * @throws Exception
	 */
	@Test
	public void testDBCPPool() throws Exception {
		final BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("fan123");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql//loclhost:3306/test");
		dataSource.setInitialSize(5);
		dataSource.setMaxActive(5);
		dataSource.setMinIdle(2);
		dataSource.setMaxWait(1000*5);
		Connection con=dataSource.getConnection();
		System.out.println(con);
	}

}
