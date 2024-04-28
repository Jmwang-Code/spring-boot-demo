package newTrie.lang;

/**
 * 字符串接口
 */
public interface WordString {

    int charAt(int index);

    int length();

    WordString substring(int offset, int length);

    int[] toIntArray();
}
