package com.cn.jmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月12日 15:33
 * @Version 1.0
 */
@SpringBootApplication
public class JMW {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
//        applicationContext = SpringApplication.run(JMW.class, args);
        //1.入口
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

    }
}
