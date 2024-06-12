package com.cn.jmw.dependencyinjection;

/**
 * 这是名为Main的主类。
 * 它包含主方法，该方法创建了MyController的实例并调用了Injector的inject方法。
 */
public class Main {
    /**
     * 这是主方法。
     * 它创建了MyController的实例并调用了Injector的inject方法。
     * 然后它调用了MyController的doSomething方法。
     * @param args 命令行参数。
     * @throws InstantiationException 如果声明字段的类无法实例化。
     * @throws IllegalAccessException 如果字段不可访问。
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        MyController myController = new MyController();
        Injector.inject(myController);
        myController.doSomething();
    }
}