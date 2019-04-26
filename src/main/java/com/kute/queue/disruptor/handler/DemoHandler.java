package com.kute.queue.disruptor.handler;

import com.kute.queue.disruptor.event.DemoEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * created by bailong001 on 2019/03/11 16:46
 */
public class DemoHandler implements EventHandler<DemoEvent>, WorkHandler<DemoEvent> {
    @Override
    public void onEvent(DemoEvent demoEvent, long sequence, boolean endOfBatch) throws Exception {

    }

    @Override
    public void onEvent(DemoEvent event) throws Exception {

    }
}
