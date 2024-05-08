package util;

import newTrie.lang.Result;
import newTrie.lang.WordString;
import newTrie.lang.WordStringFactory;
import sun.misc.Unsafe;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <code>一、基本介绍</code>
 * <p>一棵前缀树（字典树），支持检索的完全并发性和更新的高预期并发性。在一个文本中的快锁检索出对应的词语。然而整个过程是线程安全的。
 * 它可以支持所有操作线程安全并且不需要加锁，并且没有任何锁定整个表的支持以组织所有访问的方式。这个类的线程安全性是内建的,
 * 使用这个类不需要了解这个类的同步细节，而不需要做额外的同步控制。无法像{@link java.util.concurrent.ConcurrentHashMap}那样遍历所有元素。
 *
 * <br>
 * <p><code>二、基本方法</code>
 * <p>这个类的实现是基于一个前缀树，这个树是一个有向无环图，每个节点有一个或多个子节点。每个节点代表一个字符，从根节点到这个节点的路径代表一个字符串。
 * <ul>
 *     <li>检索操作（包含{@code get}），通常不会阻塞，因此可能与更新操作（包括{@code put}和{@code remove}）重叠。检索操作反映的是在它们开始时最近完成的更新操作的结果。
 *  * 更正式地说，对给定键的更新操作在任何报告更新值的（非空）检索操作之前发生。</li>
 *     <li>对于像clear这样的聚合操作,并发检索可能只反映一些条目的删除。</li>
 *     <li>对于{@code size}、{@code isEmpty}和{@code containsValue}在内的聚合状态方法的结果只是瞬间状态，并不是实时。
 *  也就是说如果在使用聚合方法的时候去进行并发{@code put}和{@code remove}操作可能无法获取到当前的状态，而是并发操作发生之前的状态。</li>
 * </ul>
 *
 * <br>
 * <p><code>三、节点扩容、构造函数和迭代器</code>
 * <p>这里的节点扩容是指的是给某个节点上的下一层数组进行扩容，这个扩容是线程安全的，不会出现并发问题。不会预留空间。每次扩容或者缩容。
 * 在有参构造器中进行深拷贝。自定义迭代器，实现了{@link Iterable}接口。默认的迭代器是层序顺序遍历。BFS广度有限搜索。不保证顺序。
 *
 * <br>
 * <p><code>四、ForkJoin分支合并函数</code>
 * <p>这个{@link ForEachTrieTask}实现了{@link ForEachForkJoinInterface}接口，支持ForkJoin的函数。可以并行遍历前缀树的任务。
 * 在这个系列中，我们将会实现更多的ForkJoin的函数。可以设置并行阈值，当size大于这个阈值的时候，就会进行并行遍历。
 * 并行阈值（parallelismThreshold）：批量操作接受一个名为parallelismThreshold的参数。如果当前映射的大小预计小于给定的阈值，方法将顺序执行。
 * 通过划分足够的子任务来充分利用ForkJoinPool.commonPool()，这个池用于所有并行计算。通常，你会首先选择这些极端值中的一个，然后测量使用介于两者之间的值（在开销和吞吐量之间进行权衡）的性能。
 */
