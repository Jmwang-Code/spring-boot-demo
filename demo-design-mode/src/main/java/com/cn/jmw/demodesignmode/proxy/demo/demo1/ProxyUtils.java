package com.cn.jmw.demodesignmode.proxy.demo.demo1;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

        Map Map = new HashMap();
        Map.put(null,"1235");
        System.out.println(Map.get(null));
        Map.put(1,null);
        Map.put(2,null);
        System.out.println(Map.get(1));
        System.out.println(Map.get(2));

        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
//        objectObjectConcurrentHashMap.put(null,"123");
//        System.out.println(objectObjectConcurrentHashMap.get(null));
        objectObjectConcurrentHashMap.put(123,null);
        System.out.println(objectObjectConcurrentHashMap.get(123));
    }
}
