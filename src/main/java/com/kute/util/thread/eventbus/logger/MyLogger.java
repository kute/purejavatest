package com.kute.util.thread.eventbus.logger;

import org.greenrobot.eventbus.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

/**
 * created by bailong001 on 2019/03/08 17:52
 */
public final class MyLogger implements Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyLogger.class);

    @Override
    public void log(Level level, String msg) {
        LOGGER.info("[{}] ==== {}", level, msg);
    }

    @Override
    public void log(Level level, String msg, Throwable th) {
        LOGGER.error("[{}] ==== {}", level, msg, th);
    }
}
