package com.rachel.jdbcdemo;

import org.junit.Test;

import java.sql.Connection;

/**
 * Created by hd48552 on 2018/4/23.
 */
public class UnionProtocolDAOTest extends JDBCDAO {
    @Test
    public void queryvalue() throws Exception {
    }

    @Test
    public void queryall() throws Exception {
    }

    @Test
    public void queryone() throws Exception {
    }

    @Test
    public void update() throws Exception {
        Connection conn = dbutilsdemo1.getC3P0();
        String sql = "update union_protocol set airline = ? where id = ?";
        new UnionProtocolDAO().update(conn,sql,"MU",1);
    }

}