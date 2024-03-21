package factory.method;

import factory.汽车发动机;
import factory.汽车零件;

public class 奔驰发动机工厂 implements 工厂方法{
    @Override
    public 汽车零件 createProduct() {
        System.out.print("奔驰发动机工厂:");
        return new 汽车发动机();
    }
}
