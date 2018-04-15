package com.rachel.jdbcdemo;

import org.junit.Test;

/**
 * Created by hd48552 on 2018/4/15.
 */
public class transactiondemo1Test {
//    创建单元测试类
//    勾选需要测试的方法
//    创建测试类对象，调用方法，进行测试
    transactiondemo1  tran = new transactiondemo1();
    @Test
    public void testTransaction() throws Exception {

        tran.testTransaction();

    }

}