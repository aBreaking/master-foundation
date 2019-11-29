package com.abreaking.master.spring.component;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @{USER}
 * @{DATE}
 */
@Component
public class RecycleDIFirst {

    @Resource
    RecycleDISecond second;

    public void print() {
        System.out.println("first");
        second.print();
    }
}
