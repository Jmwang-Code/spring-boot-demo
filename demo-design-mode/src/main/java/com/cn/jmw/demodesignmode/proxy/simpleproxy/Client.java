package com.cn.jmw.demodesignmode.proxy.simpleproxy;

/**
 * Created by Tom.
 */
public class Client {

    public static void main(String[] args) {

        Proxy proxy = new Proxy(new RealSubject());
        proxy.request();

    }

}
