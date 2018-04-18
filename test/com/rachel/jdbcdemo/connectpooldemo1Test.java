package com.rachel.jdbcdemo;

import org.junit.Test;

/**
 * Created by hd48552 on 2018/4/17.
 */
public class connectpooldemo1Test {
    @Test
    public void getDBCPConn() throws Exception {
        new connectpooldemo1().getDBCPConn();
    }

    @Test
    public  void  getDBCPConn1() throws Exception {
        new connectpooldemo1().getDBCPConn1();
    }

    @Test
    public  void  getC3P0(){
        new connectpooldemo1().getC3P0();
    }
    @Test
    public  void  getC3P01(){
        new connectpooldemo1().getC3P01();
    }
}