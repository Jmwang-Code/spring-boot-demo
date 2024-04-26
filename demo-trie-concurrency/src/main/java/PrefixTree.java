import concurrency.ConcurrencyControlStrategy;
import concurrency.NodeLockStrategy;
import concurrency.SegmentLockStrategy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 前缀树
 * @param <K>
 * @param <V>
 */
public class PrefixTree<K, V> {
    private Node<K, V> root;

    /**
     * 当全局锁被持有时，其他尝试获取全局锁的线程（例如，尝试执行 get 或 put 操作的线程）将会被阻塞，直到全局锁被释放。这确保了在切换并发控制策略的过程中，树的状态不会被其他线程改变。
     *
     * 然而，这也意味着在切换并发控制策略的过程中，树不能进行正常的资源读取或写入。因为全局锁被持有，所以任何尝试获取全局锁的线程（例如，尝试执行 get 或 put 操作的线程）都会被阻塞。这可能会导致在切换并发控制策略的过程中，树的读写性能下降。
     */
    private final ReentrantLock globalLock = new ReentrantLock();

    /**
     *  waitingQueue 字段来存储正在等待切换并发控制策略的节点。
     *
     *  在 switchConcurrencyControlStrategy 方法中，我们首先将所有的节点添加到队列中，然后我们逐个从队列中取出节点，并切换其并发控制策略。这样，我们就可以确保在切换并发控制策略的过程中，所有的操作都是线程安全的。
     */
    private Queue<Node<K, V>> waitingQueue = new LinkedList<>();

    public PrefixTree(ConcurrencyControlStrategy concurrencyControlStrategy) {
        this.root = new Node<>(concurrencyControlStrategy);
    }

    public V get(K key) {
        if (root != null) {
            root.nodeReadLock();
            try {
                // 执行获取操作
                Node<K, V> node = findNode(key);
            } finally {
                root.unReadLock();
            }
        }
        return null;
    }

    public void put(K key, V value) {
        Node<K, V> node = findNode(key);
        if (node != null) {
            node.nodeWriteLock();
            try {
                // 执行插入操作
            } finally {
                node.unWriteLock();
            }
        } else {
            // 如果节点不存在，创建新节点并插入
            node = new Node<>(new SegmentLockStrategy());
            node.nodeWriteLock();
            try {
                // 执行插入操作
            } finally {
                node.unWriteLock();
            }
        }
    }

    //按照前缀树去找节点
    private Node<K, V> findNode(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    private int compare(K key1, K key2) {
        // 这里假设 K 类型实现了 Comparable 接口
        return ((Comparable<K>) key1).compareTo(key2);
    }

    //非串行
//    public void switchConcurrencyControlStrategy(ConcurrencyControlStrategy newStrategy) {
//        switchConcurrencyControlStrategy(root, newStrategy);
//    }

    /**
     * 切换并发控制策略
     * @param newStrategy
     */
    public void switchConcurrencyControlStrategy(ConcurrencyControlStrategy newStrategy) {
        globalLock.lock();
        try {
            enqueueNodes(root);
            while (!waitingQueue.isEmpty()) {
                Node<K, V> node = waitingQueue.poll();
                node.writeLock();
                try {
                    node.setConcurrencyControlStrategy(newStrategy);
                } finally {
                    node.unWriteLock();
                }
            }
        } finally {
            globalLock.unlock();
        }
    }

    /**
     * 将所有节点添加到队列中
     */
    private void enqueueNodes(Node<K, V> node) {
        if (node == null) {
            return;
        }

        waitingQueue.add(node);
        enqueueNodes(node.left);
        enqueueNodes(node.right);
    }

    /**
     * 切换并发控制策略
     * @param node
     * @param newStrategy
     */
    private void switchConcurrencyControlStrategy(Node<K, V> node, ConcurrencyControlStrategy newStrategy) {
        if (node == null) {
            return;
        }

        node.writeLock();
        try {
            node.setConcurrencyControlStrategy(newStrategy);
        } finally {
            node.unWriteLock();
        }

        switchConcurrencyControlStrategy(node.left, newStrategy);
        switchConcurrencyControlStrategy(node.right, newStrategy);
    }


    private static class Node<K, V> extends NodeLockStrategy {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        private ConcurrencyControlStrategy concurrencyControlStrategy;

        public Node(ConcurrencyControlStrategy concurrencyControlStrategy) {
            this.concurrencyControlStrategy = concurrencyControlStrategy;
        }

        public void setConcurrencyControlStrategy(ConcurrencyControlStrategy concurrencyControlStrategy) {
            this.concurrencyControlStrategy = concurrencyControlStrategy;
        }
    }

    static final class TreeBin {
        private static final int LOCKSTATE = 0;
        private static final int WRITER = 1;
        private static final int WAITER = 2;

        private volatile int lockState = 0;
        private Thread waiter = null;

        // TrieNode的其他字段和方法...

        private final void contendedLock() {
            boolean waiting = false;
            for (int s;;) {
                if (((s = lockState) & ~WAITER) == 0) {
                    if (U.compareAndSwapInt(this, LOCKSTATE, s, WRITER)) {
                        if (waiting)
                            waiter = null;
                        return;
                    }
                }
                else if ((s & WAITER) == 0) {
                    if (U.compareAndSwapInt(this, LOCKSTATE, s, s | WAITER)) {
                        waiting = true;
                        waiter = Thread.currentThread();
                    }
                }
                else if (waiting)
                    LockSupport.park(this);
            }
        }

        // 在修改节点前调用contendedLock()获取写锁，在修改完成后调用unlock()释放锁
        private final void unlock() {
            lockState = 0;
        }
    }

    /**
     * 通过反射获取Unsafe实例
     * 提供了一组底层的原子操作，包括对内存的直接访问和CAS（Compare and Swap）操作。
     */
    private static final sun.misc.Unsafe U;
    //控制表的大小，用于并发控制。
    private static final long SIZECTL;
    //控制调整大小的操作，用于并发控制（扩容）
    private static final long TRANSFERINDEX;
    //保存ConcurrentHashMap的元素数量。
    private static final long BASECOUNT;
    //控制对counterCells数组的访问，用于并发控制。
    private static final long CELLSBUSY;
//    private static final long CELLVALUE;
//    private static final long ABASE;
//    private static final int ASHIFT;

    static {
        try {
            U = sun.misc.Unsafe.getUnsafe();
            Class<?> k = ConcurrentHashMap.class;
            SIZECTL = U.objectFieldOffset
                    (k.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = U.objectFieldOffset
                    (k.getDeclaredField("transferIndex"));
            BASECOUNT = U.objectFieldOffset
                    (k.getDeclaredField("baseCount"));
            CELLSBUSY = U.objectFieldOffset
                    (k.getDeclaredField("cellsBusy"));
//            Class<?> ck = ConcurrentHashMap.CounterCell.class;
//            CELLVALUE = U.objectFieldOffset
//                    (ck.getDeclaredField("value"));
//            Class<?> ak = ConcurrentHashMap.Node[].class;
//            ABASE = U.arrayBaseOffset(ak);
//            int scale = U.arrayIndexScale(ak);
//            if ((scale & (scale - 1)) != 0)
//                throw new Error("data type scale not a power of two");
//            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}