package com.cn.jmw.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
@Order(1)
public class WorkflowAspect {

    //通过ThreadLocal存储校验信息
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@within(com.cn.jmw.aop.Workflow) || @annotation(com.cn.jmw.aop.Workflow)")
    public void pointCut() {}

    @Before(value = "pointCut()")
    public void logMethodCall(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        //获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Workflow loggable = signature.getMethod().getAnnotation(Workflow.class);

        //获取类上的注解
        WorkflowEnum value = null;
        if (loggable!=null){
            value = loggable.value();
        }else {
            Workflow annotation = joinPoint.getTarget().getClass().getAnnotation(Workflow.class);
            value = annotation.value();
        }

        //初始化
        value.init();
        value.handle(joinPoint,request,null);

        log.info("------------------日志信息校验: {}", value.name());
    }

    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Workflow loggable = signature.getMethod().getAnnotation(Workflow.class);
        Workflow annotation = joinPoint.getTarget().getClass().getAnnotation(Workflow.class);

        startTime.remove();
    }
}
