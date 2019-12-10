package com.abreaking.master.spring.ipit.bpp;

import com.abreaking.master.spring.ipit.bpp.component.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * BeanPostProcessor 的作用：在 Bean 完成实例化后，如果我们需要对其进行一些配置、增加一些自己的处理逻辑，那么请使用 BeanPostProcessor。
 * 针对的是由的bean
 * @author liwei_paas
 * @date 2019/12/5
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor  {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Runnable){
            ((Runnable)bean).run();
        }
        if (bean instanceof Serializable){
            System.out.println(beanName + " is Serializable");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+"->postProcessAfterInitialization");
        return bean;
    }

    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext("com.abreaking.master.spring.ipit.bpp");
        System.out.println("bean初始化前后就是执行定义的BeanPostProcessor的方法");
    }
}
