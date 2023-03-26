package com.cn.jmw.demodesignmode.proxy.dynamic.jdkproxy;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@Slf4j
/**
 * Created by Tom on 2019/3/10.
 */
public class JDKMeipo implements InvocationHandler {

    private Object target;
    public Object getInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.target,args);
        after();
       return obj;
    }

    private void before(){
        log.info("我是媒婆，我要给你找对象，现在已经确认你的需求");
        log.info("开始物色");
    }

    private void after(){
        log.info("OK的话，准备办事");
    }
}
