package com.cn.jmw.demo.config;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class ActivitiTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("ActivitiTaskListener.notify");
    }
}
