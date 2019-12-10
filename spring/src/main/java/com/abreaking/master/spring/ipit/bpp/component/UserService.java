package com.abreaking.master.spring.ipit.bpp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * BeanPostProcessor 测试bean
 * @author liwei_paas
 * @date 2019/12/10
 */
@Component
public class UserService implements Runnable{

    @Value("zhangsan")
    String name;

    @Override
    public void run() {
        System.out.println("UserService is running");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
