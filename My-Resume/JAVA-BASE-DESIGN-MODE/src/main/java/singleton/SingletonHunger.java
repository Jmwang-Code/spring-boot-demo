package singleton;

public class SingletonHunger {

    //饿汉式
    private static SingletonHunger singletonHunger = new SingletonHunger();

    public SingletonHunger(){};

    public static synchronized SingletonHunger getInstance(){
        return singletonHunger;
    }
}
