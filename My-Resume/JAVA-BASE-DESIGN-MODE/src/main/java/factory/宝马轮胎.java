package factory;

public class 宝马轮胎 implements 宝马零件 {
    @Override
    public void operation() {
        System.out.println("宝马轮胎生产");
    }
}