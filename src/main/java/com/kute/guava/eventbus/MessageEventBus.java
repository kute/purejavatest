package com.kute.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.*;

public class MessageEventBus {

    public static final ExecutorService executor =
            new ThreadPoolExecutor(50, 50, 10, TimeUnit.MINUTES,
                    new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static final EventBus EVENT_BUS = new AsyncEventBus(executor);

    public static void post(Object event) {
        EVENT_BUS.post(event);
    }

    public static void register(Object handler) {
        EVENT_BUS.register(handler);
    }

    public static void unregister(Object handler) {
        EVENT_BUS.unregister(handler);
    }

}
