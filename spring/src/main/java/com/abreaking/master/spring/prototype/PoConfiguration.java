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
        MyServiceA myServiceA = new MyServiceA(null);
        System.out.println("myServiceA"+myServiceA);
        return myServiceA;
    }

    /**
     * 它会自动注入poBean的
     * @param poBean
     * @return
     */
    @Bean
    public MyServiceB beanB(PrototypeObject poBean){
        MyServiceB myServiceB = new MyServiceB(poBean);
        System.out.println(",B->"+myServiceB+",po->"+poBean);
        return myServiceB;
    }

    /**
     * 会自动注入myServiceA
     * @param myServiceA
     * @return
     */
    @Bean
    public PrototypeObject poBean(MyServiceA myServiceA){
        PrototypeObject poBean = new PrototypeObject();
        System.out.println("A->"+myServiceA+",po->"+poBean);
        return poBean;
    }
}
