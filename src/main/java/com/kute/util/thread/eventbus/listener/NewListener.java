package com.kute.util.thread.eventbus.listener;

import com.alibaba.fastjson.JSONObject;
import com.kute.util.thread.eventbus.event.NewEvent;
import com.kute.util.thread.eventbus.event.NickEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by bailong001 on 2019/03/08 18:21
 */
public class NewListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewListener.class);

    /**
     * 虽然这里并未 添加 @Subscribe  注解，但是 在 com.kute.util.thread.eventbus.index.MyIndex 中添加了 对 该 事件的找寻索引，
     * 所以也是可以订阅到的，注意方法 所在的类 需要先 注册才行，需要通过反射能获取到
     * @param event
     */
    public void consumeNewEvent(NewEvent event) {
        LOGGER.info("consumeNewEvent ：{}", event.getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNickEvent(NickEvent event) {
        LOGGER.info("onNickEvent thread[{}] receive event[{}]", Thread.currentThread().getName(), JSONObject.toJSONString(event));
    }
}
