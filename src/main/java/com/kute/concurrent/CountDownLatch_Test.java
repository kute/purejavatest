package com.kute.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * created by kute on 2018/03/13 22:18
 */
public class CountDownLatch_Test {

    private static final Logger logger = LoggerFactory.getLogger(CountDownLatch_Test.class);

    public static void main(String[] args) {

        int n = 5;

        CountDownLatch latch = new CountDownLatch(n);

        logger.info("main thread start");

        for (int i = 1; i <= n; i++) {
            final int j = i;
            new Thread(() -> {
                logger.info("thread {} sleep {}s begin", j, j);
                try {
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("thread {} sleep {}s end", j, j);
                latch.countDown();
            }).start();
        }
        logger.info("main thread begin wait other threads");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("other threads execute over all");


    }

}
