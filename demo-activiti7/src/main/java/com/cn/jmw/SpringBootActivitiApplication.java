package com.cn.jmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 一只小小狗
 * @version 1.0.0
 * @ClassName SpringBootDemoHelloworldApplication.java
 * @Description
 * @createTime 2023年02月21日 01:30:00
 */
//扫描package
@SpringBootApplication(scanBasePackages = "com.cn")
public class SpringBootActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootActivitiApplication.class, args);
    }

}
