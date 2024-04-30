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
        System.out.println(trie.getFirstAllWord("adadABC"));

        System.out.println(trie.getFirstPrefixWord("adadABCAB"));
        System.out.println(trie.getFirstAllWord("adadABCAB"));

        System.out.println(trie.getFirstPrefixWord("adadABdasABC"));
        System.out.println(trie.getFirstAllWord("adadABdasABC"));
    }


}
