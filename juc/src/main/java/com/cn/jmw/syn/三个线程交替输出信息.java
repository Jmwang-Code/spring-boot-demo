package com.cn.jmw.syn;

import java.util.concurrent.locks.LockSupport;

public class 三个线程交替输出信息 {

    static Thread thread2 = null;
    static Thread thread = null;

    static Thread thread3 = null;
    public static void lockSupport() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 6; i++) {
                    System.out.print(i);
                    LockSupport.unpark(thread2); //叫醒2
                    LockSupport.park(); //阻塞1
                }
            }
        }, "hello");

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    LockSupport.park(); //阻塞2
                    System.out.print((char)( i + 'a'));
                    LockSupport.unpark(thread3); //叫醒1
                }
            }
        }, "world");
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    LockSupport.park(); //阻塞2
                    System.out.print((char)( i + 'A'));
                    LockSupport.unpark(thread); //叫醒1
                }
            }
        }, "world");
        thread.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        lockSupport();

    }
}
