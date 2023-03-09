package com.cn.jmw.controller;

import com.cn.jmw.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/user")
    public String user() {
        //1.代码中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //2.通过获取用户信息
        User user = (User) authentication.getPrincipal();
        System.out.println("username= " + user.getUsername());
        System.out.println("authorities= " + user.getAuthorities());
        return "user ok";
    }
}
