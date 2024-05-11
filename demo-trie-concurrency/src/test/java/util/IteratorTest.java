package util;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class IteratorTest {

    private static Trie trie;

    //100w数据 0.7G
    //
    @BeforeClass
    public static void setup() {
        //初始化 数组
        int[] arr = new int[65536];
        generateAllUnicodeString(arr);

        trie = new Trie();
        Random random = new Random();

        for (int i = 0; i < 1000000; i++) {
            trie.add("ADC", 1, 1);
        }
        for (int i = 0; i < 1000000; i++) {
            trie.add("ADC", 1, 2);
        }
        for (int i = 0; i < 1000000; i++) {
            trie.add("ADC", 2, 1);
        }


        // Add 10000 random words to the Trie
        for (int i = 0; i < 1000000; i++) {
            int randomCode = random.nextInt(100000000); // Generate random code
            int randomType = random.nextInt(127); // Generate random type
            int[] randomString = generateRandomString(random.nextInt(15) + 1, arr); // Generate random string of length 1-20
            trie.add(randomString, randomCode, randomType);
            if (i % 100000 == 0) {
                System.out.println(i);
                IteratorTest.printMemoryUsage(Runtime.getRuntime());
            }
        }
        //计算对象内存
        IteratorTest.printMemoryUsage(Runtime.getRuntime());

        System.out.println();
    }

    // 打印内存情况
    private static void printMemoryUsage(Runtime runtime) {
        long maxMemory = runtime.maxMemory(); // 最大可用内存
        long totalMemory = runtime.totalMemory(); // JVM堆内存总量
        long freeMemory = runtime.freeMemory(); // JVM堆内存中空闲的内存量
        System.out.println("————————————————————————————————————");
        System.out.println("最大可用内存：" + maxMemory / 1024.0 / 1024.0 / 1024.0 + " GB");
        System.out.println("已分配内存：" + totalMemory / 1024.0 / 1024.0 / 1024.0 + " GB");
        System.out.println("已分配内存中的剩余空间：" + freeMemory / 1024.0 / 1024.0 / 1024.0 + " GB");
        System.out.println("已分配内存中的已使用的空间：" + (totalMemory - freeMemory) / 1024.0 / 1024.0 / 1024.0 + " GB");
    }

    //随机生成指定字符串
    public static int[] generateRandomString(int length, int[] arr) {
        Random random = new Random();
        int[] text = new int[length];
        for (int i = 0; i < length; i++) {
            text[i] = arr[random.nextInt(65534) + 1];
        }
        return text;
    }


    //随机生成任意字符
    public static void generateAllUnicodeString(int[] arr) {
        for (int codePoint = 0; codePoint <= 65535; codePoint++) {
            if (Character.isValidCodePoint(codePoint)) {
                arr[codePoint] = codePoint;
            }
        }
    }

    //遍历所有元素
    @Test
    public void iterator() {
        trie.iterator().forEachRemaining(a -> System.out.println(a.getValue()));

        trie.iterator().forEachRemaining(node -> {
            if (node.getNode().getStatus() == 2) {
                System.out.println(node.getValue());
            }
        });
    }

    //过滤元素
    @Test
    public void iterator1() {
        AtomicInteger sum = new AtomicInteger();
        trie.iterator().forEachRemaining(node -> {
            if (node.getNode().getStatus() == 2) {
                System.out.println(node.getValue());
                sum.getAndIncrement();
            }
        });
        System.out.println(sum.get());
    }

    //修改元素
    @Test
    public void iterator2() {
        trie.iterator().forEachRemaining(node -> {
            if (node.getNode().getStatus() == 2) {
//                node.getNode().setStatus(3);
            }
        });
    }

    //计算元素数量
    @Test
    public void iterator3() {
        AtomicInteger count = new AtomicInteger(0);
        trie.iterator().forEachRemaining(node -> {
            if (node.getNode().getStatus() == 2) {
                count.incrementAndGet();
            }
        });
        System.out.println("Number of nodes with status 2: " + count.get());
    }
}
