package com.cn.jmw;

import com.cn.jmw.service.HerService;
import com.cn.jmw.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBeanLifeCycleApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBeanLifeCycleApplication.class, args);
        MyService bean = run.getBean(MyService.class);
        bean.hello();
        HerService bean1 = run.getBean(HerService.class);
        bean1.hello();
    }

}
