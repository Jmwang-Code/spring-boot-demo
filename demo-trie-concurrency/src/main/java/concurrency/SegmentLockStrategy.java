package concurrency;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 分段锁策略
 */
public class SegmentLockStrategy implements ConcurrencyControlStrategy {
    // 假设有16个分段锁
    private final ReentrantReadWriteLock[] locks = new ReentrantReadWriteLock[16];

    public SegmentLockStrategy() {
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new ReentrantReadWriteLock();
        }
    }

    @Override
    public void nodeWriteLock() {
        locks[ThreadLocalRandom.current().nextInt(locks.length)].writeLock().lock();
    }

    @Override
    public void nodeReadLock() {
        locks[ThreadLocalRandom.current().nextInt(locks.length)].readLock().lock();
    }

    @Override
    public void unWriteLock() {
        locks[ThreadLocalRandom.current().nextInt(locks.length)].writeLock().unlock();
    }

    @Override
    public void unReadLock() {
        locks[ThreadLocalRandom.current().nextInt(locks.length)].readLock().unlock();
    }

    @Override
    public boolean hasLocks() {
        return false;
    }

}