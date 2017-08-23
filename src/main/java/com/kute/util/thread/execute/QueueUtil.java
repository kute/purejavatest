package com.kute.util.thread.execute;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class QueueUtil {
    
    public static void sleep(long millionSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(millionSeconds);
        } catch (Throwable e) {
        }
    }

    public static void close(Connection connection, Channel channel) {
        close(channel);
        if (connection != null) try {
            connection.close();
        } catch (Throwable e) {
        }
    }

    public static void close(Channel channel) {
        if (channel != null) try {
            channel.close();
        } catch (Throwable e) {
        }
    }

}
