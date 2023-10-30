package com.cn.jmw.jmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 一只小小狗
 * @version 1.0.0
 * @ClassName SpringBootDemoActiviti7Application.java
 * @Description
 * @createTime 2023年02月21日 01:30:00
 */
@SpringBootApplication
@RestController
@EnableScheduling
public class SpringBootDemoActiviti7Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoActiviti7Application.class, args);
    }

}
