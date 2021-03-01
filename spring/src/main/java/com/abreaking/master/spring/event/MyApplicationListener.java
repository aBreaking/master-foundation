package com.abreaking.master.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author liwei
 * @date 2021/3/1
 */
@Component
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MyEvent){
            MyEvent myEvent = (MyEvent) event;
            System.out.println("获取到自定义的事件信息，内容为："+myEvent.getSource());
        }
    }
}
