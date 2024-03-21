package factory.method;

import factory.奔驰发动机;
import factory.奔驰零件;

public class 奔驰发动机工厂 implements 工厂方法{
    @Override
    public 奔驰零件 createProduct() {
        System.out.print("奔驰发动机工厂:");
        return new 奔驰发动机();
    }
}
