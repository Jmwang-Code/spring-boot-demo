package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GenericJDKDynamicProxyHandler implements InvocationHandler {
    private Object target;

    public GenericJDKDynamicProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK 动态代理：开始前置操作");
        Object result = method.invoke(target, args);
        System.out.println("JDK 动态代理：执行后置操作");
        return result;
    }

}
