package com.abreaking.master.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author liwei_paas
 * @date 2020/7/14
 */
@Component
@EnableTransactionManagement
public class TxPropagationMasterB {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(propagation=Propagation.REQUIRED)
    public void requiredB(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void requiredBWithException(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
        //除于0 ，制造一个程序异常
        int s = 3/0;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void requiredNewB(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void requiredNewBWithException(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
        //除于0 ，制造一个程序异常
        int s = 3/0;
    }

    @Transactional(propagation=Propagation.NESTED)
    public void nestedB(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
    }

    @Transactional(propagation=Propagation.NESTED)
    public void nestedBWithException(){
        jdbcTemplate.update("insert into user(id,name) value (2,'lisi')");
        //除于0 ，制造一个程序异常
        int s = 3/0;
    }

    @Transactional(propagation=Propagation.MANDATORY)
    public void mandatoryB(){
        jdbcTemplate.update("insert into user(id,name) value (3,'wangwu')");
    }
}
