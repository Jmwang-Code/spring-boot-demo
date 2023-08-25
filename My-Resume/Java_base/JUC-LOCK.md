# 1. 说说你常用的锁？ （你知道的锁都有哪些？ 你工作中使用过什么锁？）

1. `synchronized`锁：`synchronized`是Java中的关键字，是JVM中的底层机制，通过`monitor`对象实现的。
2. `ReentrantLock`锁：是互斥锁，优势在于可以手动获取和释放，通过AQS实现的。
3. `ReentrantReadWriteLock`锁：既有互斥锁又有共享锁，优势在于可以手动获取和释放，可以通过AQS实现的。

# 2. 说说synchronized原理？
`synchronized`是基于java对象头中的`Mark word`标记。当线程获取锁时，会修改`mark word`的标记性能比如：无锁、偏向锁、轻量级锁、重量级锁、GC标记。

java中几乎所有锁都是可重入锁。`synchronized`也是非公平锁。

## 2.1 说说synchronized在1.6版本中的优化？ （说说synchronized的几个锁操作特性）

1. 锁消除：如果不存在临界资源，即使写了`synchroized`关键字，JVM也会自动消除这些锁。
2. 锁膨胀：如果在一个循环中，多次获取锁，JVM将自动将锁膨胀。
3. 锁升级：
   1. JDK1.6之前：`synchronized`，完全获取不到锁会立即挂起，所以性能差。
   2. JDK1.6之后：`synchronized`，会经历锁升级的过程。

## 2.2 锁升级过程
1. 无锁：线程可以随意进入临界区。
2. 偏向锁：偏向锁是对单线程访问同步代码块的优化处理。
3. 轻量级锁：会采用CAS自旋的方式获取锁，如果自旋失败，会膨胀为重量级锁。
4. 重量级锁：会阻塞线程，直到锁被释放。（内核态和用户态的切换）

# 3. 说说ReentrantLock原理？ 

`ReentrantLock` 是基于 AQS（AbstractQueuedSynchronizer）实现的，AQS 是一个用于构建锁和同步器的框架。

## 3.1 AQS是什么？ （请你说说AQS？）

AQS 是一种同步机制。
首先AQS提供了一个由volatile修饰的int类型的state变量，用来存储同步状态。
其次AQS维护了一个双向链表，用来存储等待获取锁的线程。

# 4. synchronized 和 ReentrantLock的区别

- 灵活：`synchronized`不需要手动控制、时非公平锁，`ReentrantLock`需要手动控制、可以设置公平和非公平锁。
- 性能：在低并发的情况下，`synchronized` 的性能比 `ReentrantLock` 好，因为 synchronized 是 JVM 内置的关键字，不需要额外的开销。但是在高并发的情况下，`ReentrantLock` 的性能比 `synchronized` 好，因为 `ReentrantLock` 可以实现公平锁和非公平锁，可以避免线程饥饿问题，从而提高系统的吞吐量。

# 5. synchronized 和 volatile 的区别

- `synchronized` 用于保证同一时间只有一个线程可以访问被 `synchronized` 修饰的代码
- `volatile` 用于保证多个线程之间对变量的可见性。

# 6. Java锁基本概念

## 6.1 自旋锁

自旋锁: 当某个线程尝试获取锁，如果锁已经被占用了，就一直循环等待，直到可以锁被释放。
线程不会被挂起，而是处于等待状态。

## 6.2 可重入锁

可重入锁是指同一个线程可以多次获得同一把锁，而不会被阻塞。

## 6.3 乐观锁和悲观锁

- 乐观锁：认为数据一般不会造成冲突，所以不会上锁。
- 悲观锁：认为数据一般会造成冲突，所以会上锁。

## 6.4 公平锁和非公平锁

- 公平锁：按照时间顺序进行获取锁。
- 非公平锁：不按照时间顺序进行获取锁。

## 6.5 死锁

- 死锁：两个或者两个以上的线程在执行过程中，形成了一种相互等待的现象。

避免死锁的方法：
1. 设置超时时间
2. 死锁检测`tryLock`

