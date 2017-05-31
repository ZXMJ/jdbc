package com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.util.ReflectUtil;

public class A122JdbcDaoImpl<T> implements A120Dao<T> {

	private QueryRunner qr = null;
	private Class<T> type = null;

	public A122JdbcDaoImpl() {
		qr = new QueryRunner();
		type = ReflectUtil.getSuperGenericType(getClass());
	}

	@Override
	public void batch(Connection con, String sql, Object[] ... args) throws SQLException {
		for(Object object:args){
			//TODO
			qr.update(con, sql, object);
		}
	}

	public <E> E getScalar(Connection con, String sql,Class<E> E, Object ... args) throws SQLException {
		return qr.query(con, sql, new ScalarHandler<E>(), args);
	}

	@Override
	public List<T> getBeanList(Connection con, String sql, Object ... args) throws SQLException {
		return qr.query(con, sql, new BeanListHandler<T>(type), args);
	}

	@Override
	public T getBean(Connection con, String sql, Object ... args) throws SQLException {
		return qr.query(con, sql, new BeanHandler<T>(type), args);
	}

	@Override
	public void update(Connection con, String sql, Object ... args) throws SQLException {
		qr.update(con, sql, args);

	}

}
