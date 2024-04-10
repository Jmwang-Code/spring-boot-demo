

/**
 * 1. 编译javac -encoding UTF-8 New_object_too_large_OOM.java
 * 2. 打包jar cvfe New_object_too_large_OOM.jar New_object_too_large_OOM New_object_too_large_OOM.class
 * 3. 运行java -jar New_object_too_large_OOM.jar
 *
 * java -jar -Xms200m -Xmx200m -Xmn100m -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError New_object_too_large_OOM.jar
 *
 * java -Xms80m -Xmx80m -XX:+PrintGC -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -jar New_object_too_large_OOM.jar
 */
public class New_object_too_large_OOM {
    public static void main(String[] args) {
        try {
            // 创建一个超大的整型数组，占用大量内存
            int[] largeArray = new int[Integer.MAX_VALUE / 2];
            //计算一下对象的内存大小
            //使用jol
//            System.out.println(ClassLayout.parseInstance(largeArray).toPrintable());
        } catch (OutOfMemoryError e) {
            System.out.println("内存溢出异常：" + e.getMessage());
        }
    }
}
