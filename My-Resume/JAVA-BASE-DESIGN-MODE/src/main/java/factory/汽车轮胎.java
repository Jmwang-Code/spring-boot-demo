package factory;

public class 汽车轮胎 implements 汽车零件 {
    @Override
    public void operation() {
        System.out.println("汽车轮胎生产");
    }
}