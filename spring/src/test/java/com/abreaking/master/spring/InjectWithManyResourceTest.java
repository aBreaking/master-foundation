package com.abreaking.master.spring;

import com.abreaking.master.spring.component.RecycleDIFirst;
import com.abreaking.master.spring.component.UserService;
import com.abreaking.master.spring.prototype.PrototypeMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * 有多个resource 这时会怎么样？
 * @author liwei_paas
 * @date 2019/11/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class InjectWithManyResourceTest{

    /**
     * 存在多个实现类的compoenent时：
     * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.abreaking.master.spring.InjectWithManyResouce': Injection of resource dependencies failed; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.abreaking.master.spring.component.UserService' available: expected single matching bean but found 2: userServiceFirst,userServiceSecond
     */

    @Resource
    PrototypeMaster prototypeMaster;

    @Test
    public void test02() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(()->prototypeMaster.say()).start();
        }
        Thread.currentThread().join();
    }



}