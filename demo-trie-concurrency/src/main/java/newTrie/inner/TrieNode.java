package newTrie.inner;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class TrieNode implements Serializable,Comparable<TrieNode> {

    @Serial
    private static final long serialVersionUID = 5727284941846160588L;

    /**
     * 树节点字符
     * 理论上char类型可以表示2的16次方,65536个Unicode码表示所有字符
     * TODO 暂时使用public后面改private
     */
    public int c;

    /**
     * 节点上绑定的Code值
     * TODO 暂时使用public后面改private
     */
    public int code;

    /**
     * Code类型，用于扩展
     * TODO 暂时使用public后面改private
     */
    public byte type;

    /**
     * 分支子节点
     * TODO 暂时使用public后面改private
     */
    public TrieNode[] branches;

    /**
     * 状态 (1:继续 2:是个词语但是还可以继续 3:确定)
     * TODO 暂时使用public后面改private
     */
    public byte status = 1;


    /**
     * 无参构造器
     */
    public TrieNode(){
        c = -1;
    }

    /**
     * 有参构造
     * @see #c
     * c
     */
    private TrieNode(int c) {
        this.c = c;
    }

    /**
     * @see #c
     * c
     * @see #status
     * status
     */
    private TrieNode(int c, int status) {
        this.c = c;
        this.status = (byte) status;
    }

    /**
     * @see #c
     * c
     * @see #status
     * status
     * @see #code
     * code
     */
    public TrieNode(int c, int status, int code) {
        this.c = c;
        this.status = (byte) status;
        this.code = code;
    }

    /**
     * @see #c
     * c
     * @see #status
     * status
     * @see #code
     * code
     * @see #type
     * type
     */
    public TrieNode(int c, int status, int code, int type) {
        this.c = c;
        this.status = (byte) status;
        this.code = code;
        this.type = (byte) type;
    }

    /**
     * 复制构造函数
     * @param node TrieNode
     */
    public TrieNode(TrieNode node) {
        this.c = node.c;
        //通过数组递归
        if (node.branches != null) {
            this.branches = new TrieNode[node.branches.length];
            for (int i = 0; i < node.branches.length; i++) {
                if (node.branches[i] != null) {
                    this.branches[i] = new TrieNode(node.branches[i]);
                }
            }
        }
    }

    /**
     * @return int
     * @Param [trieNode]
     */
    @Override
    public int compareTo(TrieNode trieNode) {
        int tc = this.c, oc = trieNode.c;
        return tc > oc ? 1 : (tc == oc ? 0 : -1);
    }

    public int getCode(){
        return this.c;
    }

    public byte getStatus() {
        return this.status;
    }

    public int getC() {
        return this.c;
    }

    /**
     * 在前缀树中，每个节点都有一个分支数组，这个数组包含了指向该节点的所有子节点的引用。这个数组的索引通常是根据子节点的字符值来确定的。因此，即使两个节点的哈希码相同，只要它们在树中的位置不同（即它们的父节点不同或者它们在分支数组中的索引不同），我们仍然可以区分它们。
     * <p>根据c，status和code这三个字段来生成哈希码的。这意味着，如果两个TrieNode对象的这三个字段都相等，那么它们的哈希码也会相等。</p>
     */
    @Override
    public int hashCode() {
        return Objects.hash(c, status, code);
    }
}
