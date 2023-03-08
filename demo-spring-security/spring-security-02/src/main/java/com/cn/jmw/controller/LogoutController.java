package com.cn.jmw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @RequestMapping("logout.html")
    public String logout() {
        return "logout";
    }
}
