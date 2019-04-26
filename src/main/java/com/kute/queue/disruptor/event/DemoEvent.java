package com.kute.queue.disruptor.event;

import java.io.Serializable;

/**
 * created by bailong001 on 2019/03/11 16:46
 */
public class DemoEvent implements Serializable {

    private String data;

    public DemoEvent() {
    }

    public DemoEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public DemoEvent setData(String data) {
        this.data = data;
        return this;
    }
}
