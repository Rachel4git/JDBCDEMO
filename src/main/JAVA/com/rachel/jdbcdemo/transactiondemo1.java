package com.rachel.jdbcdemo;

import java.io.IOException;
import java.sql.*;

/**
 * Created by hd48552 on 2018/4/15.
 */
public class transactiondemo1 {
    public  static  void  main(String[] args ){
        testTransaction();
    }
    public  static  void  testTransaction(){

        Connection conn = null;
        try {

            conn = getConnection();
            // 1开始事务，设置自动提交为false
            conn.setAutoCommit(false);
//            设置数据库的隔离级别
            conn.setTransactionIsolation(4);
            System.out.println(conn.getTransactionIsolation());
            String sql1 = "update union_protocol set union_airline_amount= 50 where id = 1";
            update(conn,sql1);

            int i = 10/0;
            String sql2 = "update union_protocol set union_airline_amount= 20 where id = 2";
            update(conn,sql2);
//            成功，提交事务
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();

            try {
//                出现异常 回滚事务
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            release(conn,null,null);
        }


    }



    public static  void update(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            release(null,preparedStatement,null);
        }
    }


    public static Connection getConnection() throws ClassNotFoundException, SQLException,IOException {
//        //1.从配置文件中获取数据库连接配置信息
//
//        //创建properties对象
//        Properties properties = new Properties();
//        String realPath = getServletContext().getRealPath("/WEB-INF/JDBC.properties");
//
//        //获取properties对应的输入流
//        InputStream in = new FileInputStream(realPath);
//        //加载properties的输入流
//        properties.load(in);
//        //读取properties文件中的配置信息
//        String driver = properties.getProperty("driver");
//        String user = properties.getProperty("user");
//        String password = properties.getProperty("password");
//        String jdbcUrl = properties.getProperty("jdbcUrl");
//
//        System.out.println(driver);
//        System.out.println(user);
//        System.out.println(password);
//        System.out.println(jdbcUrl);


        String driver = "com.mysql.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://10.100.157.78:3500/TCFlyIntOAG";
        String user="TCFlyIntOAG";
        String password = "nKL39Q2iOD94sxSgqlzL";
        Connection connection = null;
        //加载驱动并注册
        Class.forName(driver);
        //获取connection
        connection = DriverManager.getConnection(jdbcUrl,user,password);
        return connection;
    }

    /**
     * 释放数据库连接
     * @param conn
     * @param sta
     * @param res
     */
    public static  void  release(Connection conn, PreparedStatement sta, ResultSet res){
/**
 *  * 2. Connection、Statement 都是应用程序和数据库服务器的连接资源. 使用后一定要关闭.
 * 需要在 finally 中关闭 Connection 和 Statement 对象.
 *
 * 3. 关闭的顺序是: 先关闭后获取的. 即先关闭 Statement 后关闭 Connection
 */
        if(res!=null){
            try {
                res.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(sta!=null){
            try {
                sta.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

    }

}
