package com.kute.guava.eventbus.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class MessageEventBus {

    private static final Logger logger = LoggerFactory.getLogger(MessageEventBus.class);

    public static final ExecutorService executor =
            new ThreadPoolExecutor(50, 50, 10, TimeUnit.MINUTES,
                    new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static final EventBus EVENT_BUS = new AsyncEventBus(executor);

    public static void post(Object event) {
        EVENT_BUS.post(event);
//        logger.info("post event:{}", event);
    }

    public static void post(Object ... events) {
        for (Object event : events) {
            EVENT_BUS.post(event);
//            logger.info("post event:{}", event);
        }
    }

    public static void register(Object listener) {
        EVENT_BUS.register(listener);
//        logger.info("register listener:{}", listener);
    }

    public static void register(Object ... listeners) {
        for (Object listener : listeners) {
            EVENT_BUS.register(listener);
//            logger.info("register listener:{}", listener);
        }
    }

    public static void unregister(Object handler) {
        EVENT_BUS.unregister(handler);
    }

}
