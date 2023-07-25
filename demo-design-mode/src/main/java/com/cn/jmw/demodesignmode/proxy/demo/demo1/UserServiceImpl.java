package com.cn.jmw.demodesignmode.proxy.demo.demo1;

import lombok.SneakyThrows;

public class UserServiceImpl implements UserService{
    @Override
    public void login() throws InterruptedException {
        System.out.println("login");
        Thread.sleep(1500);
    }

    @Override
    public void delete() throws InterruptedException {
        System.out.println("delete");
        Thread.sleep(1000);
    }

    @Override
    public void query() throws InterruptedException {
        System.out.println("query");
        Thread.sleep(500);
    }
}
