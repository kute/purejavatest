package com.kute.util.thread.eventbus.listener;

import com.alibaba.fastjson.JSONObject;
import com.kute.util.thread.eventbus.event.*;
import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * created by bailong001 on 2019/03/08 15:34
 */
public class EventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    /**
     * 父 事件 会在 子事件 执行结束后 才会别订阅
     * @param event
     */
    @Subscribe
    public void onBaseEvent(BaseEvent event) {
        LOGGER.info("onBaseEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), event);
    }

    /**
     * 默认值
     * @param orderEvent
     */
    @Subscribe(
            /**
             * 订阅者在 发布者线程中执行，默认值，适用于 快速简短非阻塞的任务
             * 消费者若阻塞，则生产者也阻塞直到结束
            */
            threadMode = ThreadMode.POSTING,
            /**
             * 订阅者会 在 发布者线程中执行，会阻塞 发布者线程，有多个事件则
             * 非阻塞地放入队列
            */
//            threadMode = ThreadMode.MAIN,
            /**
             *  和 MAIN 不同的是， MAIN_ORDERED 会始终 按照入队列的顺序，不是发送的顺序
            */
//            threadMode = ThreadMode.MAIN_ORDERED,
            /**
             * 异步模式，在独立的线程执行，适用于 耗时任务，不会阻塞调用者线程，
             * 有多少事件，就会起多少线程消费
             * 不会阻塞发送者线程，不会考虑订阅者是否执行完成
            */
//            threadMode = ThreadMode.ASYNC,
            /**
             *
             * 不会阻塞发送者线程，不会考虑订阅者是否执行完成
             * 若 调用者线程非 主线程，则 订阅者直接在 调用者线程执行
             * 若 调用者线程为 主线程，则 会 起一个 保护线程 去 按顺序执行
            */
//            threadMode = ThreadMode.BACKGROUND,
            sticky = false,
            priority = 0
    )
    public void onOrderEvent(OrderEvent orderEvent) {
        LOGGER.info("onOrderEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), JSONObject.toJSONString(orderEvent));
        sleep(3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onPackEvent(PackEvent event) {
        LOGGER.info("onPackEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), JSONObject.toJSONString(event));
        sleep(2);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNickEvent(NickEvent event) {
        sleep(2);
        LOGGER.info("onNickEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), JSONObject.toJSONString(event));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onPauEvent(PauEvent event) {
        sleep(10);
        LOGGER.info("onPauEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), JSONObject.toJSONString(event));
    }

    @Subscribe(sticky = true)
    public void onStickyEvent(StickyEvent event) {
        LOGGER.info("onStickyEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), JSONObject.toJSONString(event));
    }

    @Subscribe
    public void onExceptionEvent(ExceptionEvent event) {
        LOGGER.info("onExceptionEvent begin throw exception：{}", JSONObject.toJSONString(event));
        throw new IllegalStateException("状态不对");
    }

    /**
     * 当 发现 未订阅的事件时 这里消费
     * @param event
     */
    @Subscribe
    public void onNoSubscriberEvent(NoSubscriberEvent event) {
        LOGGER.info("onNoSubscriberEvent 发现未有订阅的事件：{}", JSONObject.toJSONString(event));
    }

    /**
     * 当 某个事件在订阅消费时产生异常
     * @param event
     */
    @Subscribe
    public void onSubscriberExceptionEvent(SubscriberExceptionEvent event) {
        // SubscriberExceptionEvent 会包含 有异常的事件源数据、异常信息，以及 eventbus的一些信息
        LOGGER.info("onSubscriberExceptionEvent 发现某个事件在订阅消费时产生异常：{}", JSONObject.toJSONString(event));
    }

    private void sleep(int n) {
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
