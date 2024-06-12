package com.cn.jmw.dependencyinjection;

/**
 * 这是一个名为MyController的控制器类。
 * 它包含一个类型为MyService的字段，该字段标有Inject注解。
 * 它还包含一个名为doSomething的方法，该方法调用MyService的serve方法。
 */
public class MyController {
    @Inject
    private MyService myService;

    public void doSomething() {
        myService.serve();
    }
}