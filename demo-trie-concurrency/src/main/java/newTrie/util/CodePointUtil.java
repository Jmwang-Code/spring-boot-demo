package newTrie.util;


public abstract class CodePointUtil {

	/**
	 * 扩展字符的基地址
	 */
	public static final int EXT_CHAR_BASE = 0x100000;

	public static int[] codePoints(String string) {
		if (string == null) {
			return null;
		}
		if (string.length() == 0) {
			return new int[0];
		}
		int count = string.codePointCount(0, string.length());
		int[] result = new int[count];
		for (int i = 0; i < count; i++) {
			result[i] = string.codePointAt(i);
		}
		return result;
	}
	
	public static String toString(int c) {
		if (c < 0 || c > 65535) {
			// 返回一个默认的字符串，或者抛出一个异常
			return "Invalid character";
		}
		return new String(new int[] { c }, 0, 1);
	}

	public static String toString(int[] cs) {
		StringBuilder buf = new StringBuilder();
		for (int c : cs) {
			if (c >= CodePointUtil.EXT_CHAR_BASE) {
				buf.append("*");
			} else {
				buf.append(toString(c));
			}
		}
		return buf.toString();
	}
}
