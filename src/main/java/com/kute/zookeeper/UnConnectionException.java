package com.kute.zookeeper;

/**
 * created by kute on 2018/04/29 10:08
 */
public class UnConnectionException extends RuntimeException {

    public UnConnectionException(String message) {
        super(message);
    }

    public UnConnectionException(Throwable cause, String message) {
        super(message, cause);
    }
}
