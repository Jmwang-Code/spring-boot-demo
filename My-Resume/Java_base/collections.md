# 1. Java 集合框架有那些接口？ （特点有那些+）
- `List`顺序
- `Set`去重
- `Map`Key-value键值表
- `Queue`队列
- `Deque`双端队列

# 2. 常见对比
## 2.1 ArrayList 和 LinkedList 的区别是什么？
- `ArrayList`：数据结构为数组，支持随机访问，实现了`RandomAccess`接口，线程不安全，效率高
- `LinkedList`：数据结构为链表，不支持随机访问，没有实现`RandomAccess`接口，线程不安全，效率高

### 2.1.1 使用ArrayList而不使用LinkedList的原因
- 插入、删除：ArrayList尾插入效率高，LinkedList头尾插入效率高。但是中加插入效率都很低，因为ArrayList需要移动元素，LinkedList需要移动指针。
- 修改、查询：ArrayList随机访问效率高，LinkedList随机访问效率低。


## 2.2 HashMap 和 Hashtable 的区别是什么？
- `HashMap`：内部是通过数组+链表+红黑树实现的，不允许重复元素，可以有一个`null`元素，线程不安全，效率高
- `Hashtable`：内部是通过数组+链表实现的，不允许重复元素，不可以有`null`元素，线程安全，但是由于synchronized关键字加到了每个方法上，效率低

## 2.4 HashMap 和 ConcurrentHashMap 的区别是什么？
- `HashMap`：内部是通过数组+链表+红黑树实现的，不允许重复元素，可以有一个`null`元素，线程不安全，效率高
- `ConcurrentHashMap`：内部是通过数组+链表+红黑树实现的，不允许重复元素，不可以有`null`元素，线程安全，效率高

## 2.5 为什么ConcurrentHashMap效率会高?
- 分段锁：ConcurrentHashMap 内部采用了分段锁的机制，将整个哈希表分成了多个段（Segment），每个段都有自己的锁。这样，在多线程环境下，不同的线程可以同时访问不同的段，从而减少了锁的竞争，提高了并发性能。
- CAS 操作：ConcurrentHashMap 内部采用了 CAS（Compare and Swap）操作来实现线程安全，而不是像 Hashtable 和 synchronizedMap 一样使用重量级锁。CAS 操作是一种基于硬件支持的原子操作，可以在不使用锁的情况下实现线程安全，从而提高了并发性能。

## 2.6 HashMap 和 TreeMap 的区别是什么？
- `HashMap`：内部是通过数组+链表+红黑树实现的，不允许重复元素，可以有一个`null`元素，线程不安全，效率高
- `TreeMap`：内部是通过红黑树实现的，不允许重复元素，不可以有`null`元素，线程不安全，效率高。可以对元素进行排序。通过SortedMap接口实现。


# 3. HashMap
- JDK1.8之前：链表则是主要解决哈希冲突的（拉链法）。
- JDK1.8之后：当链表长度大于8时，链表就转换为红黑树（提高查询效率）。数组长度大于64时，链表转换为红黑树，小于6时，红黑树转换为链表（提高插入效率）。（开放地址法）

## 3.1 HashMap的初始容量和负载因子
- 初始容量：HashMap初始容量，即哈希表在创建时的容量，默认为16。
- 负载因子：负载因子是衡量HashMap满的程度的值，默认为0.75f，即当HashMap中元素个数大于等于容量*负载因子时，进行扩容。

## 3.2 HashMap如何扩容的？
当HashMap中元素个数大于等于容量*负载因子时，进行扩容，扩容为原来的2倍。

## 3.3 为什么扩容是2倍？HashMap的长度为什么是2的幂次方？
- 为了减少哈希冲突，提高查询效率。
- 数组大小是 2 的幂次方，那么取模运算可以转化为位运算，效率更高。

## 3.4 HashMap如何解决哈希冲突的？
链表法（拉链法）：将链表存储在数组中，当发生哈希冲突时，将冲突的元素存储在链表中。
开放地址法：当发生哈希冲突时，使用某种算法寻找下一个空的散列地址。

## 3.5 如何确定元素在数组中的位置？
通过key的hashCode()方法计算出哈希值 和 数组长度取模运算，得到在数组中的位置。
```java
public class AbstractHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
    @Override
    public V put(final K key, final V value) {
        final Object convertedKey = convertKey(key);
        final int hashCode = hash(convertedKey);
        final int index = hashIndex(hashCode, data.length);
        HashEntry<K, V> entry = data[index];
        //...
        }

        addMapping(index, hashCode, key, value);
        return null;
    }

    protected int hashIndex(final int hashCode, final int dataSize) {
        return hashCode & dataSize - 1;
    }
}
```

## 3.6 HashMap HashSet如果检查重复？如何判断相等？
通过元素的hashCode来判断是否相等，相等则通过equals来判断是否相等。
```java
public class AbstractHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
    @Override
    public V put(final K key, final V value) {
        //...
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(convertedKey, entry.key)) {
                final V oldValue = entry.getValue();
                updateEntry(entry, value);
                return oldValue;
            }
            entry = entry.next;
        }
        //...
    }

    protected boolean isEqualKey(final Object key1, final Object key2) {
        return key1 == key2 || key1.equals(key2);
    }
}
```

## 3.7 HashMap的hash算法 扰动函数，JDK1.8为什么比JDK1.7的hash算法更优？
JDK 1.8 的 hash 方法 相比于 JDK 1.7 hash 方法更加简化，但是原理不变。

```java
    static final int hash(Object key) {
      int h;
      // key.hashCode()：返回散列值也就是hashcode
      // ^：按位异或
      // >>>:无符号右移，忽略符号位，空位都以0补齐
      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }
```

对比一下 JDK1.7 的 HashMap 的 hash 方法源码.

```java
static int hash(int h) {
    // This function ensures that hashCodes that differ only by
    // constant multiples at each bit position have a bounded
    // number of collisions (approximately 8 at default load factor).

    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}
```

相比于 JDK1.8 的 hash 方法 ，JDK 1.7 的 hash 方法的性能会稍差一点点，因为毕竟扰动了 4 次。

## 3.8 hashMap 多线程死循环的问题
- JDK1.7：当多个线程同时进行扩容操作时，会导致链表成环，形成死循环。头插法会导致链表成环。
- JDK1.8：当多个线程同时进行扩容操作时，会导致链表成环，形成死循环。尾插法不会导致链表成环。

# 4. ArrarList
# 4.1 ArrayList扩容机制
- ArrayList的初始容量为10，当ArrayList中元素个数大于等于容量时，进行扩容，扩容为原来的1.5倍。