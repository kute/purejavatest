package com.kute.util.thread.eventbus;

import com.kute.google.guava.FutureTest;
import com.kute.util.thread.eventbus.event.*;
import com.kute.util.thread.eventbus.listener.NewListener;
import com.kute.util.thread.eventbus.send.EventBusSender;
import org.greenrobot.eventbus.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * created by bailong001 on 2019/03/08 15:38
 */
public class EventBusTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusTest.class);

    @Before
    public void before() {
        EventBus.clearCaches();

        NewListener listener1 = new NewListener();

        EventBusSender.regist(listener1);
//        EventListener listener = new EventListener();
//        EventBusSender.regist(listener);

        LOGGER.info("Current thread before:{}", Thread.currentThread().getName());
    }

    @Test
    public void test1() {
        OrderEvent orderEvent = new OrderEvent(1, Instant.now());
        EventBusSender.post(orderEvent);
    }

    @Test
    public void test2() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            FutureTest.coreThreadPoolExecutor.submit(() -> {
                EventBusSender.post(new PackEvent(i));
                LOGGER.info("post event:{}", i);
            });
        });
        countdonw(10);
    }

    @Test
    public void test3() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            EventBusSender.post(new NickEvent(i));
        });
    }

    @Test
    public void test4() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            EventBusSender.post(new PauEvent(i));
        });
    }

    @Test
    public void test5() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            EventBusSender.post(new StickyEvent(i), true);
        });
    }

    /**
     * 测试 死信
     */
    @Test
    public void test6() {
        EventBusSender.post(new DeadEvent(1));
    }

    /**
     * 测试 在 订阅消费时发生异常时
     */
    @Test
    public void test7() {
        EventBusSender.post(new ExceptionEvent(1));
    }

    @Test
    public void test8() {
        EventBusSender.post(new NewEvent());
    }

    @After
    public void after() {
        LOGGER.info("Current thread after:{}", Thread.currentThread().getName());
    }

    public void countdonw(int m) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await(m, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
