package com.kute.guava.eventbus.event;

import java.io.Serializable;

/**
 * created by kute on 2018/02/04 15:59
 */
public class NoListenerEvent implements Serializable {

    private static final long serialVersionUID = 6822921883309929675L;
    private String message;

    public NoListenerEvent() {
    }

    public NoListenerEvent(String message) {
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
        return "NoListenerEvent{" +
                "message='" + message + '\'' +
                '}';
    }
}
