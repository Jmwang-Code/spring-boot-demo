package proxy;

public class ProxyObject implements AbstractObject{
    private RealObject realObject;
    public ProxyObject(RealObject realObject){
        this.realObject = realObject;
    }
    @Override
    public void operation() {
        System.out.println("代理对象的操作");
        realObject.operation();
    }
}