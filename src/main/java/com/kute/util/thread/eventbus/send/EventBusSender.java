package com.kute.util.thread.eventbus.send;

import com.alibaba.fastjson.JSONObject;
import com.kute.util.thread.eventbus.index.MyIndex;
import com.kute.util.thread.eventbus.logger.MyLogger;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by bailong001 on 2019/03/08 15:38
 */
public class EventBusSender {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusSender.class);

    private static EventBus eventBus;

    static {
//        eventBus = EventBus.getDefault();
        eventBus = EventBus.builder()
                // 当 事件 未有 订阅者 时 打印log，日志级别是 FINE，默认true
                .logNoSubscriberMessages(true)
                // 当 事件 未有 订阅者 时，发送 NoSubscriberEvent，默认true
                .sendNoSubscriberEvent(true)
                // 当 订阅者消费时抛出异常时 发送 SubscriberExceptionEvent 事件，默认true
                .sendSubscriberExceptionEvent(true)
                // 记录 在订阅者 抛出的异常，default true
                .logSubscriberExceptions(true)
                // 自定义logger
                .logger(new MyLogger())
                // 是否 允许 父事件 触发
                .eventInheritance(false)
                // 添加 发现订阅者索引，会通过反射 获取 方法名以及参数名，进而调用
                .addIndex(new MyIndex())
                //
                .ignoreGeneratedIndex(false)
                // 当订阅者抛出异常时 是否 抛出 EventBusException 异常，default false
                .throwSubscriberException(true)
                // 自定义线程池
//                .executorService()
                .build();
    }

    public static void regist(Object listener) {
        eventBus.register(listener);
    }

    public static void post(Object o, boolean sticky) {
        if(null == o) {
            return;
        }
        LOGGER.info("EventBus send event:{}, sticky:{}", JSONObject.toJSONString(o), sticky);
        if(sticky) {
            eventBus.postSticky(o);
        } else {
            eventBus.post(o);
        }
    }

    public static void post(Object o) {
        post(o, false);
    }

}
