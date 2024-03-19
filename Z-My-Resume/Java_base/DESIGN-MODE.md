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

// 支持并发的懒汉式
public class Singleton {
    // 使用volatile关键字保证多线程环境下的可见性
    private static volatile Singleton instance;
    // 私有构造函数，防止外部实例化
    private Singleton() {}
    public static Singleton getInstance() {
        // 第一次检查，如果实例已经创建，直接返回，避免不必要的同步
        if (instance == null) {
            // 使用同步块，确保只有一个线程进入创建实例的代码块
            synchronized (Singleton.class) {
                // 第二次检查，防止多个线程同时通过了第一次检查，造成重复创建实例
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
产品: 比如汽车发动机、汽车轮胎
产品等级结构: 比如汽车发动机的型号A B C、汽车轮胎的型号A B C
产品族: 比如某一厂家的发动机、轮胎
1. 简单工厂模式，创建单个产品（比如：汽车发动机、轮胎）
2. 工厂方法模式，创建单个产品族（比如：某一厂家的发动机、某一厂家的轮胎）
3. 抽象工厂模式，创建多个产品族、多个产品等级（比如：多个厂家的发动机、轮胎）


# 3. 原型模式（考虑对象创建成本）
1. 浅克隆:创建一个新对象，但是属性和原对象都指向相同的内存地址。（实现 Cloneable 接口和重写 Object 类的 clone() 方法）
2. 深克隆:创建一个新对象，属性和原对象的属性指向不同的内存地址。（序列化和反序列化、递归复制）

```java
// 浅克隆
public class Prototype implements Cloneable {
    @Override
    public Prototype clone() {
        try {
            return (Prototype) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// 深克隆
public class Prototype implements Serializable {
    public Prototype deepClone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Prototype) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

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

