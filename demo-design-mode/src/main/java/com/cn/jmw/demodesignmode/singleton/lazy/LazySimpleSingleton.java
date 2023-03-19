package com.cn.jmw.demodesignmode.singleton.lazy;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月19日 16:39
 * @Version 1.0
 */
public class LazySimpleSingleton {

    private volatile static LazySimpleSingleton lazySimpleSingleton;

    private LazySimpleSingleton() {
    }

    public synchronized static LazySimpleSingleton getInstance() {
        if (lazySimpleSingleton == null) {
            lazySimpleSingleton = new LazySimpleSingleton();
        }
        return lazySimpleSingleton;
    }

}
