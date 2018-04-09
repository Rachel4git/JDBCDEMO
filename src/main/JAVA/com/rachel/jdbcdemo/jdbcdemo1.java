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
        testjdbc();
}


    public  void testjdbc() throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from union_protocol where id <=12";

        //加载数据库驱动并注册
        try {
             connection = getConnection();
             statement = connection.createStatement();
             resultSet =statement.executeQuery(sql);

             while (resultSet.next()){
                 int id = resultSet.getInt(1);
                 String airline = resultSet.getString("airline");
                 String create_gmt = resultSet.getString("gmt_create");

                 System.out.println(id);
                 System.out.println(airline);
                 System.out.println(create_gmt);

             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            release(connection,statement,resultSet);
        }


    }



    public Connection getConnection() throws ClassNotFoundException, SQLException ,IOException{
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
        Class.forName(driver);
        connection = DriverManager.getConnection(jdbcUrl,user,password);
        return connection;
    }

    public  void  release(Connection conn,Statement sta,ResultSet res){

        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
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

        if(res!=null){
            try {
                res.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
}
