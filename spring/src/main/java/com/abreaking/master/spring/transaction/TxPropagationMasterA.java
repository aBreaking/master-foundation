package com.abreaking.master.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务隔离级别
 * learn from : {@href https://snailclimb.gitee.io/javaguide/#/docs/system-design/framework/spring/spring-transaction}
 * @author liwei_paas
 * @date 2020/7/14
 */
@Component
@EnableTransactionManagement //相当于配置文件的spring-tx配置，springboot自动包括该注解
public class TxPropagationMasterA {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //外部类，引用其外部方法
    @Autowired
    TxPropagationMasterB b;

    /**
     * 使用的最多的一个事务传播行为，我们平时经常使用的@Transactional注解默认使用就是这个事务传播行为。如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。也就是说：
     *
     * 如果外部方法没有开启事务的话，Propagation.REQUIRED修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     * 如果外部方法开启事务并且被Propagation.REQUIRED的话，所有Propagation.REQUIRED修饰的内部方法和外部方法均属于同一事务 ，只要一个方法回滚，整个事务均回滚。
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void requiredA(){

        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");
        b.requiredB();
    }

    /**
     * 或者将异常放在方法B中，就算方法A对异常进行了try catch处理，方法B回滚了，那么方法A也一样会回滚。
     * 因为A B 用的是同一个事务
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void requiredAWithCatchException(){

        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");
        try{
            //将异常放在B中，就算异常捕获了，A还是会回滚
            b.requiredBWithException();
        }catch (Throwable e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * 创建一个新的事务，如果当前存在事务，则把当前事务挂起。也就是说不管外部方法是否开启事务，Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void requiredNewA(){
        b.requiredNewB();
        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");
        //除于0 ，制造一个程序异常
        int s = 3/0;
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public void requiredNewAWithCatchException(){
        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");
        try{
            b.requiredNewBWithException();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *它大部分情况都等价于Propagation.REQUIRS_NEW，但是：
     * 如果主事务回滚了，那么NESTED也会回滚。
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void nestedA(){
        b.nestedB();
        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");
        int s = 3/0;
    }

    /**
     * mandatory：强制性
     * 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void mandatoryA(){
        b.mandatoryB();
        jdbcTemplate.update("insert into user(id,name) value (1,'zhangsan')");

    }
}
