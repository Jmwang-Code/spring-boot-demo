package com.cn.jmw.controller;

import com.cn.jmw.authentication.AuthenticationIm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class HelloController {

    private final AuthenticationIm authenticationIm;

    public HelloController(AuthenticationIm authenticationIm) {
        this.authenticationIm = authenticationIm;
    }

    @RequestMapping("/hello")
    public String hello() {
        Authentication authentication = authenticationIm.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
        Object principal = authentication.getPrincipal();
        boolean authenticated = authentication.isAuthenticated();
        String name = authentication.getName();
        System.out.println("hello security");
        return "hello spring security";
//        AuthenticationManager
    }
    
}
