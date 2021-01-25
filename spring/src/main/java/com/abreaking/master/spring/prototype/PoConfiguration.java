package com.abreaking.master.spring.prototype;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author liwei_paas
 * @date 2021/1/25
 */
@Configuration
public class PoConfiguration {

    @Bean("")
    @Scope("scope")
    public void bean(){

    }
}
