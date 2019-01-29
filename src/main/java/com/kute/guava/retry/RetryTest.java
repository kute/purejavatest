package com.kute.guava.retry;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by kute on 2018/04/23 23:00
 */
public class RetryTest {

    private static final Logger logger = LoggerFactory.getLogger(RetryTest.class);

    private static volatile AtomicInteger number = new AtomicInteger(0);

    @Test
    public void test1() {

        Callable<Boolean> callable = () -> {
            logger.info("{}", number.incrementAndGet());
            return null;
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                // 重试等待时间(ms) = 100 * (2 ^ 重试次数)，指数级增长方式，最多等待 5s
                .withWaitStrategy(WaitStrategies.exponentialWait(100, 5, TimeUnit.SECONDS))
                // 重试等待时间=斐波那契数列 增长方式
//                .withWaitStrategy(WaitStrategies.fibonacciWait(100, 5, TimeUnit.SECONDS))
//                .withStopStrategy(StopStrategies.neverStop())
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
//                .withStopStrategy(StopStrategies.stopAfterDelay(3, TimeUnit.MINUTES))
                // 休眠等待的方式，默认是 Thread.sleep
                .withBlockStrategy(BlockStrategies.threadSleepStrategy())
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        try {
                            logger.info("{}={}={}={}", attempt.getAttemptNumber(), attempt.get(), attempt.hasResult(), attempt.hasException());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build();

        try {
            retryer.call(callable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RetryException e) {
            e.printStackTrace();
        }


    }


}
