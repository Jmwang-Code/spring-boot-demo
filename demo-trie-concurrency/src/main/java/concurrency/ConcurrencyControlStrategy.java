package concurrency;

/**
 * 并发控制策略
 *
 * 对于并发控制，我们可以使用不同的策略，控制并发访问和加锁粒度。
 */
public interface ConcurrencyControlStrategy {

    /**
     * 加锁
     */
    void nodeWriteLock();

    void nodeReadLock();

    /**
     * 解锁
     */
    void unWriteLock();

    void unReadLock();


    /**
     * 判断锁
     */
    boolean hasLocks();
}
