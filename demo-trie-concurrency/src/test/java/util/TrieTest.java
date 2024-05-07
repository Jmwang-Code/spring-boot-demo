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
    }


    @Test
    public void iterator() {
        Thread.startVirtualThread(() -> {
            System.out.println(2);
            trie.iterator().forEachRemaining(a -> System.out.println(a.getValue()));
        });

        Thread.startVirtualThread(() -> {
            System.out.println(1);
            trie.iterator().forEachRemaining(a -> System.out.println(a.getValue()));
        });
    }

    @Test
    public void size() {
    }

    @Test
    public void deep() {
    }

    @Test
    public void getRoot() {
    }

    @Test
    public void forEachParallel() {
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