import factory.method.奔驰发动机工厂;
import factory.method.奥迪轮胎工厂;
import factory.奔驰零件;
import factory.single.简单工厂;
import org.junit.Test;
import prototype.Prototype;
import proxy.AbstractObject;
import proxy.GenericJDKDynamicProxyHandler;
import proxy.ProxyObject;
import proxy.RealObject;
import singleton.SingletonHunger;
import singleton.SingletonLazy;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class TestDesignMode {

    /**
     * TODO 单例模式
     */
    @Test
    public void testSingletonHunger() {
        // 创建多个线程，尝试获取 SingletonHunger 的实例
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Prototype instance = SingletonHunger.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + instance);
            }).start();
        }

        // 等待一段时间，确保所有线程都执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingletonLazy() {
        // 创建多个线程，尝试获取 SingletonLazy 的实例
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Prototype instance = SingletonLazy.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + instance);
            }).start();
        }

        // 等待一段时间，确保所有线程都执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO 工厂模式
     */
    @Test
    public void testSingleFactory() {
        奔驰零件 汽车轮胎 = 简单工厂.createProduct("汽车轮胎");
        汽车轮胎.operation();

        奔驰零件 汽车发动机 = 简单工厂.createProduct("汽车发动机");
        汽车发动机.operation();
    }

    @Test
    public void testFactoryMethod() {
        new 奔驰发动机工厂().createProduct().operation();

        new 奥迪轮胎工厂().createProduct().operation();
    }

    /**
     * TODO 原型模式
     */
    @Test
    public void testClone() {
        Prototype instance = SingletonHunger.getInstance();
        Prototype clone = instance.clone();

        System.out.println(clone == instance);//false
        System.out.println(clone.arr == instance.arr);//true
    }

    @Test
    public void testDeepClone() {
        Prototype instance = SingletonHunger.getInstance();
        Prototype prototype = instance.deepClone();

        System.out.println(instance == prototype);//false
        System.out.println(prototype.arr == instance.arr);//false
    }

    //代理模式
    @Test
    public void testProxy() throws Throwable {
        //静态代理
        //1.真实对象
        RealObject realObject = new RealObject();
        //2.代理对象
        ProxyObject proxyObject = new ProxyObject(realObject);
        //3.代理对象调用方法
        proxyObject.operation();

        //JDK动态代理
        new GenericJDKDynamicProxyHandler(realObject).invoke(realObject, RealObject.class.getMethod("operation"), null);

    }

}


