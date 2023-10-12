package indl.lixn.lx7xl.juc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author listen
 **/
public class AQSDebug {

    private static CustomMutex cmx = new CustomMutex();

    public static void main(String[] args) throws Exception {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (;;) {
                        // lock 还是 tryLock呢
                        System.out.println(Thread.currentThread().getName() + " : 尝试获取同步状态");
                        boolean acquire = cmx.tryLock();
                        if (acquire) {
                            System.out.println(Thread.currentThread().getName() + " : 成功获取同步状态 持有5秒钟后释放");
                            TimeUnit.SECONDS.sleep(5);
                            cmx.unlock();
                            System.out.println(Thread.currentThread().getName() + " : 成功释放同步状态");
                            break;
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " : 生命周期结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.setName("MutexLocker-" + i);
            threads.add(thread);
        }

        Iterator<Thread> tI = threads.iterator();
        while (tI.hasNext()) {
            Thread t = tI.next();
            t.start();
        }
    }


}
