package com.abreaking.master.spring.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 简单的bean，验证属性注入，当指定属性值与属性类型不一致会怎么样？
 * @author liwei_paas
 * @date 2019/12/9
 */
@Component
public class MyBeanWarpper {

    @Value("zhangsan")
    String name;

    /**
     * 类型转换错误
     */
    @Value("18")
    int age;

    @Override
    public String toString() {
        return "MyBeanWarpper{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
