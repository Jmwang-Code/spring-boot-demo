import newTrie.Trie;
import newTrie.inner.MultiCodeMode;
import newTrie.inner.TrieNode;
import newTrie.inner.TrieQueryResult;
import newTrie.lang.WordString;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrieTest {

    @Test
    public void add(){
        Trie trie = new Trie();
        String str = "hello";
        //String转换成int[]数组,每个字符的Unicode代码点
        int[] intArray = str.codePoints().toArray();
        System.out.println(trie.add("AB"));
        System.out.println(trie.add("ABC"));
        System.out.println(trie.add("ABCD"));
        System.out.println(trie.add("ABCDE"));

        System.out.println(trie.getFirstPrefixWord("adadABC"));
        System.out.println(trie.getAllWordsWithoutPrefixes("adadABC"));
        System.out.println(trie.getAllWordsWithPrefixes("adadABC"));

        System.out.println();

        System.out.println(trie.getFirstPrefixWord("addABCAB"));
        System.out.println(trie.getAllWordsWithoutPrefixes("addABCAB"));
        System.out.println(trie.getAllWordsWithPrefixes("addABCAB"));

        System.out.println();

        System.out.println(trie.getFirstPrefixWord("adadABAABC"));
        System.out.println(trie.getAllWordsWithoutPrefixes("adadABAABC"));
        System.out.println(trie.getAllWordsWithPrefixes("adadABAABC"));
    }

    @Test
    public void highConcurrencyTest() throws InterruptedException {
        // 初始化 Trie
        Trie trie = new Trie();

        // 要添加、获取和删除的单词列表
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "fig", "grape");

        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 向线程池提交任务
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    // 将所有单词添加到 Trie
                    for (String word : words) {
                        trie.add(word);
                    }

                    // 从 Trie 获取所有单词
                    for (String word : words) {
                        List<TrieQueryResult> allWordsWithoutPrefixes = trie.getAllWordsWithoutPrefixes(word);
                        assert allWordsWithoutPrefixes.size()!=0;
                        System.out.println("获取单词 " + word + " 的结果: " + allWordsWithoutPrefixes);
                    }

                    // 从 Trie 中删除所有单词
                    for (String word : words) {
                        trie.remove(word,-1,-1);
                        List<TrieQueryResult> allWordsWithoutPrefixes = trie.getAllWordsWithoutPrefixes(word);
                        assert allWordsWithoutPrefixes.size()==0;
                        System.out.println("删除单词 " + word + " 后的结果: " + allWordsWithoutPrefixes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // 关闭线程池
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }


    @Test
    public void testAdd() {
        // 创建一个TrieNode实例
        TrieNode trieNode = new TrieNode();

        // 定义测试数据
        int[] word = {97, 98, 99}; // 对应的字符串是"abc"
        MultiCodeMode mode = MultiCodeMode.Drop;
        int code = 1;
        int type = 1;

        // 调用add方法
        boolean result = trieNode.add(word, mode, code, type);

        // 验证结果
        Assert.assertTrue(result);
        // 还可以添加更多的断言来验证trieNode的状态
    }
}
