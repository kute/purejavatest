package com.kute.util.thread.eventbus.event;

import java.io.Serializable;

/**
 * created by bailong001 on 2019/03/08 15:20
 */
public abstract class BaseEvent implements Serializable {

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
