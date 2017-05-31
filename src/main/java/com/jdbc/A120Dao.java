package com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 * 
 * @ClassName: A120Dao 
 * @Description: 最底层的dao,供jdbc，hibernate，mybatis实现
 * @author fanbaoshen
 * @date 2016年7月27日 上午9:31:06 
 * 
 * @version 5.0
 * @param <T>
 */
public interface A120Dao<T> {

	void batch(Connection con,String sql,Object []...args) throws SQLException;
	
	<E> E getScalar(Connection con,String sql,Class<E> E,Object ...args) throws SQLException;
	
	List<T> getBeanList(Connection con,String sql,Object ...args) throws SQLException;
	
	T getBean(Connection con,String sql,Object ...args) throws SQLException;
	
	void update(Connection con,String sql,Object ...args) throws SQLException;
}
