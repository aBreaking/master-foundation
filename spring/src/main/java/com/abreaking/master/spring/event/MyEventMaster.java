package com.abreaking.master.spring.event;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author liwei
 * @date 2021/3/1
 */
public class MyEventMaster {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyApplicationListener.class,MyEventService.class);
        MyEventService service = applicationContext.getBean(MyEventService.class);
        service.sendMsg("hello world");
    }
}
