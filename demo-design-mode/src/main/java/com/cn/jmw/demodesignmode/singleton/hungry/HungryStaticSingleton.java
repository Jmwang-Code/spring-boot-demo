package com.cn.jmw.demodesignmode.singleton.hungry;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月19日 16:30
 * @Version 1.0
 */
public class HungryStaticSingleton {

    private static final HungryStaticSingleton hungry;

    static {
        hungry = new HungryStaticSingleton();
    }

    private HungryStaticSingleton(){}

    public static HungryStaticSingleton getInstance(){
        return hungry;
    }

}
