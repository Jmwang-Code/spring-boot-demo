package com.cn.jmw.aspect;
import org.aspectj.lang.JoinPoint;


public interface LogModuleHandler {

    void handle(JoinPoint joinPoint, Object request);
}