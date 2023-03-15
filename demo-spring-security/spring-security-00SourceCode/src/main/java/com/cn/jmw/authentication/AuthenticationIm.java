package com.cn.jmw.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月15日 10:22
 * @Version 1.0
 */
@Component
public class AuthenticationIm {

    /**
     * 获取身份验证
     */
    public Authentication getAuthentication(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
}
