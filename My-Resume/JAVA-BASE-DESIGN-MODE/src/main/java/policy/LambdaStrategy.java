package policy;

interface LambdaStrategy {
    void execute();
}

// 客户端代码
class Client4 {
    public static void main(String[] args) {
        // 使用策略 1
        executeStrategy(() -> System.out.println("执行策略 1"));

        // 使用策略 2
        executeStrategy(() -> System.out.println("执行策略 2"));
    }

    public static void executeStrategy(LambdaStrategy strategy) {
        strategy.execute();
    }
}
