package com.kute.guava.eventbus.listener;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by kute on 2018/02/04 15:23
 */
public class DeadEventListener {

    private static final Logger logger = LoggerFactory.getLogger(DeadEventListener.class);

    @Subscribe
    public void handle(DeadEvent deadEvent) {
        logger.info("收到 未有监听此事件的消息:{}", deadEvent);
    }
}
