package com.abreaking.master.spring.ipit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 进行单个bean的测试
 * @author liwei_paas
 * @date 2019/12/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class IpitTest {

    @Test
    public void testMyApplicationAware(){
        getBean(MyApplicationAware.class).display();
    }

    @Test
    public void testMyBeanPostProcessor(){
        //在applicationContext，会自动将定义的BeanPostProcessor bean注册到AbstractBeanFactory，针对所有的bean都生效
    }


    public static <T> T getBean(Class<T> beanClass){
        ApplicationContext applicationContext;
        applicationContext = new AnnotationConfigApplicationContext(beanClass);
        return (T) applicationContext.getBean(beanClass);
    }
}
