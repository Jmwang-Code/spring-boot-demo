import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1. 编译javac -encoding UTF-8 HighCPUUsageExample.java
 * 2. 打包jar cvfe HighCPUUsageExample.jar HighCPUUsageExample HighCPUUsageExample.class
 * 3. 运行java -jar HighCPUUsageExample.jar
 *
 * java -jar -Xms200m -Xmx200m -Xmn100m -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError HighCPUUsageExample.jar
 *
 * java -Xms80m -Xmx80m -XX:+PrintGC -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -jar HighCPUUsageExample.jar
 */
public class HighCPUUsageExample {

    /**
     * 1. 先查看top
     * 2. 然后查看对应程序的 top -Hp pid 看看那个线程比较高
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                while (true) {
                    // 模拟耗时操作，这里使用空循环
                }
            });
        }
    }
}
