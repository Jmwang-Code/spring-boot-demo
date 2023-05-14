package com.cn.jmw.controller;

import com.cn.jmw.annotation.JMWAutowired;
import com.cn.jmw.annotation.JMWController;
import com.cn.jmw.annotation.JMWRequestMapping;
import com.cn.jmw.annotation.JMWRequestParam;
import com.cn.jmw.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月12日 14:17
 * @Version 1.0
 */
@JMWController
@JMWRequestMapping("/test")
public class TestController {

    @JMWAutowired
    private TestService testService;

    @JMWRequestMapping("/name")
    public String test(@JMWRequestParam("myName") String myName, HttpServletRequest req, HttpServletResponse resp){
        return testService.myAnnotation(myName);
    }
}
