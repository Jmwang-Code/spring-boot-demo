package com.cn.jmw.AlibabaInterview;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
//一个热点LRU 的缓存队列
public class HotCacheLruDeque {

    Deque<Object> deque;

    Map<Object,Object> map;

    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final static Lock read = lock.readLock();

    private final static Lock write = lock.writeLock();

    private static AtomicInteger counter = new AtomicInteger();

    private static int capacity;

    public HotCacheLruDeque(int capacity){
        deque = new LinkedList();
        map = new HashMap();
        capacity = capacity;
    }


    //输出缓存
    public void popCache(Object oKey){
        if (map.getOrDefault(oKey, null)!=null){
            try {
                write.lock();
                //
                Object o = map.get(oKey);
                deque.remove(o);
                map.remove(oKey);
            }finally {
                write.unlock();
            }
        }
    }

    //输入缓存
    //输入进来的缓存，我们默认把他移动到首位
    public void putCache(Object oKey,Object oValue){
        if (counter.get()<capacity){
            try {
                write.lock();
                //map没有元素 可以加入数据
                if (map.getOrDefault(oKey, null)==null){
                    map.put(oKey,oValue);
                    deque.addLast(oValue);
                    counter.incrementAndGet();
                }
            }finally {
                write.unlock();
            }
        }
        //当容量到达上限
        else {
            try {
                write.lock();
                if (map.getOrDefault(oKey, null)==null){
                    map.remove(oKey);
                    deque.remove(oValue);

                    map.put(oKey,oValue);
                    deque.addLast(oValue);
                }
            }finally {
                write.unlock();
            }
        }
    }

    //是否拥有对应缓存
    public Object getCache(Object oKey){
        Object oValue = null;
        if (map.getOrDefault(oKey, null)!=null){
            try {
                read.lock();
                //map没有元素 可以加入数据
                oValue = map.get(oKey);
                deque.addFirst(oValue);
            }finally {
                read.unlock();
            }
        }
        return oValue;
    }
}


