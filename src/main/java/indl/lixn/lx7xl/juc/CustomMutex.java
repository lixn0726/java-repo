package indl.lixn.lx7xl.juc;

import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * @author listen
 **/
public class CustomMutex implements Lock, Serializable {

    public static void main(String[] args) {

    }

    private static void myAcquire() {
        if (!false && randomBoolean()) {
            System.out.println("Should interrupt current thread");
        }
    }

    private static boolean shouldBeTrue() {
        System.out.println("Code ran into method: shouldBeTrue()");
        return true;
    }

    private static boolean randomBoolean() {
        System.out.println("Code ran into method: randomBoolean()");
//        return (System.currentTimeMillis() / 2) == 0;
        return true;
    }

    private final CustomSync sync = new CustomSync();

    @Override
    public void lock() {
        sync.tryAcquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        try {
            sync.acquire(1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        System.out.println("-=-=-=-=-=-=-=-=-=-=-");
        int waitCount = 1;
        for (Thread thread : sync.getWaitingThreads()) {
            System.out.println("在[" + waitCount++ + "]上等待的是: " + thread.getName());
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-");
        sync.release(1);
//        sync.tryRelease(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /** non-reentrant lock 0:unlocked 1:locked */
    private static class CustomSync extends AbstractQueuedSynchronizer {

        public Collection<Thread> getWaitingThreads() {
            // 这里是倒序的，要摆回去
            List<Thread> threads = new ArrayList<>(super.getExclusiveQueuedThreads());
            List<Thread> res = new ArrayList<>();
            for (int i = threads.size() - 1; i >= 0; i--) {
                res.add(threads.get(i));
            }
            return res;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        // Provides a Condition
        Condition newCondition() {
            return new ConditionObject();
        }

        // Deserializes properly
        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0);
        }
    }

}
