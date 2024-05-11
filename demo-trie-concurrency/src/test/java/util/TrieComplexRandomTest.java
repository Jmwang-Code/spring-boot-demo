package util;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TrieComplexRandomTest {

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
        for (int i = 0; i < 100000; i++) {
            int randomCode = random.nextInt(100000000); // Generate random code
            int randomType = random.nextInt(127); // Generate random type
            int[] randomString = generateRandomString(random.nextInt(15) + 1, arr); // Generate random string of length 1-20
            trie.add(randomString, randomCode, randomType);
            if (i % 10000 == 0) {
                System.out.println(i);
                TrieComplexRandomTest.printMemoryUsage(Runtime.getRuntime());
            }
        }
        //计算对象内存
        TrieComplexRandomTest.printMemoryUsage(Runtime.getRuntime());

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


    @Test
    public void iterator() {
        trie.iterator().forEachRemaining(a -> System.out.println(a.getValue()));

        trie.iterator().forEachRemaining(node -> {
            if (node.getNode().getStatus() == 2) {
                System.out.println(node.getValue());
            }
        });

    }

    @Test
    public void size() {
        System.out.println(trie.size());
    }

    @Test
    public void deep() {
        System.out.println(trie.deep());
        //getDeep获取子树深度
        System.out.println(trie.getDeep("hel"));

        System.out.println(trie.getDeep("e"));
    }

    /**
     * 假设一种使用场景
     * <p>
     * 比如这里使用 分支合并的方式，将所有的 code 累加起来，同时记录重复的 code。加入存在不同实体相同的编码的情况。这样是需要进行业务操作的。
     */
    @Test
    public void forEachParallel() throws InterruptedException {
        //存储已经遇到的 code
        Set<Integer> seenCodes = ConcurrentHashMap.newKeySet();
        //存储 code 的累加值
        AtomicInteger total = new AtomicInteger(0);
        //存储存在重复code 集合
        Set<Integer> repeatCodes = ConcurrentHashMap.newKeySet();

        trie.forEachParallel(2,
                nodeWrapper -> nodeWrapper.getNode().getCode(),
                code -> {
                    //如果 code 是新的（即未出现过），则将其添加到 sum 中。
                    if (seenCodes.add(code)) {
                        total.incrementAndGet();
                    } else {
                        repeatCodes.add(code);
                    }
                }
        );

        System.out.println("Sum of unique codes: " + total.get());
        System.out.println("Repeat codes: " + repeatCodes);
    }

//    /**
//     * 搜索并行code Or code+codeID
//     */
//    @Test
//    public void searchParallel() throws InterruptedException {
//        Trie.TrieNodeWrapper trieNode = trie.searchParallel(2, node -> {
//            if (node.getNode().getCode() == 6 && node.getNode().getType() == 1) {
//                return node;
//            }
//            return null;
//        });
//        if (trieNode != null) {
//            System.out.println(trieNode.getValue());
//            System.out.println(trieNode.getLength());
//        } else {
//            System.out.println("trieNode2 is null");
//        }
//
//        System.out.println("————————————————————");
//
//        Trie.TrieNodeWrapper trieNode2 = trie.searchParallel(2, node -> {
//            if (node.getNode().getCode() == 7) {
//                return node;
//            }
//            return null;
//        });
//        if (trieNode2 != null) {
//            System.out.println(trieNode2.getValue());
//            System.out.println(trieNode2.getLength());
//        } else {
//            System.out.println("trieNode2 is null");
//        }
//    }

    @Test
    public void testAdd() {
        boolean maga = trie.add("MAGA", 7, 2);
        System.out.println(maga);
        //获取第一个匹配到的数据
        System.out.println(trie.getFirstPrefixWord("的MAGA阿萨斯"));
        //获取所有匹配到的数据 (如果前缀是词也包含前缀)
        System.out.println(trie.getAllWordsWithPrefixes("阿达java大圣"));
        //获取所有匹配到的数据 (如果前缀是词也不包含前缀)
        System.out.println(trie.getAllWordsWithoutPrefixes("阿达java大圣"));
    }

    /**
     * 通过word删除
     */
    @Test
    public void remove() {
        boolean java = trie.remove("java");
        System.out.println(java);
        System.out.println(trie.getFirstPrefixWord("java"));
    }

    @Test
    public void testRemove() {
        boolean java = trie.remove("java", 3, 1);
        System.out.println(java);
        System.out.println(trie.getFirstPrefixWord("java"));
    }

    @Test
    public void getFirstPrefixWord() {
        System.out.println(trie.getFirstPrefixWord("java"));
    }

    @Test
    public void getAllWordsWithoutPrefixes() {
        System.out.println(trie.getAllWordsWithoutPrefixes("java"));
    }

    @Test
    public void getAllWordsWithPrefixes() {
        System.out.println(trie.getAllWordsWithPrefixes("java"));
    }

//    @Test
//    public void clear() {
//        System.out.println(trie.size());
//        trie.clear();
//        System.out.println(trie.size());
//    }
}