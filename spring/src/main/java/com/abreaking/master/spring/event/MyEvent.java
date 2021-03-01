package com.abreaking.master.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * spring 事件原理研究
 * 参考：https://www.cnblogs.com/youzhibing/p/9593788.html
 * @author liwei
 * @date 2021/3/1
 */
public class MyEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }
}
