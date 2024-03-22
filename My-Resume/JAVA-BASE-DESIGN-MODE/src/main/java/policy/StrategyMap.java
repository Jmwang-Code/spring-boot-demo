package policy;

import java.util.HashMap;
import java.util.Map;

interface StrategyMap {

    void execute();
}



// 具体策略实现类 1
class ConcreteStrategy1 implements StrategyMap {
    @Override
    public void execute() {
        System.out.println("执行策略 1");
    }
}

// 具体策略实现类 2
class ConcreteStrategy2 implements StrategyMap {
    @Override
    public void execute() {
        System.out.println("执行策略 2");
    }
}

// 策略上下文类
class Context {
    private Map<String, StrategyMap> strategies = new HashMap<>();

    public Context() {
        strategies.put("STRATEGY_1", new ConcreteStrategy1());
        strategies.put("STRATEGY_2", new ConcreteStrategy2());
    }

    public void executeStrategy(String strategyKey) {
        StrategyMap strategy = strategies.get(strategyKey);
        if (strategy != null) {
            strategy.execute();
        } else {
            System.out.println("未找到对应的策略");
        }
    }
}

// 客户端代码
class Client3 {
    public static void main(String[] args) {
        Context context = new Context();
        // 使用策略 1
        context.executeStrategy("STRATEGY_1");

        // 使用策略 2
        context.executeStrategy("STRATEGY_2");
    }
}
