package singleton;

import prototype.Prototype;

public class SingletonHunger {

    //饿汉式
    private static Prototype singletonHunger = new Prototype();

    public SingletonHunger(){};

    public static synchronized Prototype getInstance(){
        return singletonHunger;
    }
}
