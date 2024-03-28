package com.cn.jmw.华为;

import java.util.concurrent.atomic.AtomicInteger;

public class 按序打印1114 {

    private AtomicInteger firstJobDone = new AtomicInteger(0);
    private AtomicInteger secondJobDone = new AtomicInteger(0);

    public 按序打印1114() {}

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first".
        printFirst.run();
        // mark the first job as done, by increasing its count.
        firstJobDone.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (firstJobDone.get() != 1) {
            // waiting for the first job to be done.
        }
        // printSecond.run() outputs "second".
        printSecond.run();
        // mark the second as done, by increasing its count.
        secondJobDone.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (secondJobDone.get() != 1) {
            // waiting for the second job to be done.
        }
        // printThird.run() outputs "third".
        printThird.run();
    }

    public static void main(String[] args) throws InterruptedException {
        按序打印1114 按序打印1114 = new 按序打印1114();
        按序打印1114.first(new Runnable() {
            @Override
            public void run() {
                System.out.println("first");
            }
        });
        按序打印1114.second(new Runnable() {
            @Override
            public void run() {
                System.out.println("second");
            }
        });
        按序打印1114.third(new Runnable() {
            @Override
            public void run() {
                System.out.println("third");
            }
        });

    }
}
