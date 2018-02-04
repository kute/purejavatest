package com.kute.guava.eventbus.event;

import java.io.Serializable;

/**
 * created by kute on 2018/02/04 10:27
 */
public class MessageEvent implements Serializable {

    private static final long serialVersionUID = -3957126967306036558L;
    private String message;

    public MessageEvent() {
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "message='" + message + '\'' +
                '}';
    }
}
