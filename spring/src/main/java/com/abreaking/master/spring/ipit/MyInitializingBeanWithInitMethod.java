package com.abreaking.master.spring.ipit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试bean的init-method方法。
 * 它不会实现任何spring接口与注解，与spring是完全解耦的。
 * @author liwei_paas
 * @date 2019/12/13
 */
public class MyInitializingBeanWithInitMethod {

    String name;
    Integer age;

    final Map<String,String> cache = new ConcurrentHashMap<>();

    /**
     * 自定义的初始化方法。
     * 最终效果同InitializingBean接口的afterPropertiesSet方法
     * @throws Exception
     */
    public void myInitMethod() throws Exception {
        System.out.println("myInitMethod方法开始，先看看该bean已注入的属性：");
        System.out.println(this);
        System.out.println("应该都是空的，这时我们可以手动来注入下属性");
        this.name = "lisi";
        this.age = 19;

        cache.put("name",name);
        cache.put("age",String.valueOf(age));
        System.out.println("myInitMethod方法完毕！");
    }

    @Override
    public String toString() {
        return "MyInitializingBeanWithInitMethod{name='" + name + '\'' + ", age=" + age +", cache=" + cache +'}';
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyInitializingConfiguration.class);
        MyInitializingBeanWithInitMethod bean = context.getBean(MyInitializingBeanWithInitMethod.class);
        System.out.println(bean);
    }

}
