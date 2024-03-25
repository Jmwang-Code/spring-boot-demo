package proxy;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBDynamicProxyHandler implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("CGLIB 动态代理：开始前置操作");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("CGLIB 动态代理：执行后置操作");
        return result;
    }
}