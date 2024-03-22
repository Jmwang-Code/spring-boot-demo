package policy;

// 策略枚举
enum StrategyEnum {
    STRATEGY_1 {
        @Override
        void execute() {
            System.out.println("执行策略 1");
        }
    },
    STRATEGY_2 {
        @Override
        void execute() {
            System.out.println("执行策略 2");
        }
    };

    abstract void execute();
}

// 客户端代码
class Client2 {
    public static void main(String[] args) {
        // 使用策略 1
        StrategyEnum.STRATEGY_1.execute();

        // 使用策略 2
        StrategyEnum.STRATEGY_2.execute();
    }
}
