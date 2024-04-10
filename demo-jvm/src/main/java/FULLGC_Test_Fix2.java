import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务的生命周期和任务中使用的对象的生命周期，以避免潜在的内存泄漏问题。
 */

/**
 * 1. 编译javac -encoding UTF-8 FULLGC_Test.java
 * 2. 打包jar cvfe FULLGC_Test.jar FULLGC_Test FULLGC_Test.class FULLGC_Test$CardInfo.class
 * 3. 运行java -jar FULLGC_Test.jar
 *
 * java -jar -Xms200m -Xmx200m -Xmn100m -XX:+PrintGC FULLGC_Test.jar
 *
 * java -Xms80m -Xmx80m -XX:+PrintGC -Xloggc:gc.log -jar FULLGC_Test.jar
 * */
public class FULLGC_Test_Fix2 {

    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * -Xms200m -Xmx200m -Xmn100m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintAdaptiveSizePolicy -XX:+PrintTenuringDistribution -XX:+PrintHeapAtGC -XX:+PrintReferenceGC -Xloggc:gc.log
     * <p>
     * CardInfo是一个普通的Java对象，我们在main方法中创建了一个CardInfo对象，并且将其放入到一个List中，然后通过一个无限循环不断的从List中获取CardInfo对象，然后调用m方法，这样就会导致CardInfo对象无法被回收，最终导致堆内存溢出。
     */
    private static class CardInfo {
        BigDecimal price = new BigDecimal(0.0);
        String name = "张三";
        int age = 5;
        Date birthDay = new Date();

        public void m() {
        }
    }

    //线程没被回收导致对应创建的缓存无法被回收
    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50, r -> {
        Thread t = new Thread(r, "业务" + count.getAndIncrement());
        return t;
    }, new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws InterruptedException {
        executor.setMaximumPoolSize(50);

        for (; ; ) {
            modelFit();
            Thread.sleep(100);
        }
    }

    private static void modelFit() {
        List<CardInfo> taskList = getAllCardInfo();
        taskList.forEach(info -> {
            executor.scheduleWithFixedDelay(() -> {
                info.m();
            }, 2, 3, TimeUnit.SECONDS);
        });
        taskList.clear();
    }

    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> taskList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CardInfo ci = new CardInfo();
            taskList.add(ci);
        }

        return taskList;
    }
}
