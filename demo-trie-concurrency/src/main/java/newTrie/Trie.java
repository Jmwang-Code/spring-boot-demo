package newTrie;

import newTrie.inner.*;
import newTrie.inter.ForEachForkJoinInterface;
import newTrie.inter.SearchForkJoinInterface;
import newTrie.lang.WordString;
import newTrie.lang.WordStringFactory;
import sun.misc.Unsafe;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
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
public class Trie implements Serializable, Iterable<TrieNode>,
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
    public Iterator<TrieNode> iterator() {
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
        return deep.get();
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
    public <U> void forEachParallel(int parallelismThreshold, Function<TrieNode, U> transformer, Consumer<U> action) {
        if (size() > parallelismThreshold) {
            ForkJoinPool.commonPool().invoke(new ForEachTrieTask<U>(this.mainTree, transformer, action, new Semaphore(parallelismThreshold)));
        } else {
            forEach(node -> action.accept(transformer.apply(node)));
        }
    }

    @Override
    public TrieNode searchParallel(int parallelismThreshold) {
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

    //获取所有匹配到的数据 获取第一个的词
    public List<TrieQueryResult> getFirstAllWord(String text) {
        TrieQuery trieQuery = new TrieQuery(mainTree, WordStringFactory.create(text), false);
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
    public static class TrieQuery {

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

        /**
         * 查询操作
         *
         * @return
         */
        public TrieQueryResult next() {
            int len = this.content.length();
            TrieNode node = this.trieRootNode; // 从前缀树根节点开始探测
            TrieQueryResult result = null;
            for (; this.i < len + 1; this.i++) {
                if (i == len) {
                    node = null;
                } else {
                    int c = this.content.charAt(this.i);
                    if (this.assistedQuery != null) {
                        // 查询压缩后的字符编码
                        this.assistedQuery.reset(this.i);
                        TrieQueryResult res = this.assistedQuery.next();
                        if (res != null && res.getCodes() != null
                                && res.getCodes().length > 0) {
                            c = res.getCodes()[0].getCode();
//						if(enableTrieAllSearch) {
//							this.i += 1;
//						}else {
//							this.i += res.getWord().length() - 1;
//						}
                            this.i += res.getWord().length() - 1;
                        }
                    }
                    node = node.getBranch(c); // 查找下一个节点
                }
                if (node == null) {
                    if (isRootReset) {
                        this.trieRootNode = this.sourceRoot;
                        this.isRootReset = false;
                    }
                    node = this.trieRootNode; // 找不到，则从头开始探测


                    if (this.isBack) {
                        this.offset = this.root;
                        String str = new String(this.content.toIntArray(),
                                this.root, this.iTemp - this.root + 1);
                        if (str.length() > 0) {
                            if (enableTrieAllSearch) {
                                this.i = this.root + 1;
                            } else {
                                this.i = this.iTemp + 1;//原有会从匹配的尾部的下一个位置开始扫描
                            }
                            this.root = this.i;
                            result = new TrieQueryResult(str, this.offset,
                                    nodeTemp.getCodes());
                        }
                        this.isBack = false;
                        this.nodeTemp = null;
                        return result;
                    }
                    if (this.beginOnly) {
                        return null; // 如果只进行开头匹配，则无需再往下探测
                    }
                    this.i = this.root;
                    this.root += 1; // 向前移动已探测到的字符串位置
                } else {
                    switch (node.getStatus()) {
                        case 2:
                            // 2状态（是个词语但是还可以继续），继续往下探测，如没有则可能进行回退，因此这里进行一下记录
                            this.iTemp = this.i;
                            this.nodeTemp = node;
                            if (enableTrieAllSearch) {
                                //返回
                                String str1 = new String(this.content.toIntArray(),
                                        this.root, this.iTemp - this.root + 1);
                                if (str1.length() > 0) {
//							result = new TrieQueryResult(str1, this.offset,
//									nodeTemp.getCodes());
                                    result = new TrieQueryResult(str1, this.root,
                                            nodeTemp.getCodes());
                                    this.i += 1;
                                    this.trieRootNode = node;
                                    this.isRootReset = true;
                                    return result;
                                }
                            } else {
                                this.isBack = true;
                            }
                            break;
                        case 3:
                            this.offset = this.root;
                            String str = new String(this.content.toIntArray(),
                                    this.root, this.i - this.root + 1);
                            this.isBack = false;
                            if (str.length() > 0) {
                                if (enableTrieAllSearch) {
                                    this.i = this.offset + 1; // 移动
                                    this.isRootReset = false;
                                } else {
                                    this.i = this.i + 1; // 移动已探测到的字符串位置
                                }
                                this.root = this.i;
                                result = new TrieQueryResult(str, this.offset,
                                        node.getCodes());
                                this.trieRootNode = this.sourceRoot;
                            }
                            return result;
                    }
                }
            }
            return null;
        }

        /**
         * @return com.cn.jmw.trie.entity.TrieQueryResult
         * @Param []
         * @Date 2023/5/8 11:19
         * 查询allCode
         */
        public List<TrieQueryResult> queryAll() {
            //当前查询字符长度
            int length = this.content.length();
            //根节点探测
            TrieNode trieNode = this.trieRootNode;
            //返回结果
            List<TrieQueryResult> trieQueryResults = new ArrayList<>();
            TrieQueryResult trieQueryResult = null;

            for (; this.i < length + 1; this.i++) {
                /**
                 * 1.如果到达终点，则将当前节点置空。
                 * 2.如果未到达终点，则将将当前子树的引用指向
                 */
                if (i == length) {
                    //如果探测位置以及等于字符长度，说明到达终点
                    trieNode = null;
                } else {
                    //获取当前位置字符
                    int c = this.content.charAt(this.i);
                    //当前前缀子树返回
                    trieNode = trieNode.getBranch(c);
                }
                /**
                 * 1.当前节点等于null,说明已经到底，可以考虑回退的问题了。
                 * 2.如果当前节点不为null，说明可以继续探查。
                 */
                if (trieNode == null) {
                    //根节点是否重置
                    if (this.isRootReset) {
                        this.trieRootNode = this.sourceRoot;
                        this.isRootReset = false;
                    }
                    // 找不到，则从头开始探测
                    trieNode = this.trieRootNode;

                    //是否回退
                    if (this.isBack) {
                        this.offset = this.root;
                        String str = new String(this.content.toIntArray(),
                                this.root, this.iTemp - this.root + 1);

                        //如果字符长度大于0
                        if (str.length() > 0) {
                            if (enableTrieAllSearch) {
                                this.i = this.root + 1;
                            } else {
                                //原有会从匹配的尾部的下一个位置开始扫描
                                this.i = this.iTemp + 1;
                            }
                            this.root = this.i;
                            trieQueryResult = new TrieQueryResult(str, this.offset,
                                    nodeTemp.getCodes());
                        }
                        //不需要回退
                        this.isBack = false;
                        this.nodeTemp = null;
                        trieQueryResults.add(trieQueryResult);
                    }
                    this.i = this.root;
                    // 向前移动已探测到的字符串位置
                    this.root += 1;
                } else {
                    switch (trieNode.getStatus()) {
                        //是词段，但是还可以继续探查，如果没有属性则可以回退。
                        case 2: {
                            this.iTemp = this.i;
                            this.nodeTemp = trieNode;
                            //如果允许全查询
                            if (enableTrieAllSearch) {
                                //返回
                                String str1 = new String(this.content.toIntArray(),
                                        this.root, this.iTemp - this.root + 1);
                                if (str1.length() > 0) {
                                    trieQueryResult = new TrieQueryResult(str1, this.root,
                                            nodeTemp.getCodes());
                                    this.i += 1;
                                    this.trieRootNode = trieNode;
                                    this.isRootReset = true;
                                    trieQueryResults.add(trieQueryResult);
                                }
                            } else {
                                this.isBack = true;
                            }
                            break;
                        }
                        case 3:
                            this.offset = this.root;
                            String str = new String(this.content.toIntArray(),
                                    this.root, this.i - this.root + 1);
                            this.isBack = false;
                            if (str.length() > 0) {
                                if (enableTrieAllSearch) {
                                    this.i = this.offset + 1; // 移动
                                    this.isRootReset = false;
                                } else {
                                    this.i = this.i + 1; // 移动已探测到的字符串位置
                                }
                                this.root = this.i;
                                trieQueryResult = new TrieQueryResult(str, this.offset,
                                        trieNode.getCodes());
                                this.trieRootNode = this.sourceRoot;
                            }
                            trieQueryResults.add(trieQueryResult);
                    }
                }
            }
            return trieQueryResults;
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
                dfs(trieRootNode,deep);
            }
            return deep;
        }

        public void dfs(TrieNode nextTireNodes,int deep) {
            if (!nextTireNodes.hasNext()) {
                deep = Math.max(deep,deep);
                return;
            }
            TrieNode[] trieNodes = nextTireNodes.branches;
            for (int j = 0; j < trieNodes.length; j++) {
                TrieNode x = trieNodes[j];
                dfs(x,deep+1);
            }

        }

    }

}
