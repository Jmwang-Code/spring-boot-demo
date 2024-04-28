package newTrie.lang;


import newTrie.util.CodePointUtil;

import java.util.Arrays;

/**
 * 单词字符串对象
 */
public class WordStringObject implements WordString {

	private final int[] characters;

	public WordStringObject(String string) {
		if (string == null) {
			this.characters = new int[0];
		} else {
			this.characters = CodePointUtil.codePoints(string);
		}
	}

	public WordStringObject(int[] characters) {
		this.characters = characters;
	}

	public int charAt(int index) {
		return characters[index];
	}

	public int length() {
		return characters.length;
	}

	public WordString substring(int offset, int length) {
		return new WordStringRef(this, offset, length);
	}

	@Override
	public int[] toIntArray() {
		return Arrays.copyOf(characters, characters.length);
	}

	@Override
	public String toString() {
		return CodePointUtil.toString(this.characters);
	}

}
