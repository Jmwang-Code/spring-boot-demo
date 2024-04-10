
import java.util.ArrayList;
import java.util.List;

/**
 * 在很多复杂业务中(计算)，增加堆栈的切面日志。
 *
 */
/**
 * 1. 编译javac -encoding UTF-8 Creating_too_many_objects_leads_to_OOM_Fix.java
 * 2. 打包jar cvfe Creating_too_many_objects_leads_to_OOM_Fix.jar Creating_too_many_objects_leads_to_OOM_Fix Creating_too_many_objects_leads_to_OOM_Fix.class
 * 3. 运行java -jar Creating_too_many_objects_leads_to_OOM.jar
 *
 * java -jar -Xms80m -Xmx80m -XX:+PrintGC Creating_too_many_objects_leads_to_OOM_Fix.jar
 *
 * java -Xms80m -Xmx80m -XX:+PrintGC -Xloggc:gc.log -jar Creating_too_many_objects_leads_to_OOM_Fix.jar
 * */
public class Creating_too_many_objects_leads_to_OOM_Fix {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int objectCount = 0;
        long objectSize = 0;
        Runtime runtime = Runtime.getRuntime();

        try {
            while (true) {
                Object obj = new Object();
                list.add(obj); // 不断创建新的对象并加入到列表中
                objectCount++;

                // 使用 JOL 来测量对象的大小
                objectSize += 16;
//                System.out.println("对象大小: " + objectSize + " bytes");

                // 每创建一定数量的对象后，输出一次内存情况
                if (objectCount % 10000000 == 0) {
                    printMemoryUsage(runtime);
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("内存溢出异常：" + e.getMessage());
            System.out.println("创建的对象数量：" + objectCount);
            System.out.println("创建的对象总大小：" + objectSize + " bytes");
            //KB
            System.out.println("创建的对象总大小：" + objectSize / 1024 + " KB");
            //MB
            System.out.println("创建的对象总大小：" + objectSize / 1024 / 1024 + " MB");
            //GB
            System.out.println("创建的对象总大小：" + objectSize / 1024 / 1024 / 1024 + " GB");
            printMemoryUsage(runtime);
        }
    }

    // 打印内存情况
    private static void printMemoryUsage(Runtime runtime) {
        long maxMemory = runtime.maxMemory(); // 最大可用内存
        long totalMemory = runtime.totalMemory(); // JVM堆内存总量
        long freeMemory = runtime.freeMemory(); // JVM堆内存中空闲的内存量
        System.out.println("————————————————————————————————————");
        System.out.println("最大可用内存：" + maxMemory / 1024 + " KB");
        System.out.println("已分配内存：" + totalMemory / 1024 + " KB");
        System.out.println("已分配内存中的剩余空间：" + freeMemory / 1024 + " KB");
        System.out.println("已分配内存中的已使用的空间：" + (totalMemory - freeMemory) / 1024 + " KB");
    }
}