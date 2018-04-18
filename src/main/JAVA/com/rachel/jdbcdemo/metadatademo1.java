package com.rachel.jdbcdemo;

import java.io.IOException;
import java.sql.*;


/**
 * Created by hd48552 on 2018/4/10.
 */
public class metadatademo1 {
    public static  void  main(String[] args){
//        metaDataTest();
        genkeytest();
    }

    public static  void  genkeytest(){
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet res = null;
        ResultSet res1 = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO `union_protocol` (airline,union_airline_amount) VALUES (\"TT\", 1)";
            sta = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//Statement.RETURN_GENERATED_KEYS返回自动生成的主键
            //通过 getGeneratedKeys() 获取包含了新生成的主键的 ResultSet 对象
            //在 ResultSet 中只有一列 GENERATED_KEY, 用于存放新生成的主键值.
            sta.executeUpdate();
            res1 = sta.getGeneratedKeys();
            while (res1.next()){
                System.out.println(res1.getObject(1));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release(conn,sta,res);
        }

        //Statement.RETURN_GENERATED_KEYS返回自动生成的主键


    }

    public  static  void  metaDataTest(){
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet res = null;
        String sql = null;
        java.sql.ResultSetMetaData resultSetMetaData = null;

        try {
            conn =getConnection();
            /**
             * 数据库元数据
             */
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            //DatabaseMetaData的方法
            int version = databaseMetaData.getDatabaseMajorVersion();
            String url = databaseMetaData.getURL();
            String username = databaseMetaData.getUserName();
            String driver = databaseMetaData.getDriverName();
            ResultSet cat = databaseMetaData.getCatalogs();

            System.out.println("------DatabaseMetaData------");
            System.out.println("version =" + version);
            System.out.println("url =" + url);
            System.out.println("username =" + username);
            System.out.println("driver =" + driver);
            while (cat.next()){
                System.out.println("cat =" + cat.getString(1));
            }



            sql = "select * from union_protocol where id <=12";
            sta = conn.prepareStatement(sql);
            res = sta.executeQuery(sql);

            /**
             * ResultSetMetaData
             */
            resultSetMetaData = res.getMetaData();
            //resultsetmatadata方法
            System.out.println("------resultsetmatadata方法------");
            int clu = resultSetMetaData.getColumnCount();
            for (int i = 0; i<clu-1;i++){
                String cluname = resultSetMetaData.getColumnName(i+1);
                String clutypename = resultSetMetaData.getColumnTypeName(i+1);
                System.out.println("cluname =" + cluname);
                System.out.println("clutypename =" + clutypename);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(conn,sta,res);
        }
    }


    /**
     * 获取数据库连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */

    public static  Connection getConnection() throws ClassNotFoundException, SQLException ,IOException{
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
    public static  void  release(Connection conn,Statement sta,ResultSet res){
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
