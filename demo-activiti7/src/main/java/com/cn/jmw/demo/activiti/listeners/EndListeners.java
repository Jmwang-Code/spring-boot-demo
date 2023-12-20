package com.cn.jmw.demo.activiti.listeners;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 流程结束监听器
 */
@Component
@Slf4j
public class EndListeners implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        log.info("流程结束监听器");
    }
}
