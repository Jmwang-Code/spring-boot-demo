package util;

import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;

import java.util.Iterator;

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

    @Test
    public void forEachParallel() {
        trie.forEachParallel(2,
                nodeWrapper -> {
                    // 在这里，你可以定义如何从 TrieNodeWrapper 转换到你需要的类型 U
                    // 例如，如果 U 是 String 类型，你可以返回 nodeWrapper 的某个字段
                    return nodeWrapper.getNode().c;
                },
                u -> {
                    // 在这里，u 是上面转换器函数返回的类型 U
                    // 你可以在这个 lambda 函数中编写你需要执行的操作
                    // 例如，打印 u
                    System.out.println(u);
                }
        );
    }

    @Test
    public void searchParallel() {
    }

    @Test
    public void add() {
    }

    @Test
    public void testAdd() {
    }

    @Test
    public void testAdd1() {
    }

    @Test
    public void testAdd2() {
    }

    @Test
    public void testAdd3() {
    }

    @Test
    public void testAdd4() {
    }

    @Test
    public void testAdd5() {
    }

    @Test
    public void testAdd6() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void testRemove() {
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