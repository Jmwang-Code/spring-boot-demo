package allAlgorithm.BmodelLinkedList;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    //用到的数据结构
    class LinkedNode {
        int key;
        int value;
        LinkedNode pre;
        LinkedNode next;
        public LinkedNode(){};
        public LinkedNode(int newKey,int newValue) {
            key = newKey;
            value = newValue;
        }
    }

    private Map<Integer,LinkedNode> cache = new HashMap<Integer,LinkedNode>();

    private int size;
    private int capacity;
    private LinkedNode head;
    private LinkedNode tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        head = new LinkedNode();
        tail = new LinkedNode();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        LinkedNode node = cache.get(key);
        if(node == null) {
            return -1;
        }

        moveToHead(node);
        return node.value;


    }

    public void put(int key, int value) {
        LinkedNode node = cache.get(key);
        if(node == null) {
            LinkedNode newNode = new LinkedNode(key,value);
            cache.put(key,newNode);
            addToHead(newNode);
            size++;
            if(size > capacity) {
                LinkedNode tail = removeTail();
                cache.remove(tail.key);
                size--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }



    private void addToHead(LinkedNode node) {
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }

    private LinkedNode removeTail() {
        LinkedNode res = tail.pre;
        removeNode(res);
        return res;
    }

    private void removeNode(LinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void moveToHead(LinkedNode node) {
        removeNode(node);
        addToHead(node);
    }
}
