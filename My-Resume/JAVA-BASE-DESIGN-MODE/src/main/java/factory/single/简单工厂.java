package factory.single;

import factory.奔驰发动机;
import factory.奔驰轮胎;
import factory.奔驰零件;

// 简单工厂类
public class 简单工厂 {

    public static 奔驰零件 createProduct(String type) {
        if ("汽车轮胎".equals(type)) {
            return new 奔驰轮胎();
        } else if ("汽车发动机".equals(type)) {
            return new 奔驰发动机();
        } else {
            throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}
