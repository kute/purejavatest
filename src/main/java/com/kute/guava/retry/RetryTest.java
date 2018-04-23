package com.kute.guava.retry;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
//                .withStopStrategy(StopStrategies.stopAfterDelay(3, TimeUnit.MINUTES))
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
