package com.cn.jmw.activiti.observer;

public class EmailNotificationListener implements ProcessEventListener {
    @Override
    public void onEvent(String eventType) {
        // 发送邮件通知
        System.out.println("Sending email notification for event: " + eventType);
    }
}