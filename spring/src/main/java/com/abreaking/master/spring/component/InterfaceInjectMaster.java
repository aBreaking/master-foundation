package com.abreaking.master.spring.component;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 讨论接口注入
 * @author liwei_paas
 * @date 2019/11/29
 */
@Component
public class InterfaceInjectMaster {

    @Resource(name = "userServiceFirst")
    UserService userService;

    public void print(){
        userService.print();
    }
}
