package com.cn.jmw.demo.aop;

import org.aspectj.lang.JoinPoint;

public interface WorkflowEnumInterface {

    void handle(JoinPoint joinPoint, Object request, Object result);
}
