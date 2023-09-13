package com.cn.jmw.syn;

import java.util.concurrent.locks.LockSupport;

public class 两个线程输出信息 {

    //notify wait
    public static void notifyWait() {
        Object lock = new Object();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < 5; i++) {
                        System.out.print(i);
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, "hello");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 5; i <= 9; i++) {
                        System.out.print(i);
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, "world");
        thread.start();
        thread2.start();
    }

    static Thread thread2 = null;
    static Thread thread = null;
    public static void lockSupport() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.print(i);
                    LockSupport.unpark(thread2); //叫醒2
                    LockSupport.park(); //阻塞1
                }
            }
        }, "hello");

         thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 5; i <= 9; i++) {
                    LockSupport.park(); //阻塞2
                    System.out.print(i);
                    LockSupport.unpark(thread); //叫醒1
                }
            }
        }, "world");
        thread.start();
        thread2.start();
    }

    public static void main(String[] args) {
//        notifyWait();
        lockSupport();

    }
}
