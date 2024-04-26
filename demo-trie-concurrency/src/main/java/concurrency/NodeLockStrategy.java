package concurrency;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NodeLockStrategy extends ReentrantReadWriteLock implements ConcurrencyControlStrategy, Serializable {
    private static final long serialVersionUID = 2249069246763182397L;

    public void nodeWriteLock() {
        super.writeLock().lock();
    }

    public void nodeReadLock() {
        super.readLock().lock();
    }

    @Override
    public void unWriteLock() {
        super.writeLock().unlock();
    }

    @Override
    public void unReadLock() {
        super.readLock().unlock();
    }

    @Override
    public boolean hasLocks() {
        return this.isWriteLocked();
    }
}