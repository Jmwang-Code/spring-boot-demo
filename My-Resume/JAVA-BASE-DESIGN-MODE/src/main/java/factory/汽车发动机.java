package factory;

// 具体产品 B
public class 汽车发动机 implements 汽车零件 {
    @Override
    public void operation() {
        System.out.println("汽车发动机生产");
    }
}
