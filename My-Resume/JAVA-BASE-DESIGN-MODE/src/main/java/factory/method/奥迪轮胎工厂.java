package factory.method;

import factory.奔驰轮胎;
import factory.奔驰零件;

public class 奥迪轮胎工厂 implements 工厂方法{
    @Override
    public 奔驰零件 createProduct() {
        System.out.print("奥迪轮胎工厂:");
        return new 奔驰轮胎();
    }
}
