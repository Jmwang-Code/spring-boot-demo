# 1. 单例模式（考虑并发使用）

```java
// 饿汉式
public class Singleton {
    private static Singleton instance = new Singleton();
    private Singleton() {}
    public static Singleton getInstance() {
        return instance;
    }
}

// 懒汉式
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

// 双重检查锁
public class Singleton {
    private volatile static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}



// ThreadLocal
public class Singleton {
    private static final ThreadLocal<Singleton> threadLocalInstance = new ThreadLocal<Singleton>() {
        @Override
        protected Singleton initialValue() {
            return new Singleton();
        }
    };
    private Singleton() {}
    public static Singleton getInstance() {
        return threadLocalInstance.get();
    }
}

// 容器
public class SingletonManager {
    private static Map<String, Object> objMap = new HashMap<String, Object>();
    private SingletonManager() {}
    public static void registerService(String key, Object instance) {
        if (!objMap.containsKey(key)) {
            objMap.put(key, instance);
        }
    }
    public static Object getService(String key) {
        return objMap.get(key);
    }
}

// 支持并发的懒汉式
public class Singleton {
    private static volatile Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

# 2. 工厂模式（考虑扩展性）
1. 主要考虑是否有产品和产品族的概念，如果有，就考虑抽象工厂模式，否则就考虑工厂方法模式。

# 3. 原型模式（考虑对象创建成本）
1. 浅克隆:创建一个新对象，但是属性和原对象都指向相同的内存地址。（实现 Cloneable 接口和重写 Object 类的 clone() 方法）
2. 深克隆:创建一个新对象，属性和原对象的属性指向不同的内存地址。（序列化和反序列化、递归复制）

# 4. 策略模式（考虑业务场景）

# 5. 代理模式（考虑业务场景）
