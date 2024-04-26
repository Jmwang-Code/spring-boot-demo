package concurrency;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁策略
 */
public class ReadWriteLockStrategy implements ConcurrencyControlStrategy {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void nodeWriteLock() {
        lock.writeLock().lock();
    }

    @Override
    public void nodeReadLock() {
        lock.readLock().lock();
    }

    @Override
    public void unWriteLock() {
        lock.writeLock().unlock();
    }

    @Override
    public void unReadLock() {
        lock.readLock().unlock();
    }

    @Override
    public boolean hasLocks() {
        return false;
    }
}