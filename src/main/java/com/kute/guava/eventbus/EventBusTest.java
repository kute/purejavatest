package com.kute.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.kute.guava.eventbus.event.MessageEvent;
import com.kute.guava.eventbus.event.OrderMessageEvent;
import com.kute.guava.eventbus.event.PackMessageEvent;
import com.kute.guava.eventbus.eventbus.MessageEventBus;
import com.kute.guava.eventbus.listener.DeadEventListener;
import com.kute.guava.eventbus.listener.OrderMessageListener;
import com.kute.guava.eventbus.listener.PackMessageListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * spring 集成 eventbus: github.com/kute/spring-boot-demo/com.kute.demo.eventbus.EventBusTest
 */
public class EventBusTest {

    @Before
    public void init() {
        // regist listener
        OrderMessageListener orderMessageListener = new OrderMessageListener();
        PackMessageListener packMessageListener = new PackMessageListener();

        DeadEventListener deadEventListener = new DeadEventListener();

        MessageEventBus.register(orderMessageListener, packMessageListener, deadEventListener);
    }

    @After
    public void sleep() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2L);
    }

    @Test
    public void test() {
        OrderMessageEvent orderMessageEvent = new OrderMessageEvent("Order Message Event", 1001, 35.9);
        MessageEventBus.post(orderMessageEvent);

    }

    @Test
    public void test1() {

        OrderMessageEvent orderMessageEvent = new OrderMessageEvent("Order Message Event", 1001, 35.9);
        PackMessageEvent packMessageEvent = new PackMessageEvent("Pack message event", "Beijing", new Timestamp(System.currentTimeMillis()));

        MessageEventBus.post(orderMessageEvent, packMessageEvent);

    }

    /**
     * 并发测试
     * @throws InterruptedException
     */
    @Test
    public void test2() {
        for (int i = 1; i <= 10; i++) {
            MessageEventBus.post(new OrderMessageEvent("Order Message Event", 1000 + i, 35.9));
        }
    }

    @Test
    public void test3() {
        DeadEvent deadEvent = new DeadEvent("ph", new MessageEvent("dead message"));
        MessageEventBus.post(deadEvent);
    }

}
