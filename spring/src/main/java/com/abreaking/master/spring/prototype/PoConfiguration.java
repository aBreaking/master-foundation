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

    /**
     * A B 都依赖poBean，都会是同一个对象的
     */
    @Bean
    public MyServiceA beanA(){
        return new MyServiceA(poBean());
    }
    @Bean
    public MyServiceB beanB(){
        return new MyServiceB(poBean());
    }


    @Bean
    public PrototypeObject poBean(){
        return new PrototypeObject();
    }
}
