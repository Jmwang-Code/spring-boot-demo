# 动态代理是什么？
- 动态代理是一种用于扩展对象功能的技术。它可以在运行时为一个对象创建代理,并通过代理对象对方法进行增强。
JDK 动态代理:
1. 必须是接口,通过`InvokeHandler`来处理代理实例中方法调用。
2. 使用`Proxy`类来创建代理对象。
3. 只能对实现了接口的类生成代理,不能针对类。

CGLIB 动态代理:
1. 通过自定继承的方式实现,其原理是通过<span style="color:yellow">字节码技术</span>动态生成被代理类的子类。(不能对声明为final的方法进行代理)
2. 使用`Enhancer`类来创建代理对象。
3. 可以对未实现接口的类生成代理。

# JDK动态代理 VS CGLIB动态代理
- JDK动态代理只能代理实现了接口的类 <span style="color:yellow;">VS</span> CGLIB可以代理未实现接口的类。
- JDK动态代理是通过反射来实现的 <span style="color:yellow;">VS</span> CGLIB是通过生成字节码来实现的。
- JDK调用代理方法，是通过反射机制调用,执行效率低 <span style="color:yellow;">VS</span> CGLIB是通过FastClass机制直接调用方法，执行效率高。
- JDK（直接写Class字节码）创建效率高，CGLIB（ASM框架写Class字节码）创建效率低 <span style="color:yellow;">VS</span> JDK（反射）运行时效率低，CGLIB（FastClass机制直接调用）运行时的效率高。


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
| \   | 使用JDK动态代理的技术     | 使用CGLIB动态代理的技术 |
|-----|------------------|----------------|
| 1   | Spring注解@Service | 注解@Aspect      |
| 2   | 自定义日志收集、慢函数计算时间  | 自定义日志收集、慢函数计算时间            |