import org.junit.Test;
import singleton.SingletonHunger;
import singleton.SingletonLazy;

public class TestDesignMode {

    @Test
    public void testSingletonHunger() {
        // 创建多个线程，尝试获取 SingletonHunger 的实例
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonHunger instance = SingletonHunger.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + instance);
            }).start();
        }

        // 等待一段时间，确保所有线程都执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingletonLazy() {
        // 创建多个线程，尝试获取 SingletonLazy 的实例
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonLazy instance = SingletonLazy.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + instance);
            }).start();
        }

        // 等待一段时间，确保所有线程都执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
