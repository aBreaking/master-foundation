package com.abreaking.master.spring;

import com.abreaking.master.spring.component.RecycleDIFirst;
import com.abreaking.master.spring.component.UserService;
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
public class InjectWithManyResource{

    /**
     * 存在多个实现类的compoenent时：
     * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.abreaking.master.spring.InjectWithManyResouce': Injection of resource dependencies failed; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.abreaking.master.spring.component.UserService' available: expected single matching bean but found 2: userServiceFirst,userServiceSecond
     */
    @Autowired
    UserService autoUserService;

    @Resource
    UserService resourceUserService;

    @Resource(name = "userServiceSecond")
    UserService userServiceSecond;

    @Test
    public void test01(){
        autoUserService.print();
        resourceUserService.print();
        userServiceSecond.print();
    }

    @Resource
    RecycleDIFirst recycleDIFirst;
    /**
     * 循环的依赖注入怎么看？
     */
    @Test
    public void testRecycleDI(){
        recycleDIFirst.print();
    }
}
