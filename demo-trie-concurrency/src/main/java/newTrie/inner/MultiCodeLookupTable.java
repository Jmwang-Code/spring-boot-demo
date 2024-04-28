package newTrie.inner;

import java.util.*;

/**
 * <code>多码查找表</code>
 *
 * 整颗树共享一个多码表，去查询对应的编码
 */
public class MultiCodeLookupTable {

    private Map<Long, TrieCode[]> multiCodeExtMap = new LinkedHashMap<Long, TrieCode[]>();

    /**
     * currentKey 就确保了每个添加到 multiCodeExtMap 的键值对都有一个唯一的键。
     */
    private long currentKey = 0;

    /**
     * 创建Code在多码查找表上
     * @param value1
     * @param value2
     * @return
     */
    public synchronized long newCode(TrieCode value1, TrieCode value2) {
        long newKey = currentKey++;
        if (!value1.equals(value2)) {
            multiCodeExtMap.put(newKey, new TrieCode[]{value1, value2});
        } else {
            multiCodeExtMap.put(newKey, new TrieCode[]{value1});
        }
        return newKey;
    }

    /**
     * 添加Code到多码查找表上
     * @param key
     * @param code
     * @return
     */
    public synchronized boolean addCode(long key, TrieCode code) {
        TrieCode[] oldCodes = multiCodeExtMap.get(key);
        for (TrieCode oldCode : oldCodes) {
            // 如已存在，则不再添加
            if (oldCode.equals(code)) {
                return false;
            }
        }
        TrieCode[] newCodes = Arrays.copyOf(oldCodes, oldCodes.length + 1);
        newCodes[oldCodes.length] = code;
        multiCodeExtMap.put(key, newCodes);
        return true;
    }

    /**
     * 获取最小Code
     * @param key
     * @param tCode
     * @return
     */
    public synchronized TrieCode getMinCode(long key, TrieCode tCode) {
        TrieCode[] codes = multiCodeExtMap.get(key);
        TrieCode minCode = tCode;
        for (TrieCode code : codes) {
            if (code.getType() == tCode.getType()) {
                if (minCode.getCode() > code.getCode()) {
                    minCode = code;
                }
            }
        }
        return minCode;
    }

    /**
     * 获取最大Code
     * @param key
     * @param tCode
     * @return
     */
    public synchronized TrieCode getMaxCode(long key, TrieCode tCode) {
        TrieCode[] codes = multiCodeExtMap.get(key);
        TrieCode maxCode = null;
        for (TrieCode code : codes) {
            if (code.getType() == tCode.getType()) {
                if (maxCode.getCode() < code.getCode()) {
                    maxCode = code;
                }
            }
        }
        return maxCode;
    }

    /**
     * 获取Code
     * @param key
     * @return
     */
    public synchronized TrieCode[] getCode(long key) {
        return multiCodeExtMap.get(key);
    }

    /**
     * 获取Code
     * @param key
     * @param type
     * @return
     */
    public synchronized TrieCode[] getCode(long key, byte type) {
        List<TrieCode> result = new LinkedList<TrieCode>();
        TrieCode[] codes = multiCodeExtMap.get(key);
        for (TrieCode code : codes) {
            if (code.getType() == type) {
                result.add(code);
            }
        }
        return result.toArray(new TrieCode[0]);
    }

    /**
     * 替换或移除Code
     * @param key
     * @param code
     * @return
     */
    public synchronized int replaceOrRemoveCode(long key, TrieCode code) {
        List<TrieCode> newCodes = new LinkedList<TrieCode>();
        TrieCode[] oldCodes = multiCodeExtMap.get(key);
        for (TrieCode oldCode : oldCodes) {
            if (oldCode.getType() != code.getType()) {
                newCodes.add(oldCode);
            }
        }
        if (newCodes.size() == 0) {
            multiCodeExtMap.remove(key);
            return 0;
        } else {
            newCodes.add(code);
            multiCodeExtMap.put(key, newCodes.toArray(new TrieCode[0]));
            return newCodes.size();
        }
    }

    /**
     * 移除Code
     * @param key
     * @param code
     * @return
     */
    public synchronized boolean removeCode(long key, TrieCode code) {
        List<TrieCode> newCodes = new LinkedList<TrieCode>();
        TrieCode[] oldCodes = multiCodeExtMap.get(key);
        boolean needRemove = false;
        for (TrieCode oldCode : oldCodes) {
            if (oldCode.getCode() == code.getCode()
                    && oldCode.getType() == code.getType()) {
                needRemove = true;
            } else {
                newCodes.add(oldCode);
            }
        }
        if (!needRemove) {
            return false;
        }
        if (newCodes.size() == 0) {
            multiCodeExtMap.remove(key);
        } else {
            multiCodeExtMap.put(key, newCodes.toArray(new TrieCode[0]));
        }
        return true;
    }


}
