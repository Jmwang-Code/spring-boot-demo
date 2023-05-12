package com.cn.jmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月12日 15:33
 * @Version 1.0
 */
@SpringBootApplication
public class JMW {

    public static void main(String[] args) {
        SpringApplication.run(JMW.class,args);
        JMWDispatcherServlet jmwDispatcherServlet = new JMWDispatcherServlet();
        jmwDispatcherServlet.init();
    }
}
