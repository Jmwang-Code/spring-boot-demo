package com.cn.jmw.demodesignmode.prototype.simple;

/**
 * Created by Tom.
 */
public class Client {

    private Prototype prototype;

    public Client(Prototype prototype){
        this.prototype = prototype;
    }
    public Prototype startClone(Prototype concretePrototype){
        return (Prototype)concretePrototype.clone();
    }

}
