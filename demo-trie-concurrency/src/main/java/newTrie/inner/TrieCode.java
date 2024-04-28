package newTrie.inner;

import java.io.Serial;
import java.io.Serializable;

/**
 * <code>节点Code值</code>
 */
public class TrieCode implements Serializable {

    @Serial
    private static final long serialVersionUID = 3881643557612826255L;

    private int code;
    private byte type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
