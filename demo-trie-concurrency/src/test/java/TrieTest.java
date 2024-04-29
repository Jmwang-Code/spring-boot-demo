import newTrie.Trie;
import newTrie.inner.CodeTypes;
import newTrie.inner.MultiCodeMode;
import newTrie.inner.TrieQueryResult;
import org.junit.Test;

public class TrieTest {

    @Test
    public void add(){
        Trie trie = new Trie();
        String str = "hello";
        //String转换成int[]数组,每个字符的Unicode代码点
        int[] intArray = str.codePoints().toArray();
        trie.add(intArray, MultiCodeMode.Append,123, CodeTypes.MULTI_CODE);

    }


}
