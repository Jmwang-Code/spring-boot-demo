package newTrie.lang;

import java.util.List;

public class WordStringFactory {

	public static WordString create(String string) {
		return new WordStringObject(string);
	}

	public static WordString create(int[] chars) {
		return new WordStringObject(chars);
	}

	public static WordString create(List<Integer> charList) {
		int[] chars = new int[charList.size()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = charList.get(i);
		}
		return new WordStringObject(chars);
	}
}
