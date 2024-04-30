import newTrie.Trie;
import org.junit.Test;

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


}
