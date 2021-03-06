package com.rachel.jdbcdemo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hd48552 on 2018/4/14.
 */
public class daodemo2 {


    public  static  void  main(String[] args) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
//        查询一条记录
//        String sql = "SELECT  * FROM  union_protocol where id = ?";
//        unionprotocol uni = null;
//        try {
//             uni = getone(unionprotocol.class,sql,5);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        System.out.println(uni);

//        查询多条记录
        String sql = "SELECT  * FROM  union_protocol where id = ?";
        List<unionprotocol> multiuni = null;
        try {
            multiuni = get(unionprotocol.class,sql,5);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        int i = 1;
        for(unionprotocol u :multiuni){

            System.out.println(i);
            System.out.println(u);
            i++;
        }

    }


    //查询

    public  static  <T> List<T>  get(Class<T> clazz,String SQL,Object... args) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> multientity = new ArrayList<T>();
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet res = null;

        try {
            //获取数据库连接
            conn = getConnection();
            pre = conn.prepareStatement(SQL);
            //填充可变参数
            for (int i=0;i<args.length;i++){
                pre.setObject(i+1,args[i]);
            }
            //执行SQL语句获取结果集
            res = pre.executeQuery();

            //如果结果集不为空，处理结果集，将其保存到map中
            List<Map<String,Object>>  ressset = resultset2maplis( res);


            //判断list大小是否为0，若不为0，则遍历map，将map中的key-value转化为实体属性
            multientity  = maplist2BeanList( clazz, ressset);
//            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release(conn,pre,res);
        }

        //返回T
        return  multientity;

    }



    //  将查询结果保存到maplist中
    public static List<Map<String,Object>> resultset2maplis(ResultSet res){
        List<Map<String,Object>> multis = new ArrayList<>();
        Map<String,Object> values = null;

        try {
            ResultSetMetaData remd = res.getMetaData();
            while (res.next()){
                values = new HashMap<>();
                for(int i =0;i<remd.getColumnCount();i++){
                    String name = remd.getColumnName(i+1);
                    Object value = res.getObject(name);
                    values.put(name,value);
                }
                multis.add(values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  multis;
    }

//    将maplist转化为beanlist
    public static  <T> List<T> maplist2BeanList(Class<T> clazz, List<Map<String,Object>> mapList) throws NoSuchFieldException, IllegalAccessException {
        List<T> beanList = new ArrayList<>();
        T entity = null;

        try {
            if (mapList.size() > 0) {
                for (Map<String, Object> m : mapList) {
                    entity = clazz.newInstance();
                    for (Map.Entry<String, Object> me : m.entrySet()) {
                        String propertyname = me.getKey();
                        Object propertyvalue = me.getValue();
                        Field f = clazz.getDeclaredField(propertyname);
                        f.setAccessible(true);
                        f.set(entity, propertyvalue);
                    }
                    beanList.add(entity);

                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return  beanList;
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
