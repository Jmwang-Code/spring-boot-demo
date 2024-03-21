import factory.method.奔驰发动机工厂;
import factory.method.奥迪轮胎工厂;
import factory.奔驰零件;
import factory.single.简单工厂;
import org.junit.Test;
import prototype.Prototype;
import singleton.SingletonHunger;
import singleton.SingletonLazy;

import java.util.ArrayList;
import java.util.List;

public class TestDesignMode {

    int[] arr = new int[]{1,2,3};

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
    public void testSingleFactory(){
        奔驰零件 汽车轮胎 = 简单工厂.createProduct("汽车轮胎");
        汽车轮胎.operation();

        奔驰零件 汽车发动机 = 简单工厂.createProduct("汽车发动机");
        汽车发动机.operation();
    }

    @Test
    public void testFactoryMethod(){
        new 奔驰发动机工厂().createProduct().operation();

        new 奥迪轮胎工厂().createProduct().operation();
    }
}
