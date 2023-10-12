package indl.lixn.lx7xl.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author listen
 * like a CountDownLatch except that it only requires a single signal to fire
 * Bacause a latch is non-exclusive, it use the shared acquire and release methods
 **/
public class BooleanLatch {

    private static class CustomSync extends AbstractQueuedSynchronizer {
        boolean isSignalled() {
            return getState() != 0;
        }

        protected int tryAcquireShared(int ignore) {
            return isSignalled() ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignore) {
            setState(1);
            return true;
        }
    }

    private final CustomSync sync = new CustomSync();

    public boolean isSignalled() {
        return sync.isSignalled();
    }

    public void signal() {
        sync.releaseShared(1);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

}
