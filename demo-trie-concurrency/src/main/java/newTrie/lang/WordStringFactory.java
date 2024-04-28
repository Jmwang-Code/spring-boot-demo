package newTrie.lang;

import java.util.List;

/**
 * 单词字符串工厂
 */
public class WordStringFactory {

	//通过字符串创建
	public static WordString create(String string) {
		return new WordStringObject(string);
	}

	//通过字符数组创建
	public static WordString create(int[] chars) {
		return new WordStringObject(chars);
	}

	//通过字符列表创建
	public static WordString create(List<Integer> charList) {
		int[] chars = new int[charList.size()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = charList.get(i);
		}
		return new WordStringObject(chars);
	}
}
