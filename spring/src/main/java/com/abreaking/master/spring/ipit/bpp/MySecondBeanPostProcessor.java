package com.abreaking.master.spring.ipit.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 研究下BeanPostProcessor的顺序
 * @author liwei_paas
 * @date 2019/12/5
 */
@Configuration
public class MySecondBeanPostProcessor implements BeanPostProcessor {

    //bean实例化之前进行定义的逻辑处理:针对不同接口进行不同处理
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+" before in the MySecondBeanPostProcessor");
        return bean;
    }
    //bean实例化之后的逻辑处理
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+ " after in the MySecondBeanPostProcessor");
        return bean;
    }

    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext("com.abreaking.master.spring.ipit.bpp");
        System.out.println("容器启动后，bean初始化后就会执行定义的BeanPostProcessor的方法");
    }


    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
