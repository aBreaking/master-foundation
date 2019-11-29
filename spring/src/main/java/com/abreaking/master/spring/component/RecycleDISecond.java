package com.abreaking.master.spring.component;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @{USER}
 * @{DATE}
 */
@Component
public class RecycleDISecond {

    @Resource
    RecycleDIFirst first;

    public void print(){
        System.out.println("second");
    }
}
