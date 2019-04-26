package com.kute.queue.disruptor.translator;

import com.kute.queue.disruptor.event.DemoEvent;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;

/**
 * created by bailong001 on 2019/03/11 16:50
 */
public class DemoProducerTranslator {

    private final RingBuffer<DemoEvent> ringBuffer;

    public DemoProducerTranslator(RingBuffer<DemoEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorTwoArg<DemoEvent, String, Long> TRANSLATOR =
            new EventTranslatorTwoArg<DemoEvent, String, Long>() {
                @Override
                public void translateTo(DemoEvent event, long sequence, String arg0, Long arg1) {

                }
            };

    public void onData(String d1, Long d2)
    {
        ringBuffer.publishEvent(TRANSLATOR, d1, d2);
    }

}
