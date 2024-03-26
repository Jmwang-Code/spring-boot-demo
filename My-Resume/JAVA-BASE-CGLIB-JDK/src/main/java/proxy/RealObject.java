package proxy;

public class RealObject implements AbstractObject{
    @Override
    public void operation() {
        System.out.println("真实对象的操作");
    }
}