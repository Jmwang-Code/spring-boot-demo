package factory.method;

import factory.汽车轮胎;
import factory.汽车零件;

public class 奥迪轮胎工厂 implements 工厂方法{
    @Override
    public 汽车零件 createProduct() {
        System.out.print("奥迪轮胎工厂:");
        return new 汽车轮胎();
    }
}
