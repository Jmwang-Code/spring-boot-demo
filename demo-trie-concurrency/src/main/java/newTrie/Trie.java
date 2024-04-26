package newTrie;

import newTrie.inner.ForEachTrieTask;
import newTrie.inner.SearchTask;
import newTrie.inner.TrieNode;
import newTrie.inner.TrieIterator;
import newTrie.inter.ForEachForkJoinInterface;
import newTrie.inter.SearchForkJoinInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <code><b>一、基本介绍</b></code>
 * <p>一棵前缀树（字典树），支持检索的完全并发性和更新的高预期并发性。在一个文本中的快锁检索出对应的词语。然而整个过程是线程安全的。
 * 它可以支持所有操作线程安全并且不需要加锁，并且没有任何锁定整个表的支持以组织所有访问的方式。这个类的线程安全性是内建的,
 * 使用这个类不需要了解这个类的同步细节，而不需要做额外的同步控制。无法像{@link java.util.concurrent.ConcurrentHashMap}那样遍历所有元素。
 *
 * <br>
 * <p><code><b>二、基本方法</b></code>
 * <p>这个类的实现是基于一个前缀树，这个树是一个有向无环图，每个节点有一个或多个子节点。每个节点代表一个字符，从根节点到这个节点的路径代表一个字符串。
 *<ul>
 *     <li>检索操作（包含{@code get}），通常不会阻塞，因此可能与更新操作（包括{@code put}和{@code remove}）重叠。检索操作反映的是在它们开始时最近完成的更新操作的结果。
 *  * 更正式地说，对给定键的更新操作在任何报告更新值的（非空）检索操作之前发生。</li>
 *     <li>对于像clear这样的聚合操作,并发检索可能只反映一些条目的删除。</li>
 *     <li>对于{@code size}、{@code isEmpty}和{@code containsValue}在内的聚合状态方法的结果只是瞬间状态，并不是实时。
 *  也就是说如果在使用聚合方法的时候去进行并发{@code put}和{@code remove}操作可能无法获取到当前的状态，而是并发操作发生之前的状态。</li>
 *</ul>
 *
 * <br>
 * <p><code><b>三、节点扩容、构造函数和迭代器</b></code>
 * <p>这里的节点扩容是指的是给某个节点上的下一层数组进行扩容，这个扩容是线程安全的，不会出现并发问题。不会预留空间。每次扩容或者缩容。
 * 在有参构造器中进行深拷贝。自定义迭代器，实现了{@link Iterable}接口。默认的迭代器是层序顺序遍历。BFS广度有限搜索。不保证顺序。
 *
 * <br>
 * <p><code><b>四、ForkJoin分支合并函数</b></code>
 * <p>这个{@link ForEachTrieTask}实现了{@link ForEachForkJoinInterface}接口，支持ForkJoin的函数。可以并行遍历前缀树的任务。
 * 在这个系列中，我们将会实现更多的ForkJoin的函数。可以设置并行阈值，当size大于这个阈值的时候，就会进行并行遍历。
 * 并行阈值（parallelismThreshold）：批量操作接受一个名为parallelismThreshold的参数。如果当前映射的大小预计小于给定的阈值，方法将顺序执行。
 * 通过划分足够的子任务来充分利用ForkJoinPool.commonPool()，这个池用于所有并行计算。通常，你会首先选择这些极端值中的一个，然后测量使用介于两者之间的值（在开销和吞吐量之间进行权衡）的性能。
 *
 *
 */
public class Trie implements Serializable, Iterable<TrieNode>,
        ForEachForkJoinInterface, SearchForkJoinInterface {

    //序列号
    private static final long serialVersionUID = 7249069246763182397L;

    //根节点
    private final TrieNode root;

    //实体数量,是瞬间值，可能是过去某一时刻的值
    private transient int size = 0;

    /**
     * 无参构造函数 (初始化)
     */
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * 有参构造函数
     * 深拷贝
     */
    public Trie(TrieNode otherRoot) {
        this.root = new TrieNode(otherRoot);
    }

    @Override
    public Iterator<TrieNode> iterator() {
        return new TrieIterator(root);
    }

    /**
     * size
     */
    public int size() {
        return size;
    }

    /**
     * getRoot
     */
    public TrieNode getRoot() {
        return root;
    }

    /**
     * 并行遍历前缀树的任务。
     * @param parallelismThreshold 并行阈值
     * @param transformer 转换器
     * @param action 操作
     * @param <U>
     */
    @Override
    public <U> void forEachParallel(int parallelismThreshold, Function<TrieNode, U> transformer, Consumer<U> action) {
        if (size() > parallelismThreshold) {
            ForkJoinPool.commonPool().invoke(new ForEachTrieTask<>(getRoot(), transformer, action,new Semaphore(parallelismThreshold)));
        } else {
            forEach(node -> action.accept(transformer.apply(node)));
        }
    }

    @Override
    public TrieNode searchParallel(int parallelismThreshold) {
        ForkJoinPool pool = new ForkJoinPool(parallelismThreshold);
        return pool.invoke(new SearchTask(getRoot(), new ArrayList<>()));
    }
}
