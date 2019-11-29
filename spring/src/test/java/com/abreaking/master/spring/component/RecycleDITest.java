package com.abreaking.master.spring.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 循环的依赖注入怎么看？
 * spring 自动能够解决这种循环依赖的
 * 循环依赖的解决见：http://svip.iocoder.cn/Spring/IoC-get-Bean-createBean-5/
 * @author liwei_paas
 * @date 2019/11/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class RecycleDITest {

    @Resource
    RecycleDIFirst first;

    @Test
    public void test01(){
        first.print();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:*.xml");
        RecycleDIFirst first = context.getBean(RecycleDIFirst.class);
        first.print();
    }
}
