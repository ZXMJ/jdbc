package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCC3p0Util {

	/**
	 * 通过jdbc获取数据库连接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */


	// DBCP和C3P0都是用的DataSource
	private static DataSource dataSource;

	/**
	 * @Description: 通过DBCP数据库连接池获取数据库连接
	 *
	 * @date 2016年7月26日,上午9:33:44
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 * @return
	 * @throws Exception
	 */

/*	static {
		try {
			InputStream inputStream = JDBCUtil.class.getClassLoader()
					.getResourceAsStream("dbcp.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			dataSource = BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getDbcpCon() throws Exception {
		return dataSource.getConnection();
	}*/

	/**
	 * @Description: 通过C3P0数据库连接池获取数据库连接
	 *
	 * @date 2016年7月22日,上午9:38:45
	 * @author fanbaoshen
	 * @version 5.0
	 *
	 * @return
	 * @throws Exception
	 */

	static {
		// 获取的资名称是c3p0-config.xml中<named-config name="helloc3p0">中的name
		dataSource = new ComboPooledDataSource("helloc3p0");
	}

	public static Connection getC3p0Con() throws SQLException {
		Connection con = null;
		con = dataSource.getConnection();
		return con;
	}

	/**
	 * 关闭连接
	 */
	public static void close(ResultSet rs, Statement statement,
			PreparedStatement ps, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

