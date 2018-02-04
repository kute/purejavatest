package com.kute.guava.eventbus;

import com.kute.guava.eventbus.event.OrderMessageEvent;
import com.kute.guava.eventbus.event.PackMessageEvent;
import com.kute.guava.eventbus.eventbus.MessageEventBus;
import com.kute.guava.eventbus.listener.OrderMessageListener;
import com.kute.guava.eventbus.listener.PackMessageListener;
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

        MessageEventBus.register(orderMessageListener, packMessageListener);
    }

    @Test
    public void test() throws InterruptedException {
        OrderMessageEvent orderMessageEvent = new OrderMessageEvent("Order Message Event", 1001, 35.9);
        MessageEventBus.post(orderMessageEvent);

        TimeUnit.SECONDS.sleep(2L);
    }

    @Test
    public void test1() throws InterruptedException {

        OrderMessageEvent orderMessageEvent = new OrderMessageEvent("Order Message Event", 1001, 35.9);
        PackMessageEvent packMessageEvent = new PackMessageEvent("Pack message event", "Beijing", new Timestamp(System.currentTimeMillis()));

        MessageEventBus.post(orderMessageEvent, packMessageEvent);

        TimeUnit.SECONDS.sleep(2L);
    }

    /**
     * 并发测试
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            MessageEventBus.post(new OrderMessageEvent("Order Message Event", 1000 + i, 35.9));
        }

        TimeUnit.SECONDS.sleep(2L);
    }
}
