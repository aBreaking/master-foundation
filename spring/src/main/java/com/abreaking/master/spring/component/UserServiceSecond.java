package com.abreaking.master.spring.component;

import org.springframework.stereotype.Component;

/**
 * @{USER}
 * @{DATE}
 */
@Component
public class UserServiceSecond implements UserService {

    @Override
    public void print() {
        System.out.println("second");
    }
}