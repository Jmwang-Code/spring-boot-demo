package singleton;

import prototype.Prototype;

public class SingletonLazy {

    //懒汉式
    private static volatile Prototype singletonHunger = null;

    SingletonLazy(){};

    public static Prototype getInstance(){
        if (singletonHunger==null){
            synchronized (Prototype.class){
                if (singletonHunger==null){
                    singletonHunger = new Prototype();
                }
            }
        }
        return singletonHunger;
    }
}
