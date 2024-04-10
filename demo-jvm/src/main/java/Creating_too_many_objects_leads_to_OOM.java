import java.util.ArrayList;
import java.util.List;

/**
 * 1. 编译javac -encoding UTF-8 Creating_too_many_objects_leads_to_OOM.java
 * 2. 打包jar cvfe Creating_too_many_objects_leads_to_OOM.jar Creating_too_many_objects_leads_to_OOM Creating_too_many_objects_leads_to_OOM.class
 * 3. 运行java -jar Creating_too_many_objects_leads_to_OOM.jar
 *
 * java -jar -Xms200m -Xmx200m -Xmn100m -XX:+PrintGC Creating_too_many_objects_leads_to_OOM.jar
 *
 * java -Xms80m -Xmx80m -XX:+PrintGC -Xloggc:gc.log -jar Creating_too_many_objects_leads_to_OOM.jar
 * */
public class Creating_too_many_objects_leads_to_OOM {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int objectCount = 0;
        long objectSize = 0;

        try {
            while (true) {
                Object obj = new Object();
                list.add(obj); // 不断创建新的对象并加入到列表中
                objectCount++;

                // 使用 JOL 来测量对象的大小
                objectSize += getObjectSize(obj);
//                System.out.println("对象大小: " + objectSize + " bytes");
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
        }
    }

    // 获取对象的大小
    private static long getObjectSize(Object obj) {
        // 这里可以使用合适的方法来计算对象的实际大小
        // 可以考虑使用 JOL 或者其他内存分析工具
        // 这里假设每个对象占用 16 字节
        return 16;
    }
}