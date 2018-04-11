package com.rachel.jdbcdemo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by hd48552 on 2018/4/9.
 */
public class jdbcdemo1 extends HttpServlet{

@Override
    public  void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException {
//        testjdbc();//数据库连接，使用statement
//        testpreparedSta();//preparedstatement
        sqlInjection();//sql注入
}

/**
 * sql注入
 */
    public  void  sqlInjection() throws  IOException{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String airline = "ee'OR id = ";
        String id =" OR '1'='1" ;
        String sql = "SELECT * FROM `union_protocol` WHERE airline ='" +airline + "'and id = '"+id+"'";


        //
        try {

            connection = getConnection();
            statement = connection.createStatement();
            resultSet =statement.executeQuery(sql);//executeQuery中插入的SQL只能是select
            if(resultSet.next())
                System.out.println("congratulation");
            else
                System.out.println("SORRY");

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            release(connection,statement,resultSet);
        }

    }


    /**
 * preparedstatement
 */
    public  void  testpreparedSta() throws  IOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = null;

        try {
            connection = getConnection();
            sql = "SELECT  * FROM  union_protocol where id <= ?";
            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(2,"MU");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String airline = resultSet.getString("airline");

                System.out.println("---------"+id+airline);
            }

            sql = "UPDATE  union_protocol  SET  airline=? ,union_airline_amount=?  WHERE  id = 1;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"MU");
            preparedStatement.setInt(2,151);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(connection,preparedStatement,resultSet);
        }

    }

    /**
     * statement
     * @throws IOException
     */
    public  void testjdbc() throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
//        ResultSet resultSet1 = null;
        String sql = "select * from union_protocol where id <=12";
        String sql2 = "UPDATE `union_protocol` SET airline =\"MU\" WHERE ID =1;";

        //
        try {
            /**
             * 通过 JDBC 向指定的数据表中插入一条记录.
             * 1. Statement: 用于执行 SQL 语句的对象
             * 1). 通过 Connection 的 createStatement() 方法来获取
             */
             connection = getConnection();
             statement = connection.createStatement();
             resultSet =statement.executeQuery(sql);//executeQuery中插入的SQL只能是select
             while (resultSet.next()){
                 int id = resultSet.getInt(1);
                 String airline = resultSet.getString("airline");
                 String create_gmt = resultSet.getString("gmt_create");

                 System.out.println(id);
                 System.out.println(airline);
                 System.out.println(create_gmt);

             }
            /**
             * //* 2). 通过 executeUpdate(sql) 可以执行 SQL 语句.
             * * 3). 传入的 SQL 可以是 INSRET, UPDATE 或 DELETE. 但不能是 SELECT
             */
            statement.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            release(connection,statement,resultSet);
//            release(connection,statement,resultSet1);
        }


    }

    /**
     * 获取数据库连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */

    public Connection getConnection() throws ClassNotFoundException, SQLException ,IOException{
        //1.从配置文件中获取数据库连接配置信息

        //创建properties对象
        Properties properties = new Properties();
        String realPath = getServletContext().getRealPath("/WEB-INF/JDBC.properties");

        //获取properties对应的输入流
        InputStream in = new FileInputStream(realPath);
        //加载properties的输入流
        properties.load(in);
        //读取properties文件中的配置信息
        String driver = properties.getProperty("driver");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String jdbcUrl = properties.getProperty("jdbcUrl");

        System.out.println(driver);
        System.out.println(user);
        System.out.println(password);
        System.out.println(jdbcUrl);


//        String driver = "com.mysql.jdbc.Driver";
//        String jdbcUrl = "jdbc:mysql://10.100.157.78:3500/TCFlyIntOAG";
//        String user="TCFlyIntOAG";
//        String password = "nKL39Q2iOD94sxSgqlzL";
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
    public  void  release(Connection conn,Statement sta,ResultSet res){
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
