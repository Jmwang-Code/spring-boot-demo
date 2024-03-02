package com.cn.jmw.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@Order(3)
public class LoggingAspect {

    @Pointcut("@within(com.cn.jmw.aspect.Loggable) || @annotation(com.cn.jmw.aspect.Loggable)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void logMethodCall(JoinPoint joinPoint) {
        jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        //获取参数
        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);
        ClueLogEnum value = loggable.value();

        // TODO test
        log.info("------------------Start.DBXSLogEnum: {}", value.name());
    }

    @AfterReturning(pointcut = "pointCut()")
    public void logMethodReturn(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);
        ClueLogEnum value = loggable.value();

        // TODO test
        log.info("------------------End.DBXSLogEnum: {}", value.name());
    }
}
