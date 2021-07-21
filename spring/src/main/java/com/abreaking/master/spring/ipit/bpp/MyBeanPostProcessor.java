package com.abreaking.master.spring.ipit.bpp;

import com.abreaking.master.spring.ipit.bpp.component.OtherService;
import com.abreaking.master.spring.ipit.bpp.component.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * BeanPostProcessor 的作用：在 Bean 完成实例化后，如果我们需要对其进行一些配置、增加一些自己的处理逻辑，那么请使用 BeanPostProcessor。
 * 针对的是由的bean
 * @author liwei_paas
 * @date 2019/12/5
 */
@Configuration
public class MyBeanPostProcessor implements BeanPostProcessor {

    //bean实例化之前进行定义的逻辑处理:针对不同接口进行不同处理
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println(beanName+" begin init");
        if (bean instanceof Runnable){
            ((Runnable)bean).run();
        }
        if (bean instanceof Serializable){
          //  System.out.println(beanName + " can be serialized");
        }
        return bean;
    }
    //bean实例化之后的逻辑处理
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println(beanName+" init complete");
        return bean;
    }

    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext("com.abreaking.master.spring.ipit.bpp");
        System.out.println("容器启动后，bean初始化后就会执行定义的BeanPostProcessor的方法");
    }
}
