package newTrie.inner;

import newTrie.lang.Result;
import newTrie.lang.WordString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TrieNode implements Serializable, Comparable<TrieNode> {

    @Serial
    private static final long serialVersionUID = 5727284941846160588L;

    private final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private final static Lock r = rwl.readLock();

    private final static Lock w = rwl.writeLock();


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
    public long code;

    /**
     * Code类型，单码节点还是多码节点
     * TODO 暂时使用public后面改private
     */
    public byte type;

    /**
     * 多码查找表，查找节点上绑定的Code值
     */
    private static MultiCodeLookupTable multiCodeLookupTable = new MultiCodeLookupTable();

    /**
     * 分支子节点
     * <p>TODO 暂时使用public后面改private
     *
     * <p>使用一个固定长度为65536的数组，并且你可以直接通过字符映射到数组的索引位置，那么检索操作的时间复杂度确实是O(1)。这是因为数组允许通过索引直接访问元素，无论数组的大小如何，这个操作的时间复杂度都是常数时间，即O(1)。
     * <p>然而，这种方法的一个主要缺点是可能会浪费大量的内存空间。如果你的应用中并不会使用到所有的Unicode字符，那么大部分的数组空间将会被浪费。具体来说，如果你的数组中大部分元素都是空的，那么你就在存储这些空元素上浪费了空间。这种空间浪费可能会成为问题，特别是如果你的应用有严格的内存限制，或者如果你需要创建多个这样的数组。
     * <p>总的来说，这种方法在查询速度（时间复杂度为O(1)）和内存使用（可能会浪费大量空间）之间做出了权衡。
     *
     * <p>另一种就是慢慢扩展不要顺序，不维护顺序。O(n)去找
     * <br>
     * <p><code>解决数组设计的方式：</code>
     * <br>
     * <p><code>一、O(n)二进制查找、空间复杂度无限接近O(1)*n</code>
     * <br>
     * <p><code>二、O(logN)维持顺序二进制查找、空间复杂度无限接近O(1)*n,但是要维护顺序额外开销</code>
     * <br>
     * <p><code>一、O(1)索引定位、空间复杂度O(65536)*n,会消耗大量开辟内存并且可能不使用</code>
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
    public TrieNode() {
        c = -1;
    }

    /**
     * 有参构造
     *
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
     *
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

    /**
     * 获取对应的Code数组
     * @return TrieCode
     */
    public TrieCode[] getCodes() {
        r.lock();
        try {
            if (this.type == CodeTypes.MULTI_CODE) {
                return multiCodeLookupTable.getCode(this.code);
            } else {
                return new TrieCode[] { new TrieCode(this.code, this.type) };
            }
        } finally {
            r.unlock();
        }
    }

    public int getCode() {
        return this.c;
    }

    public byte getStatus() {
        return this.status;
    }

    public int getC() {
        return this.c;
    }

    /**
     * 获取TrieNode,节点通过前缀方式
     *
     * @Param [c]
     */
    public TrieNode get(int c) {
        r.lock();
        try {
            return getBranch(c);
        } finally {
            r.unlock();
        }
    }

    /**
     * 通过传入字符查询当前数组索引，获取分支节点
     *
     * @Param [c]
     */
    public TrieNode getBranch(int c) {
        int index = getIndex(c);
        if (index < 0) {
            return null;
        } else {
            return this.branches[index];
        }
    }

    /**
     * 根据一个词获得所取的参数,没有就返回null
     *
     * @param keyWord WordString
     */
    public TrieNode getBranch(WordString keyWord) {
        r.lock();
        try {
            TrieNode tempBranch = this;
            int index = 0;
            for (int j = 0; j < keyWord.length(); j++) {
                index = tempBranch.getIndex(keyWord.charAt(j));
                if (index < 0) {
                    return null;
                }
                // 获取下一个节点，如果没有就返回null
                if ((tempBranch = tempBranch.branches[index]) == null) {
                    return null;
                }
            }
            return tempBranch;
        } finally {
            r.unlock();
        }
    }

    /**
     * 获取分支路径
     *
     * @param word int[]
     */
    public TrieNodePath getBranchPath(int[] word) {
        List<TrieNode> nodes = new LinkedList<TrieNode>();
        r.lock();
        try {
            TrieNode tempBranch = this;
            nodes.add(tempBranch);
            int index = 0;
            for (int j = 0; j < word.length; j++) {
                index = tempBranch.getIndex(word[j]);
                if (index < 0) {
                    return null;
                }
                if ((tempBranch = tempBranch.branches[index]) == null) {
                    return null;
                }
                nodes.add(tempBranch);
            }
            TrieNodePath result = null;
            TrieNodePath prevPath = null;
            for (int i = nodes.size() - 1; i >= 0; i--) {
                TrieNodePath currentPath = new TrieNodePath();
                currentPath.setNode(nodes.get(i));
                if (prevPath != null) {
                    prevPath.setParent(currentPath);
                }
                if (result == null) {
                    result = currentPath;
                }
                prevPath = currentPath;
            }
            return result;
        } finally {
            r.unlock();
        }
    }

    /**
     * 获取字符在数组中的索引
     *
     * <p><code>值得考虑二分么？</code> 如果你的数组长度固定为65536，且每个元素都可能被使用，那么使用二分搜索是值得的。因为二分搜索的时间复杂度为O(log n)，在这种情况下，它将比线性搜索更高效。
     * <p><code>会出现的问题？</code> 如果你的数组长度固定为65536，但实际使用的元素数量远小于这个数，那么你将面临内存浪费的问题。此外，如果你需要存储的元素数量超过65536，你将无法在数组中存储它们。
     * <p><code>世界上所有的字符会超过Unicode的65536么?</code> Unicode字符集目前定义了超过130,000个字符。但是，这些字符并不都在基本多语言平面（BMP）中，BMP包含的字符数量是65536。超出BMP的字符存储在其他16个辅助平面中，每个平面包含65536个字符位置。因此，如果你需要处理超出BMP的字符，你需要使用更大的数组或者其他数据结构来存储它们。
     * @return int
     * @throws
     * @Param [c]
         */
    public int getIndex(int c) {
        r.lock();
        try {
            if (branches == null) {
                return -1;
            }
            int i = Arrays.binarySearch(this.branches, new TrieNode(c));
            return i;
        } finally {
            r.unlock();
        }
    }

    /**
     * @Param [newBranch]
     * @description 添加新词
     */
    public boolean add(int[] word, MultiCodeMode mode, int code, int type) {
        w.lock();
        try {
            TrieNode tempBranch = this;
            Result<Boolean> added = new Result<Boolean>();
            added.setValue(false);
            for (int i = 0; i < word.length; i++) {
                if (word.length == i + 1) {
                    tempBranch = tempBranch.add(new TrieNode(word[i], 3, code, type), mode, added);
                } else {
                    tempBranch = tempBranch.add(new TrieNode(word[i], 1), mode, added);
                }
            }
            return added.getValue();
        } finally {
            w.unlock();
        }
    }

    /**
     * 增加子页节点
     * 添加一个叶子节点 需要判断当前节点的子节点集合中是否包含该节点
     * 如果包含 则需要更新 不包含则直接进行添加
     *
     * @param newBranch
     * @param mode
     * @return
     */
    private TrieNode add(TrieNode newBranch, MultiCodeMode mode,
                              Result<Boolean> added) {
        //默认设置为false
        added.setValue(false);
        //如果当前节点的子节点集合为空，则初始化一个空数组
        if (branches == null) {
            branches = new TrieNode[0];
        }
        //获取当前节点的子节点集合中是否包含该节点(当前字符)
        int bs = getIndex(newBranch.getC());
        //当前位置的子节点已经存在
        if (bs > -1) {
            // 更新既存子节点
            if (this.branches[bs] == null) {
                this.branches[bs] = newBranch;
            }
            // 获取当前节点的子节点
            TrieNode branch = this.branches[bs];
            // 判断当前节点的状态 -1无值 1是继续 2是个词语但是还可以继续 3是确定
            // 这里只可能是-1 1 3
            switch (newBranch.getStatus()) {
                case -1:
                    branch.status = 1;
                    break;
                case 1:
                    if (branch.status == 3) {
                        branch.status = 2;
                    }
                    break;
                case 3:
                    if (branch.status == 2 || branch.status == 3) {
                        // 多Code情况
                        if (MultiCodeMode.Drop == mode) {
                            added.setValue(addBranchOnDropMode(newBranch, branch));
                        } else if (MultiCodeMode.Replace == mode) {
                            added.setValue(addBranchOnReplaceMode(newBranch, branch));
                        } else if (MultiCodeMode.Small == mode) {
                            added.setValue(addBranchOnSmallMode(newBranch, branch));
                        } else if (MultiCodeMode.Big == mode) {
                            added.setValue(addBranchOnBigMode(newBranch, branch));
                        } else if (MultiCodeMode.Append == mode) {
                            added.setValue(addBranchOnAppendMode(newBranch, branch));
                        } else if (MultiCodeMode.ThrowException == mode) {
                            throw new UnsupportedOperationException("加入了不允许重复的词");
                        } else {
                            throw new UnsupportedOperationException("不支持的多码处理模式");
                        }
                    } else {
                        branch.type = newBranch.type;
                        branch.code = newBranch.code;
                        branch.status = 2;
                    }
            }
            return branch;
        }

        // 如果 bs 小于 0，表示需要新增子节点
        if (bs < 0) {
            // 获取当前 branches 数组的长度
            int l = branches.length;
            // 创建一个新的 branches 数组，长度比原来的数组多1
            TrieNode[] newBranches = new TrieNode[branches.length + 1];
            /**
             * 这里插入维护了一个有序的branches数组
             */
            // 计算新节点应该插入的位置,位置0
            int insert = -(bs + 1);
            // 如果插入位置不是数组的起始位置，将原数组从起始位置到插入位置的元素复制到新数组
            if (insert > 0) {
                System.arraycopy(this.branches, 0, newBranches, 0, insert);
            }
            // 如果插入位置不是数组的末尾位置，将原数组从插入位置到末尾的元素复制到新数组的插入位置之后
            if (branches.length - insert > 0) {
                System.arraycopy(branches, insert, newBranches, insert + 1,
                        branches.length - insert);
            }
            // 在新数组的插入位置添加新节点
            newBranches[insert] = newBranch;
            // 标记已经添加了新节点
            added.setValue(true);
            // 将新的 branches 数组赋值给当前对象的 branches 属性
            this.branches = newBranches;
        }
        return newBranch;
    }

    /**
     * 通过扩展码添加
     * @param newBranch
     * @param branch
     * @return
     */
    private boolean addBranchOnDropMode(TrieNode newBranch, TrieNode branch) {
        if (branch.type != CodeTypes.MULTI_CODE) {
            if (newBranch.type == branch.type) {
                // 后加的码被丢弃
                return false;
            } else {
                // 通过扩展码添加
                return addExtCode(branch, newBranch);
            }
        } else {
            if (multiCodeLookupTable.getCode(branch.code, newBranch.type).length > 0) {
                // 后加的码被丢弃
                return false;
            } else {
                // 通过扩展码添加
                return multiCodeLookupTable.addCode(branch.code, new TrieCode(
                        newBranch.code, newBranch.type));
            }
        }
    }

    /**
     * 通过替换TrieCode添加
     * @param branch 老的分支
     * @param newBranch 新的分支
     * @return
     */
    private boolean addExtCode(TrieNode branch, TrieNode newBranch) {
        TrieCode newValue = new TrieCode(newBranch.code, newBranch.type);
        TrieCode oldValue = new TrieCode(branch.code, branch.type);
        long key = multiCodeLookupTable.newCode(oldValue, newValue);
        // 设置新的Code
        branch.code = key;
        // 设置为多码
        branch.type = CodeTypes.MULTI_CODE;
        return true;
    }

    /**
     * 分支 重置替换（新替旧）模式
     *
     * @return java.lang.Boolean
     * @throws
     * @Param [newBranch, branch]
     * @Date 2023/1/16 20:52
     */
    private Boolean addBranchOnReplaceMode(TrieNode newBranch, TrieNode branch) {
        try {
            // 状态为确定的词语，且新词语的状态为确定，且新词语的码不等于老词语的码
            if (newBranch.status == 3
                    && (branch.status == 3 || branch.status == 2)
                    && newBranch.code != branch.code) {
                branch.code = newBranch.code;
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 分支 保留小的码模式
     * @param newBranch
     * @param branch
     * @return
     */
    private boolean addBranchOnSmallMode(TrieNode newBranch, TrieNode branch) {
        if (branch.type != CodeTypes.MULTI_CODE) {
            if (newBranch.type == branch.type) {
                // 保留码小的
                if (newBranch.code < branch.code) {
                    branch.type = newBranch.type;
                    branch.code = newBranch.code;
                    return true;
                } else {
                    // 丢弃
                    return false;
                }
            } else {
                // 通过扩展码添加
                return addExtCode(branch, newBranch);
            }
        } else {
            TrieCode newCode = new TrieCode(newBranch.code, newBranch.type);
            TrieCode minCode = multiCodeLookupTable.getMinCode(branch.code,
                    newCode);
            // 保留码小的
            int ret = multiCodeLookupTable.replaceOrRemoveCode(branch.code,
                    minCode);
            if (ret == 0) {
                // 多码变单码的调整
                branch.code = minCode.getCode();
                branch.type = minCode.getType();
            }
            return true;
        }
    }

    /**
     * 分支 保留大的码模式
     * @param newBranch
     * @param branch
     * @return
     */
    private boolean addBranchOnBigMode(TrieNode newBranch, TrieNode branch) {
        if (branch.type != CodeTypes.MULTI_CODE) {
            if (newBranch.type == branch.type) {
                // 保留码大的
                if (newBranch.code > branch.code) {
                    branch.type = newBranch.type;
                    branch.code = newBranch.code;
                    return true;
                } else {
                    // 丢弃
                    return false;
                }
            } else {
                // 通过扩展码添加
                return addExtCode(branch, newBranch);
            }
        } else {
            TrieCode newCode = new TrieCode(newBranch.code, newBranch.type);
            TrieCode maxCode = multiCodeLookupTable.getMaxCode(branch.code,
                    newCode);
            // 保留码大的
            int ret = multiCodeLookupTable.replaceOrRemoveCode(branch.code,
                    maxCode);
            if (ret == 0) {
                // 多码变单码的调整
                branch.code = maxCode.getCode();
                branch.type = maxCode.getType();
            }
            return true;
        }
    }

    /**
     * 分支 追加模式
     * @param newBranch
     * @param branch
     * @return
     */
    private boolean addBranchOnAppendMode(TrieNode newBranch, TrieNode branch) {
        // 追加模式
        TrieCode newValue = new TrieCode(newBranch.code, newBranch.type);
        if (branch.type != CodeTypes.MULTI_CODE) {
            TrieCode oldValue = new TrieCode(branch.code, branch.type);
            if (!newValue.equals(oldValue)) {
                long key = multiCodeLookupTable.newCode(oldValue, newValue);
                branch.code = key;
                branch.type = CodeTypes.MULTI_CODE;
                return true;
            } else {
                branch.type = newBranch.type;
                branch.code = newBranch.code;
                return true;
            }
        } else {
            return multiCodeLookupTable.addCode(branch.code, newValue);
        }
    }

    /**
     * Trie 树中移除一个结束节点,比如 abc 是一个词语,那么 c 是一个结束节点
     * 当前存在 a ab abc,此时把 abc 移除,那么 c 也需要移除
     * @param path 路径
     * @param code 绑定的编码
     * @param type 编码类型
     * @return
     */
    private boolean removeEndNode(TrieNodePath path, int code, int type) {
        TrieNode branch = path.getNode();
        if (branch.getStatus() == 2 || branch.getStatus() == 3) {
            if (branch.type != CodeTypes.MULTI_CODE) {
                // 单码的情况
                if (branch.code == code && branch.type == type) {
                    // 找到匹配的节点，从父节点移除该节点
                    TrieNodePath parentPath = path.getParent();
                    TrieNode parent = parentPath.getNode();
                    removeFromParent(branch, parent);
                    if (branch.getStatus() == 3) {
                        // 如果移除的节点为终节点，还可能需要移除路径上的无用中间节点
                        removeOrphanNode(parentPath);
                    }
                    return true;
                } else {
                    // 没有找到匹配的节点
                    return false;
                }
            } else {
                // 多码的情况
                boolean remove = multiCodeLookupTable.removeCode(branch.code,
                        new TrieCode(code, type));
                if (remove) {
                    TrieCode[] codes = multiCodeLookupTable
                            .getCode(branch.code);
                    if (codes != null && codes.length == 1) {
                        // 多码变单码的调整
                        branch.code = codes[0].getCode();
                        branch.type = codes[0].getType();
                    } else if (codes == null || codes.length == 0) {
                        // 找到匹配的节点，从父节点移除该节点
                        TrieNodePath parentPath = path.getParent();
                        TrieNode parent = parentPath.getNode();
                        removeFromParent(branch, parent);
                        if (branch.getStatus() == 3) {
                            // 如果移除的节点为终节点，还需移除中间节点
                            removeOrphanNode(parentPath);
                        }
                    } else {
                        // 无需调整
                    }
                    return true;
                } else {
                    // 没有找到匹配的节点
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 从父节点移除一个分支
     * @param branch
     * @param parent
     */
    private void removeFromParent(TrieNode branch, TrieNode parent) {
        int index = parent.getIndex(branch.c);
        TrieNode[] newBranches = null;
        if (parent.branches.length - 1 > 0) {
            newBranches = new TrieNode[parent.branches.length - 1];
            if (index > 0) {
                System.arraycopy(parent.branches, 0, newBranches, 0, index);
            }
            if (parent.branches.length - index > 0) {
                System.arraycopy(parent.branches, index + 1, newBranches,
                        index, newBranches.length - index);
            }
        }
        parent.branches = newBranches;
    }

    /**
     * 移除一个词
     * @param path
     */
    private void removeOrphanNode(TrieNodePath path) {
        TrieNode node = path.getNode();
        if ((node.branches == null || node.branches.length == 0)) {
            if (node.getStatus() == 1) {
                if (path.getParent() != null) {
                    removeFromParent(path.getNode(), path.getParent().getNode());
                    removeOrphanNode(path.getParent());
                }
            } else if (node.getStatus() == 2) {
                node.status = 3;
            }
        }
    }

    /**
     * 二分查找是否包含字符
     *
     * @param c
     * @return
     */
    public boolean contains(int c) {
        r.lock();
        try {
            if (this.branches == null) {
                return false;
            }
            return Arrays.binarySearch(this.branches, c) > -1;
        } finally {
            r.unlock();
        }
    }

    /**
     * <p>在前缀树中，每个节点都有一个分支数组，这个数组包含了指向该节点的所有子节点的引用。这个数组的索引通常是根据子节点的字符值来确定的。因此，即使两个节点的哈希码相同，只要它们在树中的位置不同（即它们的父节点不同或者它们在分支数组中的索引不同），我们仍然可以区分它们。
     * <p>根据c，status和code这三个字段来生成哈希码的。这意味着，如果两个TrieNode对象的这三个字段都相等，那么它们的哈希码也会相等。
     * <br>
     * <p><code>31倍哈希码</code>
     * <p>首先初始化一个非零的常数（在这里是17），然后对每个要包含在哈希码计算中的字段，我们都将结果乘以31（一个奇素数）然后加上该字段的哈希码。这种方法被称为Effective Java中的“31倍哈希码”技巧。
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + c;
        return result;
    }


}
