package com.abreaking.master.spring.ipit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * from:http://svip.iocoder.cn/Spring/IoC-Aware-interface/
 * @author liwei_paas 
 * @date 2019/12/5
 */
@Component
public class MyApplicationAware implements BeanNameAware,BeanFactoryAware,BeanClassLoaderAware,ApplicationContextAware {

    private String beanName;

    private BeanFactory beanFactory;

    private ClassLoader classLoader;

    private ApplicationContext applicationContext;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("MyApplicationAware 调用了 BeanClassLoaderAware 的 setBeanClassLoader 方法");
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("MyApplicationAware 调用了 BeanFactoryAware 的 setBeanFactory 方法");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("MyApplicationAware 调用了 BeanNameAware 的 setBeanName 方法");
        this.beanName = name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("MyApplicationAware 调用了 ApplicationContextAware 的 setApplicationContext 方法");
        this.applicationContext = applicationContext;
    }

    public void display(){
        System.out.println("beanName:" + beanName);
        System.out.println("classloader:" + classLoader);
        System.out.println("is singleton：" + beanFactory.isSingleton(beanName));
        System.out.println("application context ：" + applicationContext.getEnvironment());
    }


    public static void main(String args[]){
        ApplicationContext context = null;
        //context = new ClassPathXmlApplicationContext("*.xml");
        //context = new AnnotationConfigApplicationContext("com.abreaking.master.spring.ipit");
        context = new AnnotationConfigApplicationContext(MyApplicationAware.class);

        MyApplicationAware bean = context.getBean(MyApplicationAware.class);
        bean.display();

    }
}
