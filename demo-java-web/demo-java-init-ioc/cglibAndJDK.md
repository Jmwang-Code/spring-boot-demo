# 动态代理是什么？
- 动态代理是一种用于扩展对象功能的技术。它可以在运行时为一个对象创建代理,并通过代理对象对方法进行增强。
JDK 动态代理:
1. 必须是接口,通过InvokeHandler来处理代理实例中方法调用。
2. 使用Proxy类来创建代理对象。
3. 只能对实现了接口的类生成代理,不能针对类。

CGLIB 动态代理:
1. 通过自定继承的方式实现,其原理是通过<span style="color:yellow">字节码技术</span>动态生成被代理类的子类。(不能对声明为final的方法进行代理)
2. 使用Enhancer类来创建代理对象。
3. 可以对未实现接口的类生成代理。

# JDK动态代理 VS CGLIB动态代理
- JDK动态代理只能代理实现了接口的类 <span style="color:yellow;">VS</span> CGLIB可以代理未实现接口的类。
- JDK动态代理是通过反射来实现的 <span style="color:yellow;">VS</span> CGLIB是通过生成字节码来实现的。
- JDK动态代理和CGLIB动态代理都是在运行期生成字节码 <span style="color:darkorchid;">Commonality point</span> 
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
| \   |   使用JDK动态代理的技术   |   使用CGLIB动态代理的技术   |
|-----| ---- | ---- |
| 1   |   Spring Cloud Feign   |   Hibernate 延迟加载   |
| 2   |   Spring Cloud OpenFeign   |   MyBatis 缓存   |
| 3   |   Spring Cloud Security   |   Spring 事务控制   |
| 4   |   Spring Cloud Contract   |   Spring Cloud Zuul   |
| 5   |   Spring Cloud Function   |   Spring Cloud Hystrix   |
| 6   |      |   Spring Cloud Ribbon   |
| 7   |      |   Spring Cloud Sleuth   |
| 8   |      |   Spring Cloud Config   |
| 9   |      |   Spring Cloud Stream   |
| 10  |      |   Spring Cloud Consul   |
| 11  |      |   Spring Cloud Zookeeper   |
| 12  |      |   Spring Cloud Vault   |
| 13  |      |   Spring Cloud Gateway   |
| 14  |      |   Spring Cloud Task   |
| 15  |      |   Spring Cloud Cloudfoundry   |
| 16  |      |   Spring Cloud Kubernetes   |
| 17  |      |   Spring Cloud Alibaba   |
| 18  |      |   Spring Cloud Bus   |
| 19  |      |   Spring Cloud CircuitBreaker   |