package com.cn.jmw.demodesignmode.singleton.lazy;

/**
 * @author jmw
 * @Description 双重检查单例
 * @date 2023年03月19日 16:39
 * @Version 1.0
 */
public class LazyDoubleCheckSingleton {

    private volatile static LazyDoubleCheckSingleton lazySingleton;

    private LazyDoubleCheckSingleton() {

    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (lazySingleton == null) {
            synchronized (lazySingleton) {
                if (lazySingleton == null) {
                    lazySingleton = new LazyDoubleCheckSingleton();
                    //CPU直线会转换成JVM指令执行
                    //1.分配内存给对象
                    //2.初始化对象
                    //3.初始化号的的对象和内存地址简历关联
                    //4.用户初次访问
                    //解决指令重排volatile
                }
            }
        }
        return lazySingleton;
    }

}
