package com.kute.queue.disruptor.factory;

import com.kute.queue.disruptor.event.DemoEvent;
import com.lmax.disruptor.EventFactory;

/**
 * created by bailong001 on 2019/03/11 16:46
 */
public class DemoEventFactory implements EventFactory<DemoEvent> {
    @Override
    public DemoEvent newInstance() {
        return new DemoEvent();
    }
}
