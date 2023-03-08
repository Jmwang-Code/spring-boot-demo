package com.cn.jmw.controller;

import com.cn.jmw.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello security");
        //1.获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println("身份信息: " + user.getUsername());
        System.out.println("权限信息: " + authentication.getAuthorities());
        new Thread(() -> {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("子线程获取: " + authentication1);
        }).start();
        return "hello security";
    }


}
