package com.abreaking.master.spring.ipit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试bean的InitializingBean接口
 * @author liwei_paas
 * @date 2019/12/13
 */
@Component
public class MyInitializingBean implements InitializingBean {

    @Value("zhangsan")
    String name;
    @Value("99")
    int age;

    final Map<String,String> cache = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet方法开始，先看看该bean已注入的属性：");
        System.out.println(this);

        System.out.println("进行一些自定义的操作，比如判断age大小，太大的age将其改变下");
        if (age>80){
            System.out.println("发现age大于80了，调整成默认的80");
            this.age = 80;
        }
        cache.put("name",name);
        cache.put("age",String.valueOf(age));
        System.out.println("afterPropertiesSet方法完毕！");
    }

    @Override
    public String toString() {
        return "MyInitializingBean{name='" + name + '\'' + ", age=" + age +", cache=" + cache +'}';
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyInitializingBean.class);
        MyInitializingBean bean = context.getBean(MyInitializingBean.class);
        System.out.println("bean初始化完成，该bean的属性如下：");
        System.out.println(bean);
    }
}
