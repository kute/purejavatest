package com.kute.util.thread.eventbus.event;

/**
 * created by bailong001 on 2019/03/08 16:14
 */
public class DeadEvent{

    private Integer id;

    public DeadEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
