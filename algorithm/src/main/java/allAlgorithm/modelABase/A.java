package allAlgorithm.modelABase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//三个线程 线程A输出12345 线程B输出abcde 线程C输出ABCDE，最后交替输出 1aA2bB3cC4dD5eE
public class A {

    //三个线程 通过同步器实现交替输出
    private static Object lock = new Object();
    private static int count = 1;
    private static char ch = 'a';

    static ReentrantLock  reentrantLock = new ReentrantLock();
    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //reentrantReadWriteLock唤醒指定的线程
    static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    static CountDownLatch countDownLatch = new CountDownLatch(3);


}

