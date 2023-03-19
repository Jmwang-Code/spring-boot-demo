package com.cn.jmw.demodesignmode.singleton.lazy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jmw
 * @Description 内部构造类单例
 * @date 2023年03月19日 16:39
 * @Version 1.0
 *
 * 性能最优
 */
public class LazyInnerClassSingleton {

    private LazyInnerClassSingleton() {
        if (LazyHolder.LAZY!=null){
            throw new RuntimeException("不允许构建多个实例");
        }
    }

    //懒汉式单例
    //LazyHolder需要等到外部的逻辑方法调用时才执行
    //JVM底层的执行逻辑，完美的避免了线程安全问题
    public static LazyInnerClassSingleton getInstance() {
        return LazyHolder.LAZY;
    }

    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();

        private Object reResolve(){
            return LAZY;
        }
    }

}

class LazyInnerClassSingletonTest{

    public static void main(String[] args) {
        try {
            Class<LazyInnerClassSingleton> lazyInnerClassSingletonClass = LazyInnerClassSingleton.class;
            Constructor<LazyInnerClassSingleton> declaredConstructor = lazyInnerClassSingletonClass.getDeclaredConstructor(null);
            declaredConstructor.setAccessible(true);
            LazyInnerClassSingleton lazyInnerClassSingleton = declaredConstructor.newInstance();

            LazyInnerClassSingleton instance = LazyInnerClassSingleton.getInstance();

            System.out.println(lazyInnerClassSingleton == instance);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}