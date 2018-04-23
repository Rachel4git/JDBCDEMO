
package com.rachel.jdbcdemo;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hd48552 on 2018/4/23.
 */

public class JDBCDAO<T> implements DAO<T>{
    QueryRunner queryRunner = new QueryRunner();
    Class<T> type;

    public JDBCDAO() {
        queryRunner = new QueryRunner();
//        type = ReflectionUtils.getSuperGenericType(getClass());
    }
    /*@Override
    public  <E> E queryvalue (Connection conn, String sql, T ...args) throws SQLException {
//        return (E) queryRunner.query(conn,sql, new ScalarHandler<>(1),args);
        return null;
    }*/


    @Override
    public Object queryvalue(Connection conn, String sql, Object... args) throws SQLException {
        return  queryRunner.query(conn,sql, new ScalarHandler<>(1),args);
    }

    @Override
    public List<T> queryall(Connection conn, String sql, Object... args) throws SQLException {
        List<T> ll = queryRunner.query(conn,sql,new BeanListHandler<>(type),args);
        return ll;
    }

    @Override
    public T queryone(Connection conn, String sql,  Object... args) throws SQLException{
        return queryRunner.query(conn,sql,new BeanHandler<>(type),args);
    }

    @Override
    public void update(Connection conn, String sql, Object... args) throws SQLException {
        queryRunner.update(conn,sql,args);
    }
}
