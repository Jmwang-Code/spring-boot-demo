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
从产品 、产品族 、产品等级结构 三个维度来考虑
产品: 比如汽车发动机
产品等级结构: 比如汽车发动机的不同型号
产品族: 比如不同厂家生产的汽车发动机
1. 创建单个产品等级结构，使用简单工厂模式（比如：某一款发动机）
2. 创建单个产品族，使用工厂方法模式（比如：某一厂家的发动机，多个型号）
3. 创建多个产品族，使用抽象工厂模式（比如：多个厂家的发动机，多个型号）

# 3. 原型模式（考虑对象创建成本）
1. 浅克隆:创建一个新对象，但是属性和原对象都指向相同的内存地址。（实现 Cloneable 接口和重写 Object 类的 clone() 方法）
2. 深克隆:创建一个新对象，属性和原对象的属性指向不同的内存地址。（序列化和反序列化、递归复制）

# 4. 策略模式（考虑业务场景）

```java
//策略模式 使用map存储策略
public class StrategyFactory {
    private static Map<String, Strategy> strategyMap = new HashMap<>();
    static {
        strategyMap.put("A", new StrategyA());
        strategyMap.put("B", new StrategyB());
    }
    public static Strategy getStrategy(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return strategyMap.get(type);
    }
}
``
```

# 5. 代理模式（考虑业务场景）
