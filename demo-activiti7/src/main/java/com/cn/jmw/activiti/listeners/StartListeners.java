package com.cn.jmw.activiti.listeners;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 流程开始监听器
 */
@Component
@Slf4j
public class StartListeners implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        log.info("流程开始监听器");
    }
}
