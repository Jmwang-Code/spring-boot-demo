package com.cn.jmw.service;

import com.cn.jmw.annotation.JMWService;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月12日 14:19
 * @Version 1.0
 */
@JMWService
public class TestServiceImpl implements TestService{
    @Override
    public String myAnnotation(String myName) {
        return "这是"+myName+"的IOC DI MVC";
    }
}
