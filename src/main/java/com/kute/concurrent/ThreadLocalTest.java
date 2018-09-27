package com.kute.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * created by kute on 2018/07/25 11:47
 */
public class ThreadLocalTest {

//    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(9);

        threadLocal.set(10);
        for(int i=1; i< 10; i++) {

            int finalI = i;
            Thread thread = new Thread(() -> {

                System.out.println(Thread.currentThread().getName() + "=" + threadLocal.get());
                threadLocal.set(finalI);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countDownLatch.countDown();

                System.out.println(Thread.currentThread().getName() + "=" + threadLocal.get());

            }, "thread-" + i);
            thread.start();

        }

        countDownLatch.await();

    }

}
