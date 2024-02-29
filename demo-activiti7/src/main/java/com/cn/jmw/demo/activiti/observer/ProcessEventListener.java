package com.cn.jmw.demo.activiti.observer;

public interface ProcessEventListener {
    void onEvent(String eventType);
}