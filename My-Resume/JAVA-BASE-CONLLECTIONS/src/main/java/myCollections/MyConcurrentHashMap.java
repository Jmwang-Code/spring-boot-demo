package myCollections;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrentHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private final int segments;

    //分段锁
    private final ReentrantLock[] locks;

    //每个数组上的Node节点都可以分出一段加锁
    private final AtomicReferenceArray<Node<K, V>>[] table;

    @SuppressWarnings("unchecked")
    public MyConcurrentHashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyConcurrentHashMap(int capacity) {
        this.segments = capacity;
        this.locks = new ReentrantLock[segments];
        this.table = new AtomicReferenceArray[segments];
        for (int i = 0; i < segments; i++) {
            locks[i] = new ReentrantLock();
            table[i] = new AtomicReferenceArray<>(DEFAULT_CAPACITY);
        }
    }

    public void put(K key, V value) {
        int segmentIndex = hash(key) % segments;
        Node<K, V> newNode = new Node<>(key, value);
        //这里就是CAS
        while (true) {
            if (tryInsert(segmentIndex, newNode)) {
                return;
            }
            segmentIndex = (segmentIndex + 1) % segments; // linear probing
        }
    }

    public V get(K key) {
        int segmentIndex = hash(key) % segments;
        for (int i = 0; i < table[segmentIndex].length(); i++) {
            Node<K, V> node = table[segmentIndex].get(i);
            if (node != null && node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    private boolean tryInsert(int segmentIndex, Node<K, V> newNode) {
        //采用分段锁
        ReentrantLock lock = locks[segmentIndex];
        lock.lock();
        try {
            AtomicReferenceArray<Node<K, V>> segment = table[segmentIndex];
            int index = newNode.hash & (segment.length() - 1);
            Node<K, V> node = segment.get(index);
            if (node == null) {
                segment.set(index, newNode);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private int hash(K key) {
        return key.hashCode();
    }

    private static class Node<K, V> {
        private final K key;
        private final V value;
        private final int hash;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }
    }
}
