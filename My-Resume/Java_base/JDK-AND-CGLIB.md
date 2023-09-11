# 1. 动态代理是什么？

- 动态代理是一种用于扩展对象功能的技术。它可以在运行时为一个对象创建代理,并通过代理对象对方法进行增强。


# 1.1 JDK动态代理 VS CGLIB动态代理

- JDK动态代理只能代理实现了接口的类（`Proxy`） <span style="color:yellow;">VS</span> CGLIB可以代理未实现接口的类。（`Enhancer`）
- JDK动态代理是通过反射来实现的 <span style="color:yellow;">VS</span> CGLIB是通过生成字节码来实现的。
- JDK动态代理（直接写Class字节码）创建效率高，通过反射机制调用,执行效率低 <span style="color:yellow;">VS</span> CGLIB（ASM框架写Class字节码）创建效率低，CGLIB（FastClass机制直接调用）执行效率高。

<h1>JDK动态代理</h1>

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

<h1>CGLIB动态代理</h1>

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

# 2. 静态代理和动态代理

- 静态代理：代理类是在编译期间就已经存在的代理方式。静态代理多使用AspectJ，一般在安卓端使用。
- 动态代理：代理类是在运行时动态生成的代理方式。动态代理多采用JDK动态代理和CGLIB动态代理实现。

# 3. 动态代理的应用场景

| \   | 使用JDK动态代理的技术     | 使用CGLIB动态代理的技术  |
| --- | ---------------- | --------------- |
| 1   | Spring注解@Service | 注解@Aspect       |
| 2   | 自定义日志收集、慢函数计算时间  | 自定义日志收集、慢函数计算时间 |


# 4.java三大特性？
封装继承多态

封装：封装是指将数据和方法封装在一个类中，对外部隐藏实现细节，只暴露必要的接口。封装可以提高代码的安全性和可维护性，避免外部对数据的直接访问和修改，同时也可以隐藏实现细节，降低代码的耦合度。

继承：继承是指一个类可以继承另一个类的属性和方法。子类可以重写父类的方法，也可以添加自己的属性和方法。继承可以提高代码的复用性和可扩展性，避免重复编写相同的代码，同时也可以实现代码的层次化管理。

多态：多态是指同一种类型的对象，在不同的情况下可以表现出不同的行为。多态可以通过方法的重载和重写来实现。多态可以提高代码的灵活性和可扩展性，使得代码更加易于维护和扩展。