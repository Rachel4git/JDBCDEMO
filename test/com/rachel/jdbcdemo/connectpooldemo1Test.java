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
}