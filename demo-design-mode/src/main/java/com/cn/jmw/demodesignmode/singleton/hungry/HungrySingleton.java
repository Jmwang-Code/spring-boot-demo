package com.cn.jmw.demodesignmode.singleton.hungry;

/**
 * @author jmw
 * @Description 饿汉式
 * @date 2023年03月19日 16:29
 * @Version 1.0
 */
public class HungrySingleton {

    private static final  HungrySingleton hungry = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance(){
        return hungry;
    }
}
