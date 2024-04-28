package newTrie.lang;

/**
 * 单词字符串接口
 */
public interface WordString {
    //如获取字符
    int charAt(int index);

    //获取长度
    int length();

    //获取子字符串
    WordString substring(int offset, int length);

    //转换为整数数组
    int[] toIntArray();
}
