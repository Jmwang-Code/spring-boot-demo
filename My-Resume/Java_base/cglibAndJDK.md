# 动态代理是什么？

- 动态代理是一种用于扩展对象功能的技术。它可以在运行时为一个对象创建代理,并通过代理对象对方法进行增强。


# JDK动态代理 VS CGLIB动态代理

- JDK动态代理只能代理实现了接口的类（`Proxy`） <span style="color:yellow;">VS</span> CGLIB可以代理未实现接口的类。（`Enhancer`）
- JDK动态代理是通过反射来实现的 <span style="color:yellow;">VS</span> CGLIB是通过生成字节码来实现的。
- JDK动态代理（直接写Class字节码）创建效率高，通过反射机制调用,执行效率低 <span style="color:yellow;">VS</span> CGLIB（ASM框架写Class字节码）创建效率低，CGLIB（FastClass机制直接调用）执行效率高。

# JDK动态代理

```java
//目标接口
public interface Target {
    void save();
}
//目标对象
public class TargetImpl implements Target {
    @Override
    public void save() {
        System.out.println("Save method");
    }
}  
//代理处理器
public class JDKProxyHandler implements InvocationHandler {
    private Object target;

    public JDKProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Start proxy");
        Object result = method.invoke(target, args);
        System.out.println("End proxy");
        return result;
    }
}
```

# CGLIB动态代理

```java
//目标对象
public class Target {
    public void save() {
        System.out.println("Save method");
    }
}
//代理创建器
public class CglibProxyFactory {
    public static Object getProxy(Target target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("Start proxy");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("End proxy");
                return result;
            }
        });
        return enhancer.create();
    }
} 
```

# 动态代理的应用场景

| \   | 使用JDK动态代理的技术     | 使用CGLIB动态代理的技术  |
| --- | ---------------- | --------------- |
| 1   | Spring注解@Service | 注解@Aspect       |
| 2   | 自定义日志收集、慢函数计算时间  | 自定义日志收集、慢函数计算时间 |