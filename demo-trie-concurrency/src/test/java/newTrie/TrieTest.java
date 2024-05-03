package newTrie;

import junit.framework.TestCase;
import newTrie.inner.TrieNode;

import java.util.Iterator;

public class TrieTest extends TestCase {

    public void testIterator() {
        //测试
        Trie trie = new Trie();
        trie.add("hello");
        trie.add("world");

        Iterator<TrieNode> iterator = trie.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void testSize() {
    }

    public void testDeep() {
    }

    public void testGetRoot() {
    }

    public void testForEachParallel() {
    }

    public void testSearchParallel() {
    }

    public void testAdd() {
    }

    public void testTestAdd() {
    }

    public void testTestAdd1() {
    }

    public void testTestAdd2() {
    }

    public void testTestAdd3() {
    }

    public void testTestAdd4() {
    }

    public void testTestAdd5() {
    }

    public void testTestAdd6() {
    }

    public void testRemove() {
    }

    public void testTestRemove() {
    }

    public void testGetFirstPrefixWord() {
    }

    public void testGetAllWordsWithoutPrefixes() {
    }

    public void testGetAllWordsWithPrefixes() {
    }

    public void testClear() {
    }
}