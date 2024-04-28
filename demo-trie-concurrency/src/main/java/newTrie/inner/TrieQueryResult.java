package newTrie.inner;

import java.util.Arrays;

public class TrieQueryResult {

	/**
	 * 查询到的词
	 */
	private String word;

	/**
	 * 查询到的词在句子中的位置
	 */
	private int offset;

	/**
	 * 代码
	 */
	private TrieCode[] codes;

	public TrieQueryResult() {
	}

	public TrieQueryResult(String word) {
		this.word = word;
	}

	public TrieQueryResult(String word, int offset, TrieCode[] codes) {
		this.word = word;
		this.offset = offset;
		this.codes = codes;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public TrieCode[] getCodes() {
		return codes;
	}

	public void setCodes(TrieCode[] codes) {
		this.codes = codes;
	}

	@Override
	public String toString() {
		return "TrieQueryResult [word=" + word + ", offset=" + offset
				+ ", codes=" + Arrays.toString(codes) + "]";
	}

}
