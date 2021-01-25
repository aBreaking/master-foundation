package com.abreaking.master.spring.prototype;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring的多例确认下
 * @author liwei_paas
 * @date 2021/1/25
 */
@Component
public class PrototypeMaster {

    @Resource(name = "po2")
    private PrototypeObject po;

    public void say(){
        System.out.println(po.getName()+"->"+po+"->"+Thread.currentThread().getName());
    }
}
