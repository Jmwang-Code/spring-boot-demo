package singleton;

public class SingletonLazy {

    //懒汉式
    private static volatile SingletonLazy singletonHunger = null;

    SingletonLazy(){};

    public static SingletonLazy getInstance(){
        if (singletonHunger==null){
            synchronized (SingletonLazy.class){
                if (singletonHunger==null){
                    singletonHunger = new SingletonLazy();
                }
            }
        }
        return singletonHunger;
    }
}
