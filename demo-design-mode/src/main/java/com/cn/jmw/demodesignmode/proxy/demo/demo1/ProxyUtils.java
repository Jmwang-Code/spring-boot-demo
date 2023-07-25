package com.cn.jmw.demodesignmode.proxy.demo.demo1;

import java.lang.reflect.Proxy;

public class ProxyUtils {

    public static UserService getProxy(UserService service){
        UserService re = (UserService) Proxy.newProxyInstance(ProxyUtils.class.getClassLoader(), UserServiceImpl.class.getInterfaces(), (proxy, method, args) -> {
            long l = System.currentTimeMillis();
            Object invoke = method.invoke(service, args);
            System.out.println((System.currentTimeMillis()-l)/1000.0);
            return invoke;
        });
        return re;
    }

    public static void main(String[] args) throws InterruptedException {
        UserService proxy = ProxyUtils.getProxy(new UserServiceImpl());
        proxy.login();
        proxy.delete();
        proxy.query();
    }
}
