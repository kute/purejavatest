package com.kute.guava;

import com.kute.guava.eventbus.MessageEvent;
import com.kute.guava.eventbus.MessageEventBus;
import com.kute.guava.eventbus.MessageListener;
import org.junit.Before;
import org.junit.Test;

public class EventBusTest {

    @Before
    public void init() {
        // regist listener
        MessageListener listener = new MessageListener();

        MessageEventBus.register(listener);
    }

    @Test
    public void test() {
        // post
        MessageEventBus.post(new MessageEvent());

    }
}