public class Trie implements Serializable, Iterable<Trie.TrieNodeWrapper>,
        ForEachForkJoinInterface, SearchForkJoinInterface {

    //序列号
    @Serial
    private static final long serialVersionUID = 7249069246763182397L;

    //前缀树根节点
    private TrieNode mainTree;

    //实体数量,是瞬间值，可能是过去某一时刻的值
    private transient volatile AtomicInteger size = new AtomicInteger(0);
    private transient volatile AtomicInteger deep = new AtomicInteger(0);

    /**
     * 无参构造函数 (初始化)
     */
    public Trie() {
        this.mainTree = new TrieNode();
    }

    /**
     * 有参构造函数
     * 深拷贝
     */
    public Trie(TrieNode otherRoot) {
        this.mainTree = new TrieNode(otherRoot);
    }

    @Override
    public Iterator<TrieNodeWrapper> iterator() {
        return new TrieIterator(mainTree);
    }

    /**
     * size
     */
    public int size() {
        return size.get();
    }

    /**
     * deep
     */
    public int deep() {
        TrieQuery trieQuery = new TrieQuery(mainTree, WordStringFactory.create(""), true);
        return trieQuery.getDeep();
    }

    /**
     * 获取辅助子树深度
     */
    public int getDeep(String word) {
        TrieQuery trieQuery = new TrieQuery(mainTree,  WordStringFactory.create(word), true);
        return trieQuery.getDeep();
    }

    /**
     * 获取root
     */
    public TrieNode getRoot() {
        return mainTree;
    }

    /**
     * 并行遍历前缀树的任务。
     *
     * @param parallelismThreshold 并行阈值
     * @param transformer          转换器
     * @param action               操作
     * @param <U>
     */
    @Override
    public <U> void forEachParallel(int parallelismThreshold, Function<TrieNodeWrapper, U> transformer, Consumer<U> action) {
        if (size() > parallelismThreshold) {
            ForkJoinPool.commonPool().invoke(new ForEachTrieTask<U>(new TrieNodeWrapper(this.mainTree,"",0), transformer, action, new Semaphore(parallelismThreshold)));
        } else {
            forEach(node -> action.accept(transformer.apply(node)));
        }
    }


    @Override
    public TrieNodeWrapper searchParallel(int parallelismThreshold) {
        ForkJoinPool pool = new ForkJoinPool(parallelismThreshold);
        try {
            return pool.invoke(new SearchTask(this.mainTree, new ArrayList<>()));
        } finally {
            pool.shutdown();  // 关闭ForkJoinPool
        }
    }

    /**
     * 增加 word
     */
    public boolean add(int[] word) {
        return add(word, MultiCodeMode.Append, -1, -1);
    }

    /**
     * 增加 word code
     */
    public boolean add(int[] word, int code) {
        return add(word, MultiCodeMode.Append, code, -1);
    }

    /**
     * 增加 word 选定mode code
     */
    public boolean add(int[] word, MultiCodeMode mode, int code) {
        return add(word, mode, code, -1);
    }

    /**
     * 增加 word 选定mode code type
     */
    public boolean add(int[] word, int code, int type) {
        return add(word, MultiCodeMode.Append, code, type);
    }

    /**
     * 增加 word 选定mode code type
     */
    public boolean add(int[] word, MultiCodeMode mode, int code, int type) {
        boolean add = this.mainTree.add(word, mode, code, type);
        if (add) size.incrementAndGet();
        return add;
    }

    /**
     * 增加 word
     */
    public boolean add(String word) {
        return add(TokenizerUtil.codePoints(word), MultiCodeMode.Append, -1, -1);
    }

    /**
     * 增加 word code
     */
    public boolean add(String word, int code) {
        return add(TokenizerUtil.codePoints(word), MultiCodeMode.Append, code, -1);
    }

    /**
     * 增加 word 选定mode code
     */
    public boolean add(String word, MultiCodeMode mode, int code) {
        return add(TokenizerUtil.codePoints(word), mode, code, -1);
    }

    public boolean add(String word,  int code, int type) {
        return add(TokenizerUtil.codePoints(word), MultiCodeMode.Append, code, type);
    }

    /**
     * 增加 word 选定mode code type
     */
    public boolean add(String word, MultiCodeMode mode, int code, int type) {
        boolean add = mainTree.add(TokenizerUtil.codePoints(word), mode, code, type);
        if (add) size.incrementAndGet();
        return add;
    }


    /**
     * 删除操作
     */
    /**
     * 必须是具体的字符串，不能模糊
     */
    public boolean remove(String word, int code, int type) {
        boolean remove = mainTree.remove(TokenizerUtil.codePoints(word), code, type);
        size.decrementAndGet();
        return remove;
    }

    /**
     * 必须是具体的字符串，不能模糊
     */
    public boolean remove(int[] word, int code, int type) {
        boolean remove = mainTree.remove(word, code, type);
        size.decrementAndGet();
        return remove;
    }


    /**
     * 查询操作 : 获取第一个匹配到的数据
     */
    public TrieQueryResult getFirstPrefixWord(String text) {
        TrieQuery trieQuery = new TrieQuery(mainTree, WordStringFactory.create(text), false);
        TrieQueryResult query = trieQuery.query();
        return query;
    }

    /**
     * 查询操作 : 获取所有匹配到的数据 (如果前缀是词也不包含前缀)
     */
    public List<TrieQueryResult> getAllWordsWithoutPrefixes(String text) {
        TrieQuery trieQuery = new TrieQuery(mainTree, WordStringFactory.create(text), false);
        List<TrieQueryResult> query = trieQuery.queryAll();
        return query;
    }

    /**
     * 查询操作 : 获取所有匹配到的数据 (如果前缀是词也包含前缀)
     */
    public List<TrieQueryResult> getAllWordsWithPrefixes(String text) {
        TrieQuery trieQuery = new TrieQuery(mainTree, WordStringFactory.create(text), true);
        List<TrieQueryResult> query = trieQuery.queryAll();
        return query;
    }

    //获取所有匹配到前缀的数据
//    public TriePrefixQueryResult getPrefix(String word) {
//        TirePrefixQuerier tirePrefixQuerier = new TirePrefixQuerier(mainTree, new TokenizerObject(word));
//        TriePrefixQueryResult triePrefixQueryResult = tirePrefixQuerier.queryAllPrefix();
//        return triePrefixQueryResult;
//    }

    /**
     * 清除
     */
    @Deprecated
    public void clear() {
        mainTree = null;
        size.set(0);
        deep.set(0);
    }


    /**
     * 通过反射获取Unsafe实例
     * 提供了一组底层的原子操作，包括对内存的直接访问和CAS（Compare and Swap）操作。
     */
    private static final Unsafe U;
    //控制表的大小，用于并发控制。
    private static final long SIZECTL;
    //控制调整大小的操作，用于并发控制（扩容）
    private static final long TRANSFERINDEX;
    //保存ConcurrentHashMap的元素数量。
    private static final long BASECOUNT;
    //控制对counterCells数组的访问，用于并发控制。
    private static final long CELLSBUSY;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            U = (Unsafe) theUnsafeField.get(null);
            Class<?> k = ConcurrentHashMap.class;
            SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
            BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
            CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 添加单词的长度要不超过50,防止层数过深
     *
     * @param word
     * @return
     */
    private boolean lengthLimit(int[] word) {
        return word.length <= 50;
    }

    private boolean lengthLimit(String word) {
        return word.length() <= 50;
    }

    /**
     * 查询器
     */
    private static class TrieQuery {

        /**
         * 前缀树根节点，搜索、插入和删除操作的起点
         */
        private TrieNode trieRootNode;
        /**
         * 存储前缀树的根节点，TrieQuery 类中的作用是作为一个备份或原始的根节点。
         * next() 方法中，trieRootNode 可能会被修改为其他节点，而 sourceRoot 则始终保持为原始的根节点。这样做的目的是为了在需要时能够重置 trieRootNode 到原始的根节点，例如在 next() 方法中，当找到一个匹配的词语后，trieRootNode 会被重置为 sourceRoot。
         */
        private TrieNode sourceRoot;

        /**
         * 用于查询的字符串内容
         */
        private WordString content;

        /**
         * 已探测到的字符串位置
         */
        private int root;

        /**
         * 下一个探测的字符串位置
         */
        private int i;

        /**
         * 当前探测到的词，在查询字符串中的开始位置
         */
        private int offset;

        /**
         * 标记是否可能需要回退
         */
        private boolean isBack;

        /**
         * 临时的位置用于回退的场合
         */
        private int iTemp;

        /**
         * 临时的节点用于回退的场合
         */
        private TrieNode nodeTemp;

        /**
         * 只查询开头匹配的
         */
        private boolean beginOnly;

        /**
         * 根节点是否回退
         */
        private boolean isRootReset;
        /**
         * 辅助树查询器
         */
        private TrieQuery assistedQuery;

        /**
         * 是否开启全量搜索
         * <p>
         * 不开启全量搜索，只搜索第一个匹配的词语
         * 开启全量搜索，搜索所有匹配的词语
         *
         * <br>
         * <p><code>举个例子，假设我们有一个字符串 "abcabc"，并且我们的 Trie 树中有 "abc","bca","cab"</code>
         * <br>
         * <p><code>enableTrieAllSearch = false</code>
         * <p>如果我们搜索 "abcabc"，我们会找到第一个 "abc"，然后从第二个 "a" 开始继续搜索，这样我们就只能找到两个 "abc"。</p>
         *
         *
         * <br>
         * <p><code>enableTrieAllSearch = true</code>
         * <p>我们搜索 "abcabc"，我们会找到第一个 "abc"，然后从第一个 "b" 开始继续搜索，这样我们就可以找到 "bca"，然后从第二个 "a" 开始继续搜索，找到第二个 "abc"，然后从第二个 "b" 开始继续搜索，找到第二个 "bca"，然后从第二个 "c" 开始继续搜索，找到 "cab"。所以，我们可以找到两个 "abc"，两个 "bca" 和一个 "cab"。
         */
        private boolean enableTrieAllSearch;

        public TrieQuery(TrieNode trieRootNode, WordString content, boolean enableTrieAllSearch) {
            this.content = content;
            this.trieRootNode = trieRootNode;
            this.sourceRoot = trieRootNode;
            this.enableTrieAllSearch = enableTrieAllSearch;
        }

        public void setBeginOnly(boolean beginOnly) {
            this.beginOnly = beginOnly;
        }

        public void setAssistedTrie(TrieNode assistedTrie) {
            this.assistedQuery = new TrieQuery(assistedTrie, this.content, false);
            this.assistedQuery.setBeginOnly(true);
        }

        /**
         * 查询
         *
         * @return
         */
        public TrieQueryResult query() {
            return next();
        }

        public List<TrieQueryResult> queryAll() {
            List<TrieQueryResult> trieQueryResults = new ArrayList<>();
            TrieQueryResult result;
            while ((result = processQuery()) != null) {
                trieQueryResults.add(result);
            }
            return trieQueryResults;
        }

        public TrieQueryResult processQuery() {
            int length = this.content.length();
            TrieNode trieNode = this.trieRootNode;
            TrieQueryResult trieQueryResult = null;

            for (; this.i < length + 1; this.i++) {
                if (i == length) {
                    trieNode = null;
                } else {
                    int c = this.content.charAt(this.i);
                    trieNode = trieNode.getBranch(c);
                }

                if (trieNode == null) {
                    if (this.isRootReset) {
                        this.trieRootNode = this.sourceRoot;
                        this.isRootReset = false;
                    }
                    trieNode = this.trieRootNode;

                    if (this.isBack) {
                        this.offset = this.root;
                        String str = new String(this.content.toIntArray(),
                                this.root, this.iTemp - this.root + 1);

                        if (str.length() > 0) {
                            this.i = this.root + 1;
                            this.root = this.i;
                            trieQueryResult = new TrieQueryResult(str, this.offset,
                                    nodeTemp.getCodes());
                        }
                        this.isBack = false;
                        this.nodeTemp = null;
                        return trieQueryResult;
                    }
                    this.i = this.root;
                    this.root += 1;
                } else {
                    switch (trieNode.getStatus()) {
                        case 2: {
                            this.iTemp = this.i;
                            this.nodeTemp = trieNode;
                            if (enableTrieAllSearch) {
                                String str1 = new String(this.content.toIntArray(),
                                        this.root, this.iTemp - this.root + 1);
                                if (str1.length() > 0) {
                                    trieQueryResult = new TrieQueryResult(str1, this.root,
                                            nodeTemp.getCodes());
                                    this.i += 1;
                                    this.trieRootNode = trieNode;
                                    this.isRootReset = true;
                                    return trieQueryResult;
                                }
                            } else {
                                this.isBack = true;
                            }
                            break;
                        }
                        case 3: {
                            this.offset = this.root;
                            String str = new String(this.content.toIntArray(),
                                    this.root, this.i - this.root + 1);
                            this.isBack = false;
                            if (str.length() > 0) {
                                this.i = this.offset + 1;
                                this.root = this.i;
                                trieQueryResult = new TrieQueryResult(str, this.offset,
                                        trieNode.getCodes());
                                this.trieRootNode = this.sourceRoot;
                            }
                            return trieQueryResult;
                        }
                    }
                }
            }
            return null;
        }

        public TrieQueryResult next() {
            return processQuery();
        }

        public void reset(int root) {
            this.offset = 0;
            this.root = root;
            this.i = root;
            this.isBack = false;
            this.iTemp = 0;
        }

        public int getDeep() {
            int deep = 0;
            if (trieRootNode.hasNext()) {
                deep = dfs(trieRootNode, deep);
            }
            return deep;
        }

        public int dfs(TrieNode nextTireNodes, int deep) {
            if (!nextTireNodes.hasNext()) {
                return deep;
            }
            int maxDepth = deep;
            TrieNode[] trieNodes = nextTireNodes.branches;
            for (int j = 0; j < trieNodes.length; j++) {
                TrieNode x = trieNodes[j];
                int childDepth = dfs(x, deep + 1);
                maxDepth = Math.max(maxDepth, childDepth);
            }
            return maxDepth;
        }

    }

    /**
     * <code>节点Code值</code>
     */
    private class TrieCode implements Serializable {

        @Serial
        private static final long serialVersionUID = 3881643557612826255L;

        /**
         * 编码 比如 161651315
         */
        private int code;

        /**
         * 限定编码的类型  -128 到 127 比如 1 是某个业务类的编码
         */
        private byte type;

        public TrieCode() {
        }

        public TrieCode(int code, byte type) {
            this.code = code;
            this.type = type;
        }

        public TrieCode(int code, int type) {
            this.code = code;
            this.type = (byte) type;
        }

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

    class TrieNodeWrapper{
        private TrieNode node;
        private String value;
        private int length; // Add this field to store the length of the current word

        public TrieNodeWrapper(TrieNode node, String value, int length){
            this.node = node;
            this.value = value;
            this.length = length;
        }

        public String getValue() {
            return value;
        }

        public TrieNode getNode() {
            return node;
        }

        public int getLength() {
            return length;
        }
    }

    private class TrieIterator implements Iterator<TrieNodeWrapper> {
        private Stack<TrieNodeWrapper> stack;
        private StringBuilder currentWord;

        public TrieIterator(TrieNode root) {
            stack = new Stack<>();
            currentWord = new StringBuilder();
            if (root != null) {
                stack.push(new TrieNodeWrapper(root, "",0));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public TrieNodeWrapper next() {
            while (!stack.isEmpty()) {
                TrieNodeWrapper nodeWrapper = stack.pop();
                TrieNode node = nodeWrapper.node;
                currentWord.setLength(nodeWrapper.getLength()); // Reset the length of currentWord
                currentWord.append(nodeWrapper.value);

                if (node.status == 3) { // Assuming 'status' indicates whether it's a complete word
                    String word = currentWord.toString();
                    return new TrieNodeWrapper(node, word, currentWord.length());
                }

                if (node.branches != null) {
                    for (TrieNode child : node.branches) {
                        if (child != null) {
                            String childWord = (char) child.c + "";
                            stack.push(new TrieNodeWrapper(child, childWord, currentWord.length()));
                        }
                    }
                }
            }
            return null; // No more words
        }
    }


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
         * <p>
         * 1.使用Integer.MAX_VALUE 也就是多码表的上限是2147483647
         * 2.使用long类型的最大值9223372036854775807，实际上不会达到，如果有需求就改成long
         * TODO 暂时使用public后面改private
         */
        public int code;

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
         *
         * @return TrieCode
         */
        public TrieCode[] getCodes() {
            r.lock();
            try {
                if (this.type == CodeTypes.MULTI_CODE) {
                    return multiCodeLookupTable.getCode(this.code);
                } else {
                    return new TrieCode[]{new TrieCode(this.code, this.type)};
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
            return getBranch(c);
        }

        /**
         * 通过传入字符查询当前数组索引，获取分支节点
         *
         * @Param [c]
         */
        public TrieNode getBranch(int c) {
            r.lock();
            try {
                int index = getIndex(c);
                if (index < 0) {
                    return null;
                } else {
                    return this.branches[index];
                }
            } finally {
                r.unlock();
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
         * 获取单词
         *
         * @param str
         * @param enableTrieAllSearch
         * @return
         */
        public TrieQuery getWord(WordString str, boolean enableTrieAllSearch) {
            return new TrieQuery(this, str, enableTrieAllSearch);
        }

        /**
         * 取得所有的分支
         *
         * @return
         */
        public TrieNode[] getBranches() {
            return this.branches;
        }

        /**
         * 获取字符在数组中的索引
         *
         * <p><code>值得考虑二分么？</code> 如果你的数组长度固定为65536，且每个元素都可能被使用，那么使用二分搜索是值得的。因为二分搜索的时间复杂度为O(log n)，在这种情况下，它将比线性搜索更高效。
         * <p><code>会出现的问题？</code> 如果你的数组长度固定为65536，但实际使用的元素数量远小于这个数，那么你将面临内存浪费的问题。此外，如果你需要存储的元素数量超过65536，你将无法在数组中存储它们。
         * <p><code>世界上所有的字符会超过Unicode的65536么?</code> Unicode字符集目前定义了超过130,000个字符。但是，这些字符并不都在基本多语言平面（BMP）中，BMP包含的字符数量是65536。超出BMP的字符存储在其他16个辅助平面中，每个平面包含65536个字符位置。因此，如果你需要处理超出BMP的字符，你需要使用更大的数组或者其他数据结构来存储它们。
         *
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
         *
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
         *
         * @param branch    老的分支
         * @param newBranch 新的分支
         * @return
         */
        private boolean addExtCode(TrieNode branch, TrieNode newBranch) {
            TrieCode newValue = new TrieCode(newBranch.code, newBranch.type);
            TrieCode oldValue = new TrieCode(branch.code, branch.type);
            int key = multiCodeLookupTable.newCode(oldValue, newValue);
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
         *
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
         *
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
         *
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
                    int key = multiCodeLookupTable.newCode(oldValue, newValue);
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
         * 移除一个词
         *
         * @param word
         * @param code
         * @param type
         * @return
         */
        public boolean remove(int[] word, int code, int type) {
            w.lock();
            try {
                TrieNodePath path = getBranchPath(word);
                if (path != null) {
                    return removeEndNode(path, code, type);
                }
                return false;
            } finally {
                w.unlock();
            }
        }

        /**
         * Trie 树中移除一个结束节点,比如 abc 是一个词语,那么 c 是一个结束节点
         * 当前存在 a ab abc,此时把 abc 移除,那么 c 也需要移除
         *
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
         *
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
         *
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
         * 清空当前TrieNode节点
         */
        public void clear() {
            w.lock();
            try {
                this.branches = null;
            } finally {
                w.unlock();
            }
        }

        /**
         * 获取分支数量
         *
         * @return
         */
        public long totalBranchCount() {
            r.lock();
            try {
                AtomicLong atomicLong = new AtomicLong(0);
                visit(this, atomicLong);
                return atomicLong.get();
            } finally {
                r.unlock();
            }
        }

        /**
         * dfs遍历
         *
         * @param forest
         * @param atomicLong
         */
        private void visit(TrieNode forest, AtomicLong atomicLong) {
            atomicLong.incrementAndGet();
            if (forest.branches != null) {
                for (TrieNode child : forest.branches) {
                    visit(child, atomicLong);
                }
            }
        }

        @Override
        public String toString() {
            int tc = this.c;
            if (tc < Trie.CodePointUtil.EXT_CHAR_BASE) {
                return Trie.CodePointUtil.toString(tc) + "(" + getExtString() + ")";
            } else {
                return String.valueOf(tc) + "(" + getExtString() + ")";
            }
        }

        /**
         * 获取扩展字符串
         *
         * @return
         */
        private String getExtString() {
            StringBuilder buf = new StringBuilder();
            buf.append("status:");
            buf.append(this.status);
            if (this.type == CodeTypes.MULTI_CODE) {
                TrieCode[] codes = multiCodeLookupTable.getCode(this.code);
                if (codes != null) {
                    buf.append(",[");
                    int index = 0;
                    for (TrieCode code : codes) {
                        if (index > 0) {
                            buf.append(",");
                        }
                        buf.append("{");
                        buf.append("code:");
                        buf.append(code.getCode());
                        buf.append(",type:");
                        buf.append(code.getType());
                        buf.append("}");
                        index++;
                    }
                    buf.append("]");
                }
            } else {
                buf.append(",code:");
                buf.append(this.code);
                buf.append(",type:");
                buf.append(this.type);
            }
            return buf.toString();
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
         * 是否有下一层
         *
         * @return
         */
        public boolean hasNext() {
            return branches != null && branches.length > 0 ? true : false;
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

        public void print() {
            print("", true);
        }

        private void print(String prefix, boolean isTail) {
            r.lock();
            try {
                System.out
                        .println(prefix + (isTail ? "└── " : "├── ") + toString());
                if (branches != null) {
                    for (int i = 0; i < branches.length - 1; i++) {
                        branches[i].print(prefix + (isTail ? "    " : "│   "),
                                false);
                    }
                    if (branches.length > 0) {
                        branches[branches.length - 1].print(prefix
                                + (isTail ? "    " : "│   "), true);
                    }
                }
            } finally {
                r.unlock();
            }
        }
    }

    private class TrieNodePath {

        private TrieNode node;
        private TrieNodePath parent;

        public TrieNode getNode() {
            return node;
        }

        public void setNode(TrieNode node) {
            this.node = node;
        }

        public TrieNodePath getParent() {
            return parent;
        }

        public void setParent(TrieNodePath parent) {
            this.parent = parent;
        }

    }

    private static class TrieQueryResult {

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

    /**
     * 代码类型
     */
    private class CodeTypes {

        /**
         * 多代码
         */
        public static final byte MULTI_CODE = Byte.MAX_VALUE;
    }

    /**
     * @author jmw
     * @Description 节点代码处理器
     */
    private enum MultiCodeMode {

        /**
         * 追加模式 (通过codetype的扩展，从而进行一词多码的支持)
         */
        Append,

        /**
         * 替换模式（后加的码替换先加的码）
         */
        Replace,

        /**
         * 丢弃模式（后加的码被丢弃）
         */
        Drop,

        /**
         * 小的码值保留模式（保留码小的）
         */
        Small,

        /**
         * 大的码值保留模式（保留码大的）
         */
        Big,

        /**
         * 抛异常
         */
        ThrowException
    }

    private static class MultiCodeLookupTable {

        private Map<Integer, TrieCode[]> multiCodeExtMap = new LinkedHashMap<Integer, TrieCode[]>();

        /**
         * currentKey 就确保了每个添加到 multiCodeExtMap 的键值对都有一个唯一的键。
         */
        private int currentKey = 0;

        /**
         * 创建Code在多码查找表上
         *
         * @param value1
         * @param value2
         * @return
         */
        public synchronized int newCode(TrieCode value1, TrieCode value2) {
            int newKey = currentKey++;
            if (!value1.equals(value2)) {
                multiCodeExtMap.put(newKey, new TrieCode[]{value1, value2});
            } else {
                multiCodeExtMap.put(newKey, new TrieCode[]{value1});
            }
            return newKey;
        }

        /**
         * 添加Code到多码查找表上
         *
         * @param key
         * @param code
         * @return
         */
        public synchronized boolean addCode(int key, TrieCode code) {
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
         *
         * @param key
         * @param tCode
         * @return
         */
        public synchronized TrieCode getMinCode(int key, TrieCode tCode) {
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
         *
         * @param key
         * @param tCode
         * @return
         */
        public synchronized TrieCode getMaxCode(int key, TrieCode tCode) {
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
         *
         * @param key
         * @return
         */
        public synchronized TrieCode[] getCode(int key) {
            return multiCodeExtMap.get(key);
        }

        /**
         * 获取Code
         *
         * @param key
         * @param type
         * @return
         */
        public synchronized TrieCode[] getCode(int key, byte type) {
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
         *
         * @param key
         * @param code
         * @return
         */
        public synchronized int replaceOrRemoveCode(int key, TrieCode code) {
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
         *
         * @param key
         * @param code
         * @return
         */
        public synchronized boolean removeCode(int key, TrieCode code) {
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

    private class TokenizerUtil {

        public static final int EXT_CHAR_BASE = 0x100000;

        public static int[] codePoints(String string) {
            if (string == null) {
                return null;
            }
            if (string.length() == 0) {
                return new int[0];
            }
            //代码基数器
            int count = string.codePointCount(0, string.length());
            int[] result = new int[count];
            for (int i = 0; i < count; i++) {
                //codePointAt是Java的一个指令，可以返回指定索引处的字符（Unicode代码点）。
                result[i] = string.codePointAt(i);
            }
            return result;
        }

        public static String toString(int c) {
            return new String(new int[]{c}, 0, 1);
        }

        public static String toString(int[] cs) {
            StringBuilder buf = new StringBuilder();
            for (int c : cs) {
                if (c >= TokenizerUtil.EXT_CHAR_BASE) {
                    buf.append("*");
                } else {
                    if (c != -1) {
                        buf.append(toString(c));
                    }
                }
            }
            return buf.toString();
        }
    }

    private class SearchTask extends RecursiveTask<TrieNodeWrapper> {
        private final TrieNode node;
        private final List<Integer> path;  // The path from the root to the current node

        public SearchTask(TrieNode node, List<Integer> path) {
            this.node = node;
            this.path = new ArrayList<>(path);  // Create a copy of the path
        }

        @Override
        protected TrieNodeWrapper compute() {
            if (node.status == 1 || node.status == 2) {
                // If the current node satisfies the condition, return it
                return new TrieNodeWrapper(node, "",0); // Assuming the value is null, adjust as needed
            } else {
                // Otherwise, create new tasks for all children
                List<SearchTask> tasks = new ArrayList<>();
                for (int i = 0; i < node.branches.length; i++) {
                    TrieNode child = node.branches[i];
                    if (child != null) {
                        List<Integer> childPath = new ArrayList<>(path);
                        childPath.add(i);  // Add the character of the child to the path
                        SearchTask task = new SearchTask(child, childPath);
                        task.fork();  // Start the task in a separate thread
                        tasks.add(task);
                    }
                }
                // Wait for all tasks to finish and check their results
                for (SearchTask task : tasks) {
                    TrieNodeWrapper result = task.join();  // Wait for the task to finish and get its result
                    if (result != null) {
                        // If a task found a node that satisfies the condition, return it
                        return result;
                    }
                }
                // If no tasks found a node that satisfies the condition, return null
                return null;
            }
        }
    }

    /**
     * 用于并行遍历前缀树的任务。
     */
    private final class ForEachTrieTask<U> extends RecursiveAction {
        private final TrieNodeWrapper nodeWrapper; // 节点
        private final Function<TrieNodeWrapper, U> transformer; // 转换器
        private final Consumer<U> action; // 操作
        private final Semaphore semaphore; // 信号量

        public ForEachTrieTask(TrieNodeWrapper nodeWrapper, Function<TrieNodeWrapper, U> transformer, Consumer<U> action, Semaphore semaphore) {
            this.nodeWrapper = nodeWrapper;
            this.action = action;
            this.semaphore = semaphore;
            this.transformer = transformer;
        }

        @Override
        protected void compute() {
            try {
                // 获取许可
                semaphore.acquire();

                // 对当前节点应用操作
                TrieNode node = nodeWrapper.node;
                if (node.status == 3) { // Assuming 'status' indicates whether it's a complete word
                    action.accept(transformer.apply(nodeWrapper));
                }

                // 对每个子节点创建并执行新的任务
                if (node.branches != null) {
                    for (TrieNode child : node.branches) {
                        if (child != null) {
                            new ForEachTrieTask<>(new TrieNodeWrapper(child, "",0), transformer, action, semaphore).fork();
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release();
            }
        }
    }

    private abstract class CodePointUtil {

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
            return new String(new int[]{c}, 0, 1);
        }

        public static String toString(int[] cs) {
            StringBuilder buf = new StringBuilder();
            for (int c : cs) {
                if (c >= newTrie.util.CodePointUtil.EXT_CHAR_BASE) {
                    buf.append("*");
                } else {
                    buf.append(toString(c));
                }
            }
            return buf.toString();
        }
    }


}

/**
 * 主要Trie中支持ForkJoin的函数
 */
interface ForEachForkJoinInterface extends ForkJoinInterface {

    //ForEachTrieTask

    /**
     * @param parallelismThreshold 并行阈值
     * @param transformer          转换器
     * @param action               操作
     * @param <U>                  泛型
     */
    public <U> void forEachParallel(int parallelismThreshold, Function<Trie.TrieNodeWrapper, U> transformer, Consumer<U> action);


}

interface ForkJoinInterface {
}

/**
 * 主要Trie中支持ForkJoin的函数
 */
interface SearchForkJoinInterface extends ForkJoinInterface {

    //SearchTrieTask

    public Trie.TrieNodeWrapper searchParallel(int parallelismThreshold);
}