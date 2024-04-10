import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. 编译javac -encoding UTF-8 DeadlockExample.java
 * 2. 打包jar cvfe DeadlockExample.jar DeadlockExample DeadlockExample.class DeadlockExample$BankAccount.class
 * 3. 运行java -jar DeadlockExample.jar
 *
 * java -jar -Xms80m -Xmx80m -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError DeadlockExample.jar
 *
 * java -Xms80m -Xmx80m -XX:+PrintGC -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -jar DeadlockExample.jar
 */
public class DeadlockExample {
    /**
     * 1. 查看CPU 内存占用都没有异常
     * 2. 就把当前线程栈信息打印出来 jstack PID > thread_dump.txt
     * 3. 寻找对应业务名称的线程 和对应的WAITING和BLOCK线程即可
     */
    // 王佳和妈妈相互转账
    static class BankAccount {
        private final int id;
        private double balance;
        private final Lock lock = new ReentrantLock();

        public BankAccount(int id, double balance) {
            this.id = id;
            this.balance = balance;
        }

        public void deposit(double amount) {
            lock.lock();
            try {
                balance += amount;
            } finally {
                lock.unlock();
            }
        }

        public void withdraw(double amount) {
            lock.lock();
            try {
                balance -= amount;
            } finally {
                lock.unlock();
            }
        }

        public void transfer(BankAccount other, double amount) {
            // 锁定转出账户
            lock.lock();
            try {
                System.out.println("[" + Thread.currentThread().getName() + "] 锁定的帐户 " + id);

                // 模拟业务处理时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 锁定转入账户
                other.lock.lock();
                try {
                    System.out.println("[" + Thread.currentThread().getName() + "] 锁定的帐户 " + other.id);

                    if (balance >= amount) {
                        withdraw(amount);
                        other.deposit(amount);
                        System.out.println("[" + Thread.currentThread().getName() + "] 传输成功: " + amount);
                    } else {
                        System.out.println("[" + Thread.currentThread().getName() + "] 转移资金不足");
                    }
                } finally {
                    other.lock.unlock();
                    System.out.println("[" + Thread.currentThread().getName() + "] 解锁的帐户 " + other.id);
                }
            } finally {
                lock.unlock();
                System.out.println("[" + Thread.currentThread().getName() + "] 解锁的帐户 " + id);
            }
        }
    }

    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1, 1000);
        BankAccount account2 = new BankAccount(2, 2000);

        Thread thread1 = new Thread(() -> {
            account1.transfer(account2, 500);
        },"客户王佳");

        Thread thread2 = new Thread(() -> {
            account2.transfer(account1, 300);
        },"客户王佳妈妈");

        thread1.start();
        thread2.start();
    }
}
