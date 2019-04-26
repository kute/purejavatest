package com.kute.util.thread.eventbus.index;

import com.kute.util.thread.eventbus.event.NewEvent;
import org.greenrobot.eventbus.meta.SimpleSubscriberInfo;
import org.greenrobot.eventbus.meta.SubscriberInfo;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;
import org.greenrobot.eventbus.meta.SubscriberMethodInfo;

/**
 * created by bailong001 on 2019/03/08 18:06
 */
public class MyIndex implements SubscriberInfoIndex {

    @Override
    public SubscriberInfo getSubscriberInfo(Class<?> subscriberClass) {
        return new SimpleSubscriberInfo(subscriberClass, true, new SubscriberMethodInfo[]{
                new SubscriberMethodInfo("consumeNewEvent", NewEvent.class)
        });
    }
}
