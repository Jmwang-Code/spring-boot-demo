import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 10, null, null);
    }
}