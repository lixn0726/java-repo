package indl.lixn.lx7xl.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author listen
 **/
@Slf4j
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger(0);

    private int i = 0;

    private void count() {
        i++;
    }

    private void safeCount(int unused) {
        int t = 0;
        for (;;) {
//            log.info("Loop [{}] for [{}]", t++, unused);
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, i+1);
            if (suc) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    cas.count();
                    cas.safeCount(i);
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("i: " + cas.i);
        System.out.println("atomicI: " + cas.atomicI.get());
        System.out.println("Cost: " + (System.currentTimeMillis() - start));
    }

}
