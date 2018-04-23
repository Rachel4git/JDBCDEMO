package com.rachel.jdbcdemo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hd48552 on 2018/4/23.
 */
public interface DAO<T> {

    /**
     * 定义操作数据库的通用方法
     */
//    query
//    queryvalue
    public  <E> E queryvalue (Connection conn, String sql, Object ...args)throws SQLException;
//    queryall
    public List<T> queryall(Connection conn, String sql,Object ...args) throws SQLException;
//    queryone
    public T queryone(Connection conn, String sql,  Object ...args)throws SQLException;
//    update
    public  void update(Connection conn,String sql,Object ...args) throws SQLException;
}
