package com.abreaking.master.spring.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author liwei
 * @date 2021/3/1
 */
@Component
public class MyEventService implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void sendMsg(String msg){
        MyEvent myEvent = new MyEvent(msg);
        applicationContext.publishEvent(myEvent);
    }
}
