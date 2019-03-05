package com.kute.test;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * created by kute on 2018-03-08 18:56
 */
public class CtlTest {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;

    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }

    public static void main(String[] args) {

        System.out.println(new CtlTest().ctl.get() + "=" + Integer.toBinaryString(new CtlTest().ctl.get()));
        print(COUNT_BITS);
        print(CAPACITY);
        print(~CAPACITY);
        print(RUNNING);
        print(SHUTDOWN);
        print(STOP);
        print(TIDYING);
        print(TERMINATED);
        print(runStateOf(new CtlTest().ctl.get()));
        print(workerCountOf(new CtlTest().ctl.get()));
        print(-1);

        Executors.newFixedThreadPool(8);

        int n= 1 << 2;
        retry:
        for(;;) {
//            return;

            System.out.println("1");
            for(;;) {
                System.out.println("2");
                if (n > 2) {
                    continue retry;
                }
                System.out.println(43);
//                return;
            }
//            System.out.println("3");
//            System.out.println("23");
        }
    }

    private static void print(int n) {
        System.out.println(n + "=" + Integer.toBinaryString(n) + "=" + Integer.toBinaryString(n).length());
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CtlTest.class);

    @Test
    public void test() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
        ExecutorService executorService = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());

        Stopwatch stopwatch = Stopwatch.createStarted();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        IntStream.rangeClosed(1, 10).forEach(i -> {
            executorService.submit(() -> {
                System.out.println("thread " + Thread.currentThread().getName() + " run " + i + " done");
                try {
                    TimeUnit.SECONDS.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i == 10) {
                    countDownLatch.countDown();
                }
            });
        });
        countDownLatch.await();
        System.out.println("take:" + stopwatch.elapsed(TimeUnit.MILLISECONDS));

    }

}
