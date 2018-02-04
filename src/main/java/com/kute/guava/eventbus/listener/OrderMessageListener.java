package com.kute.guava.eventbus.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.kute.guava.eventbus.event.MessageEvent;
import com.kute.guava.eventbus.event.OrderMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by kute on 2018/02/04 09:54
 */
public class OrderMessageListener{

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);

    @Subscribe
    @AllowConcurrentEvents
    public void handle(OrderMessageEvent orderMessageEvent) {
        logger.info("order event:{}", orderMessageEvent);
    }

    @Subscribe
    public void handle(String event) {
        logger.info("string event:{}", event);
    }

    @Subscribe
    public void handle(MessageEvent messageEvent) {
        logger.info("same event:{}", messageEvent);
    }
}
