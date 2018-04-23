package com.rachel.jdbcdemo;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hd48552 on 2018/4/23.
 */
public class dbutilsdemo1 {

//queryloader
    public void testqueryloader() throws IOException {
        Map<String,String> sqls = QueryLoader.instance().load("sql.properties");
    }

//    query\
    /**
     * 1. ResultSetHandler 的作用: QueryRunner 的 query 方法的返回值最终取决于
     * query 方法的 ResultHandler 参数的 hanlde 方法的返回值.
     *
     * 2. BeanListHandler: 把结果集转为一个 Bean 的 List, 并返回. Bean 的类型在
     * 创建 BeanListHanlder 对象时以 Class 对象的方式传入. 可以适应列的别名来映射
     * JavaBean 的属性名:
     * String sql = "SELECT id, name customerName, email, birth " +
     *			"FROM customers WHERE id = ?";
     *
     * BeanListHandler(Class<T> type)
     *
     * 3. BeanHandler: 把结果集转为一个 Bean, 并返回. Bean 的类型在创建 BeanHandler
     * 对象时以 Class 对象的方式传入
     * BeanHandler(Class<T> type)
     *
     * 4. MapHandler: 把结果集转为一个 Map 对象, 并返回. 若结果集中有多条记录, 仅返回
     * 第一条记录对应的 Map 对象. Map 的键: 列名(而非列的别名), 值: 列的值
     *
     * 5. MapListHandler: 把结果集转为一个 Map 对象的集合, 并返回.
     * Map 的键: 列名(而非列的别名), 值: 列的值
     *
     * 6. ScalarHandler: 可以返回指定列的一个值或返回一个统计函数的值.
     */
//    scalarhandler
public  void testquery6() throws Exception{
    QueryRunner queryRunner = new QueryRunner();
    String sql = "select * from union_protocol where id <5";
    Connection conn = null;

    try {
        conn = getC3P0();
        Object obj = queryRunner.query(conn, sql, new ScalarHandler("union_airline_amount"));//返回指定列名或列索引的值
        System.out.println(obj);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conn.close();
    }
}
//    maplisthandler
public  void testquery5() throws Exception{
    QueryRunner queryRunner = new QueryRunner();
    String sql = "select id,airline AIRLINE ,union_airline_amount from union_protocol where id <5";
    Connection conn = null;

    try {
        conn = getC3P0();
        Object obj = queryRunner.query(conn, sql, new MapListHandler());
        System.out.println(obj);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conn.close();
    }
}
//    maphandler
public  void testquery4() throws Exception{
    QueryRunner queryRunner = new QueryRunner();
    String sql = "select * from union_protocol where id <5";
    Connection conn = null;

    try {
        conn = getC3P0();
        Object obj = queryRunner.query(conn, sql, new MapHandler());
        System.out.println(obj);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conn.close();
    }
}
//    beanlisthandler,返回结果集对应的list
    public  void testquery3() throws Exception{
    QueryRunner queryRunner = new QueryRunner();
    String sql = "select * from union_protocol where id <5";
    Connection conn = null;

    try {
        conn = getC3P0();
        Object obj = queryRunner.query(conn, sql, new BeanListHandler<>(unionprotocol.class));//获取不到ID和airline
        System.out.println(obj);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conn.close();
    }
}

//  beanhandler，返回结果集第一条记录对应的对象
    public  void testquery2() throws Exception{
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from union_protocol where id <5";
        Connection conn = null;

        try {
            conn = getC3P0();
            Object obj = queryRunner.query(conn, sql, new BeanHandler<>(unionprotocol.class));//获取不到ID和airline
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

//    resultsethandler,query方法的返回值取决于resultsethandler中handle方法的返回值
    public  void  testquery1() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from union_protocol where id <5";
        Connection conn = null;

        try {
            conn = getC3P0();
            Object obj = queryRunner.query(conn, sql, new ResultSetHandler() {

                @Override
                public Object handle(ResultSet rs) throws SQLException {
                    List<unionprotocol> ll = new ArrayList<>();
                    while (rs.next()){
                        int id = rs.getInt(1);
                        String airline = rs.getString(2);
                        int union_airline_amount = rs.getInt(3);
                        String union_airlines = rs.getString(4);

                        unionprotocol u = new unionprotocol(id,airline,union_airline_amount,union_airlines);
                        ll.add(u);
                    }
                    return  ll;
                }
            });
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

//    update
    public  void testupdate() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update union_protocol set airline = ? where id = ?";
        Connection conn = null;
        try {
            conn = getC3P0();
            queryRunner.update(conn, sql, "KE", 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }


    public  static Connection getC3P0(){
        Connection conn = null;
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setUser("TCFlyIntOAG");
        cpds.setPassword("nKL39Q2iOD94sxSgqlzL");
        cpds.setJdbcUrl("jdbc:mysql://10.100.157.78:3500/TCFlyIntOAG");
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setAcquireIncrement(5);
        cpds.setInitialPoolSize(5);
        cpds.setMaxPoolSize(10);
        cpds.setMinPoolSize(5);
        cpds.setMaxStatements(50);
        cpds.setMaxStatementsPerConnection(5);

        try {
            conn = cpds.getConnection();
//            System.out.println(cpds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    }



