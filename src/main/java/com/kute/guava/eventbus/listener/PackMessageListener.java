package com.kute.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import com.kute.guava.eventbus.event.MessageEvent;
import com.kute.guava.eventbus.event.PackMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by kute on 2018/02/04 09:54
 */
public class PackMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(PackMessageListener.class);

    @Subscribe
    public void handle(PackMessageEvent messageEvent) {
        logger.info("pack event:{}", messageEvent);

        throw new RuntimeException("Order listener throw exception");
    }

    @Subscribe
    public void handle(MessageEvent messageEvent) {
        logger.info("same event:{}", messageEvent);
    }
}
