package com.cn.jmw.demodesignmode.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tom.
 */

//Spring中的做法，就是用这种注册式单例
public class ContainerSingleton {
    private ContainerSingleton(){}
    private static Map<String,Object> ioc = new ConcurrentHashMap<String,Object>();
    public static Object getInstance(String className){
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object obj = null;
                try {
                    obj = Class.forName(className).newInstance();
                    ioc.put(className, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return obj;
            } else {
                return ioc.get(className);
            }
        }
    }

    public static void main(String[] args) {
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                Object instance = ContainerSingleton.getInstance("com.cn.jmw.demodesignmode.singleton.register.Pojo");
                System.out.println(instance);
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                Object instance = ContainerSingleton.getInstance("com.cn.jmw.demodesignmode.singleton.register.Pojo");
                System.out.println(instance);
            }
        };
        Thread thread = new Thread() {
            @Override
            public void run() {
                Object instance = ContainerSingleton.getInstance("com.cn.jmw.demodesignmode.singleton.register.Pojo");
                System.out.println(instance);
            }
        };
        thread.start();
        thread1.start();
        thread2.start();
    }
}

class Pojo{

}