package com.kute.queue.disruptor.producer;

import com.kute.queue.disruptor.event.DemoEvent;
import com.lmax.disruptor.RingBuffer;

/**
 * created by bailong001 on 2019/03/11 16:57
 */
public class DemoProducer {

    private final RingBuffer<DemoEvent> ringBuffer;

    public DemoProducer(RingBuffer<DemoEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String d1, Long d2)
    {
        long sequence = ringBuffer.next();
        try {
            DemoEvent event = ringBuffer.get(sequence);
            // for the sequence
            event.setData(d1 + d2);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
