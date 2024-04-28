package newTrie.lang;

import newTrie.util.CodePointUtil;

import java.util.Arrays;

/**
 * 单词字符串引用
 */
public class WordStringRef implements WordString {

    private final WordString string;
    private final int offset;
    private final int length;

    public WordStringRef(WordString string, int offset, int length) {
        this.string = string;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public int charAt(int index) {
        return string.charAt(offset + index);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public WordString substring(int offset, int length) {
        return new WordStringRef(string, this.offset + offset, length);
    }

    @Override
    public int[] toIntArray() {
        return Arrays.copyOfRange(string.toIntArray(), offset, offset + length);
    }

    @Override
    public String toString() {
        return CodePointUtil.toString(toIntArray());
    }
}
