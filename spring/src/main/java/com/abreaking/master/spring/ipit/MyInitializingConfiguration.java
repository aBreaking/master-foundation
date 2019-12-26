package com.abreaking.master.spring.ipit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean的生产地
 * @author liwei_paas
 * @date 2019/12/13
 */
@Configuration
public class MyInitializingConfiguration {
    /**
     * bean的创建时指定init-method方法
     * @return
     */
    @Bean(initMethod = "myInitMethod")
    public MyInitializingBeanWithInitMethod bean(){
        return new MyInitializingBeanWithInitMethod();
    }
}
