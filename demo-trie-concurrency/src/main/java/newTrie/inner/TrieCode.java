package newTrie.inner;

import java.io.Serial;
import java.io.Serializable;

/**
 * <code>节点Code值</code>
 */
public class TrieCode implements Serializable {

    @Serial
    private static final long serialVersionUID = 3881643557612826255L;

    private long code;
    private byte type;

    public TrieCode() {
    }

    public TrieCode(long code, byte type) {
        this.code = code;
        this.type = type;
    }

    public TrieCode(long code, int type) {
        this.code = code;
        this.type = (byte) type;
    }

    public long getCode() {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) code;
        result = prime * result + type;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrieCode other = (TrieCode) obj;
        if (code != other.code)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TrieCode [code=" + code + ", type=" + type + "]";
    }
}
