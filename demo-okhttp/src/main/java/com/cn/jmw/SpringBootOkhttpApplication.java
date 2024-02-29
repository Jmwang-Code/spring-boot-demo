package com.cn.jmw;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootOkhttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOkhttpApplication.class, args);

        //虚拟线程
        Thread.startVirtualThread(()->{
            System.out.println("hello world");
        });
    }

}
