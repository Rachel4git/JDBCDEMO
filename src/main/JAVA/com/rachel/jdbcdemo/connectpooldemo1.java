package com.rachel.jdbcdemo;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by hd48552 on 2018/4/17.
 */
public class connectpooldemo1 {
    static DataSource ds = null;
    static {
        ds = new ComboPooledDataSource("c3p0.xml");
    }

    public  static  Connection getConnection(){
        Connection conn = null;
        try {
            conn=  ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  conn;
    }

    public  void getC3P01(){
        ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0.xml");///读取配置文件有问题
        try {
            System.out.println(cpds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    c3p0数据库连接池

    /**
     * maven依赖配置;
     * <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>5.0.4</version>
     </dependency>
     <dependency>
     <groupId>com.mchange</groupId>
     <artifactId>c3p0</artifactId>
     <version>0.9.5.1</version>
     </dependency>
     * @return
     */
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

    //DBCP数据库连接池
    public Connection getDBCPConn(){
        Connection conn=null;
        //    创建数据源对象
        BasicDataSource ds = new BasicDataSource() ;//设置对应的maven依赖
        /**
         * <dependency>
         <groupId>commons-dbcp</groupId>
         <artifactId>commons-dbcp</artifactId>
         <version>1.4</version>
         </dependency>
         */
//    指定必须属性
        /**
         *
         String driver = "com.mysql.jdbc.Driver";
         String jdbcUrl = "jdbc:mysql://10.100.157.78:3500/TCFlyIntOAG";
         String user="TCFlyIntOAG";
         String password = "nKL39Q2iOD94sxSgqlzL";
         */
        ds.setUsername("TCFlyIntOAG");
        ds.setPassword("nKL39Q2iOD94sxSgqlzL");
        ds.setUrl("jdbc:mysql://10.100.157.78:3500/TCFlyIntOAG");
        ds.setDriverClassName("com.mysql.jdbc.Driver");

//    指定可选属性
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMinIdle(2);
        ds.setMaxWait(500);



//    获取连接
        try {
            conn= ds.getConnection();
            System.out.println(ds.getConnection());
            System.out.println(ds.getMaxWait());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  conn;
    }

    //从配置文件中获取数据库连接配置信息,使用BasicDataSourceFactory创建数据源
    public Connection getDBCPConn1(){
        Connection conn=null;



        try {
            //创建properties对象
            Properties properties = new Properties();

            //获取properties对应的输入流
//            读取配置文件有问题

            InputStream inputStream =connectpooldemo1.class.getClassLoader().getResourceAsStream("dbcp.properties");

            //加载properties的输入流
            properties.load(inputStream);

            DataSource dataSource =  BasicDataSourceFactory.createDataSource(properties);

            System.out.println(dataSource.getConnection());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    return  conn;
    }

}
