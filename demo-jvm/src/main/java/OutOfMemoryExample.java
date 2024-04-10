import java.util.ArrayList;
import java.util.List;
import org.openjdk.jol.info.GraphLayout;

public class OutOfMemoryExample {
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
                objectSize += GraphLayout.parseInstance(obj).totalSize();
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
}