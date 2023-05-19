package com.cn.jmw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月19日 11:40
 * @Version 1.0
 */
public class EventApplicationListener implements ApplicationListener<RequestHandledEvent> {

    @Override
    public void onApplicationEvent(RequestHandledEvent event) {
        System.out.println("EventApplicationListener监听到的事件：" + event);
    }
}

class EventApplicationListener2 implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("EventApplicationListener2监听到的事件：" + event);
    }
}

class EventApplicationListener3 implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("EventApplicationListener3监听到的事件：" + event);
    }
}

class EventApplicationListener4 implements ApplicationListener<ContextStartedEvent> {

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("EventApplicationListener4监听到的事件：" + event);
    }
}

class EventApplicationListener5 implements ApplicationListener<ContextStoppedEvent> {

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        System.out.println("EventApplicationListener5监听到的事件：" + event);
    }
}