package com.abreaking.master.spring.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class BeanTest {

    @Resource
    MyBeanWarpper myBeanWarpper;
    @Test
    public void test01(){
        System.out.println(myBeanWarpper);
    }
}
