package policy;

// 策略接口
interface Strategy {
    void execute();
}

// 具体策略实现类 1
class ConcreteStrategy3 implements Strategy {
    @Override
    public void execute() {
        System.out.println("执行策略 3");
    }
}

// 具体策略实现类 2
class ConcreteStrategy4 implements Strategy {
    @Override
    public void execute() {
        System.out.println("执行策略 4");
    }
}

// 上下文类
class Context2 {
    private Strategy strategy;

    public Context2(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.execute();
    }
}

// 客户端代码
class Client {
    public static void main(String[] args) {
        // 使用策略 1
        Context2 context1 = new Context2(new ConcreteStrategy3());
        context1.executeStrategy();

        // 使用策略 2
        Context2 context2 = new Context2(new ConcreteStrategy4());
        context2.executeStrategy();
    }
}
