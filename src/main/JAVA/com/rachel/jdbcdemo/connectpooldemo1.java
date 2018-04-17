package com.rachel.jdbcdemo;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by hd48552 on 2018/4/17.
 */
public class connectpooldemo1 {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  conn;
    }

    //从配置文件中获取数据库连接配置信息
    public Connection getDBCPConn1(){
        Connection conn=null;



        try {
            //创建properties对象
            Properties properties = new Properties();

            //获取properties对应的输入流
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
