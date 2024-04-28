package newTrie.inner;

public class CodeTypes {

    /**
     * 多代码
     */
    public static final byte MULTI_CODE = Byte.MAX_VALUE;

    public static String getStringCode(int code, String prefix) {
        StringBuilder buf = new StringBuilder();
        if (prefix != null) {
            buf.append(prefix);
        }
        buf.append(String.valueOf(code));
        return buf.toString();
    }

}