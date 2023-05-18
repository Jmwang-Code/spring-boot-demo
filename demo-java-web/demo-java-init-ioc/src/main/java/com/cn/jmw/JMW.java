package com.cn.jmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
        applicationContext = SpringApplication.run(JMW.class, args);
        //1.入口
//        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Constructor_B constructorB = applicationContext.getBean("constructor_B", Constructor_B.class);
        Constructor_A constructorA = constructorB.getJmMapper();
        Constructor_B constructorB1 = constructorA.getJmwService();
        System.out.println(constructorB);

        SinA setting_sin_a = applicationContext.getBean(SinA.class);
        SinB setting_sin_b = setting_sin_a.getSinB();
        SinA setting_sin_a1 = setting_sin_b.getSinA();
        System.out.println();
        ProA proA = applicationContext.getBean("proA", ProA.class);
        ProB proB = proA.getProB();
        ProA proA1 = proB.getProA();

    }
}
