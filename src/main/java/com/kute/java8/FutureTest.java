package com.kute.java8;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * created by bailong001 on 2019/02/16 19:48
 */
public class FutureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureTest.class);

    @Test
    public void test() throws InterruptedException {

        List<String> list = Lists.newArrayList("1", "2", "3", "4");
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        Stopwatch stopwatch = Stopwatch.createStarted();
        list.parallelStream().map(data -> CompletableFuture.supplyAsync(() -> sleepFunction(data)))
                .map(future -> future.thenApply(d -> d + "s"))
                .map(future -> future.whenComplete((result, throwable) -> {
                    if (null != throwable) {
                        LOGGER.error("异常发生", throwable);
                    } else {
                        LOGGER.info("完成：{}", result);
                    }
                    countDownLatch.countDown();
                }))
                .map(future -> future.getNow("还未完成"))
                .forEach(LOGGER::info);
        countDownLatch.await();
        LOGGER.info("take total:{}", stopwatch.elapsed(TimeUnit.SECONDS));
    }

    private String sleepFunction(String data) {
        if ("3".equalsIgnoreCase(data)) {
            throw new RuntimeException("RuntimeException");
        }
        try {
            TimeUnit.SECONDS.sleep(Integer.parseInt(data));
        } catch (Exception e) {

        }
        return data;
    }

}
