package com.cn.jmw.activiti.observer;

public interface ProcessEventListener {
    void onEvent(String eventType);
}