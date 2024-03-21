package factory;

// 具体产品 B
public class 奔驰发动机 implements 奔驰零件 {
    @Override
    public void operation() {
        System.out.println("奔驰发动机生产");
    }
}
