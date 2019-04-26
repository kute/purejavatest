package com.kute.util.thread.eventbus.event;

/**
 * created by bailong001 on 2019/03/08 16:14
 */
public class StickyEvent {

    private Integer id;

    public StickyEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
