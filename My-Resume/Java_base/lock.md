# 1. 锁

常用和锁、线程安全相关的工具：synchronized、ReentrantLock、AtomicInteger、AtomicLong、CopyOnWriteArrayList、CopyOnWriteArraySet、ConcurrentHashMap、ConcurrentSkipListMap、ConcurrentSkipListSet

# 2. ReentrantLock
![Alt text](../images/imagehello.png)

## 2.1 ReentrantLock 的实现原理
ReentrantLock 是基于 AQS（AbstractQueuedSynchronizer）实现的，AQS 是一个用于构建锁和同步器的框架，使用 AQS 能简单且高效地构造出应用广泛的大量的同步器，比如常用的 ReentrantLock、Semaphore、CountDownLatch、ReentrantReadWriteLock、ThreadPoolExecutor 等。

## 2.2 AQS 是什么？
AQS 内部维护了一个 volatile int state 和一个 FIFO 队列，state 用于表示同步状态，FIFO 队列用于存放获取同步状态失败的线程。


## 2.3 CAS 是什么？
CAS（Compare And Swap）是一种无锁算法，当多个线程尝试使用 CAS 同时更新同一个变量时，只有其中一个线程能更新变量的值，而其它线程都失败，失败的线程并不会被挂起，而是被告知这次竞争中失败，并可以再次尝试。

# 3. synchronized

# 3.1 synchronized 的实现原理
Java 中的对象监视器（monitor）实现的。每个对象都有一个 monitor，monitor 可以和任意个线程关联，线程可以通过调用对象的 wait() 和 notify() 方法来与 monitor 进行交互。

线程执行 wait() ：会让自己进入 monitor 的等待队列，并且释放 monitor 上的所有锁。

线程执行 notify() ：会从等待队列中随机选择一个线程，并将其唤醒，被唤醒的线程则进入 monitor 的锁定队列，等待获取 monitor 上的锁。当线程执行完毕退出 synchronized 代码块时，会释放 monitor 上的所有锁，此时被唤醒的线程可以获取 monitor 上的锁，继续执行。

## 3.2 synchronized 的锁升级过程
![Alt text](../images/image-111.png)
synchronized 的锁升级过程分为四个阶段：无锁、偏向锁、轻量级锁和重量级锁。


简述升级过程：
无锁
↓
偏向锁：当一个线程访问同步块并获取锁时，如果该同步块没有被锁定，那么该线程会尝试获取该同步块的偏向锁，并将对象头中的 Mark Word 设置为偏向锁。如果该同步块已经被其他线程锁定，那么该线程会尝试获取该同步块的轻量级锁。
↓
轻量级锁：当一个线程尝试获取一个同步块的锁时，如果该同步块没有被锁定，那么该线程会尝试获取该同步块的轻量级锁，并将对象头中的 Mark Word 设置为指向线程栈帧的指针。如果该同步块已经被其他线程锁定，那么该线程会尝试获取该同步块的重量级锁。
↓
重量级锁：当一个线程尝试获取一个同步块的锁时，如果该同步块已经被其他线程锁定，那么该线程会进入阻塞状态，直到该同步块的锁被释放。在重量级锁的实现中，JVM 会将对象的 Mark Word 指向一个互斥量，从而实现线程的阻塞和唤醒。

### 3.2.1 无锁
无锁状态下，线程可以随意进入临界区，不需要进行任何同步操作。

### 3.2.2 偏向锁
偏向锁（只使用于没有竞争的场景下），如果一个现场获取了锁，在未来的一段时间内，该线程可能在此获取这个锁，JVM会偏向于这个线程。

### 3.2.3 轻量级锁
轻量级锁（只使用于没有竞争的场景下），如果一个现场获取了锁，在未来的一段时间内，该线程可能在此获取这个锁，JVM会偏向于这个线程。

### 3.2.4 重量级锁
重量级锁（只使用于有竞争的场景下），如果一个现场获取了锁，在未来的一段时间内，该线程可能在此获取这个锁，JVM会偏向于这个线程。

### 3.2.5 自旋锁
自旋锁是指当一个线程尝试获取某个锁时，如果该锁已被其他线程占用，就一直循环等待，直到该锁被释放为止，线程不会被挂起，而是处于忙等状态，因此自旋锁一般适用于锁保护的临界区比较小的情况。

## 3.2 synchronized 和 volatile 的区别
synchronized 用于保证同一时间只有一个线程可以访问被 synchronized 修饰的代码，而 volatile 用于保证多个线程之间对变量的可见性。


# 4. synchronized和ReentrantLock的区别
1. synchronized是Java关键字，ReentrantLock是类
2. synchronized相比,ReentrantLock提供了一些高级功能，主要有以下三项：
    1. 等待可中断，持有锁的线程长期不释放的时候，正在等待的线程可以选择放弃等待，这相当于synchronized来说可以避免出现死锁的情况
    2. 公平锁，多个线程等待同一个锁时，必须按照申请锁的时间顺序获得锁，synchronized锁非公平锁，ReentrantLock默认的构造函数是创建的非公平锁，可以通过传入true创建公平锁
    3. 锁绑定多个条件，一个ReentrantLock对象可以同时绑定多个Condition对象
3. synchronized会自动释放锁，ReentrantLock需要手动释放锁，如果不释放锁，就有可能导致死锁

## 4.1 什么是可重入锁？
可重入锁是指同一个线程可以多次获得同一把锁，而不会被阻塞。ReentrantLock和synchronized都是可重入锁。

## 4.2 什么是乐观锁和悲观锁？（简要回答）
概念，具体实现方式有很多种。
乐观锁认为数据一般不会造成冲突，所以不会上锁，而悲观锁认为数据一般会造成冲突，所以会上锁。

## 4.3 什么是自旋锁？（简要回答）
自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。

## 4.4 什么是公平锁和非公平锁？（简要回答）
公平锁是指多个线程按照申请锁的顺序来获取锁，非公平锁是非顺序的，非公平锁的优点在于吞吐量比公平锁大。

## 4.5 什么是死锁？如何避免（简要回答）
死锁是指两个或者两个以上的线程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，这些线程都将无法推进下去。
避免死锁的方法：
1. 设置超时时间
2. 死锁检测tryLock
等
