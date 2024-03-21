package factory.single;

import factory.汽车发动机;
import factory.汽车轮胎;
import factory.汽车零件;

// 简单工厂类
public class 简单工厂 {

    public static 汽车零件 createProduct(String type) {
        if ("汽车轮胎".equals(type)) {
            return new 汽车轮胎();
        } else if ("汽车发动机".equals(type)) {
            return new 汽车发动机();
        } else {
            throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}
