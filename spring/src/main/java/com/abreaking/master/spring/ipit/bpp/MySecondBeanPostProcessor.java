package com.abreaking.master.spring.ipit.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;


/**
 * 研究下BeanPostProcessor的顺序
 * @author liwei_paas
 * @date 2019/12/5
 */
@Configuration
public class MySecondBeanPostProcessor implements BeanPostProcessor,PriorityOrdered {

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

    //这里设置一个排序值，让该BeanPostProcessor最先被执行
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
