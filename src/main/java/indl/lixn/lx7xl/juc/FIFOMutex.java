package indl.lixn.lx7xl.juc;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author listen
 * 简单的FIFO独占式同步器/锁
 **/
public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);

    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // Block while not first in queue or cannot acquire lock
        while (waiters.peek() != current
                || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            // Ignore interrupts while waiting
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }
        waiters.remove();
        if (wasInterrupted) {
            // Reassert interrupt status on exit
            current.interrupt();
        }
    }

    public void unlock() {
        locked.set(false);
        // 唤醒第一个等待的节点
        LockSupport.unpark(waiters.peek());
    }

}
