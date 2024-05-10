package util;

import org.junit.Test;


import org.junit.BeforeClass;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TrieTest {

    private static Trie trie;

    @BeforeClass
    public static void setup() {
        System.out.println("begin");
        trie = new Trie();

        // Add words to the Trie
        trie.add("hello".codePoints().toArray(), 1, 1);
        trie.add("world".codePoints().toArray(), 2, 1);
        trie.add("java".codePoints().toArray(), 3, 1);
        trie.add("he".codePoints().toArray(), 4, 1);
        trie.add("wor".codePoints().toArray(), 5, 1);
        trie.add("ja".codePoints().toArray(), 6, 1);
    }


    @Test
    public void iterator() {
//        Thread.startVirtualThread(() -> {
        System.out.println(2);
        trie.iterator().forEachRemaining(a -> System.out.println(a.getValue()));
//        });

//        Thread.startVirtualThread(() -> {
//            System.out.println(1);
//            trie.iterator().forEachRemaining(a -> System.out.println(a.getValue()));
//        });
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
                nodeWrapper -> nodeWrapper.getNode().code,
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

    /**
     * 搜索并行code Or code+codeID
     */
    @Test
    public void searchParallel() throws InterruptedException {
        Trie.TrieNodeWrapper trieNode = trie.searchParallel(2, node -> {
            if (node.getNode().getCode() == 6 && node.getNode().getType() == 1) {
                return node;
            }
            return null;
        });
        if (trieNode != null) {
            System.out.println(trieNode.getValue());
            System.out.println(trieNode.getLength());
        } else {
            System.out.println("trieNode2 is null");
        }

        System.out.println("————————————————————");

        Trie.TrieNodeWrapper trieNode2 = trie.searchParallel(2, node -> {
            if (node.getNode().getCode() == 7) {
                return node;
            }
            return null;
        });
        if (trieNode2 != null) {
            System.out.println(trieNode2.getValue());
            System.out.println(trieNode2.getLength());
        } else {
            System.out.println("trieNode2 is null");
        }
    }

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
        boolean java = trie.remove("java",3,1);
        System.out.println(java);
        System.out.println(trie.getFirstPrefixWord("java"));
    }

    @Test
    public void getFirstPrefixWord() {
    }

    @Test
    public void getAllWordsWithoutPrefixes() {
    }

    @Test
    public void getAllWordsWithPrefixes() {
    }

    @Test
    public void clear() {
    }
}