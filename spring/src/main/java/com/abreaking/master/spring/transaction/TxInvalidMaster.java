package com.abreaking.master.spring.transaction;

/**
 * 事务失效的原因分析
 * @author liwei_paas
 * @date 2020/7/14
 */

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Spring 事务失效的几种情况
 * 1、自调用
 * @author liwei_paas
 * @date 2020/7/14
 */
@Component
@EnableTransactionManagement
public class TxInvalidMaster {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void a(){
        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");
        b();
    }

    @Transactional
    public void b(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
        int s =3/0;
    }

    @Transactional
    protected void pA(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
        int s =3/0;
    }

    @Transactional(rollbackFor = SQLException.class)
    public void rollbackForA() throws IOException {
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
        throw new IOException("123");
    }
}
