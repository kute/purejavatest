package com.kute.zookeeper;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * created by kute on 2018/04/29 10:45
 *
 * 1、watcher种类：defaultWatcher，非defaultWatcher
 *
 * dafaultWatcher是在创建ZooKeeper对象时传递的watcher参数。非defaultWatcher只得是，在调用getData，getChildren，exists函数时传递的watcher对象。
 *
 * 二者的相同点在于：都可以监听数据节点的状态，比如在getData中使用了defaultWatcher，那么当监听的节点内容发生改变时，defaultWatcher就会收到通知。如果没有使用了非defaultWatcher，也是同样的。
 *
 * 而这的不同点在于：defaultWatcher会监听session的生命周期，比如session创建成功了，失效了等，而非defaultWatcher不具有这个职责。其次defaultWatcher并不与某一个节点路径相互关联。
 *
 *
 *
 */
public class DefaultWatcher implements Watcher, StatCallback, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(DefaultWatcher.class);

    private static volatile DefaultWatcher watcher = new DefaultWatcher();

    public static Watcher getInstance() {
        return watcher;
    }

    /**
     * 监听节点变化
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        logger.info(event.toString());

        if(event.getType() == Event.EventType.NodeCreated) {

        } else if (event.getType() == Event.EventType.NodeDataChanged) {

        } else if (event.getType() == Event.EventType.NodeDeleted) {

        } else if (event.getType() == Event.EventType.NodeChildrenChanged) {

        } else {
            // do nothing
        }

    }

    /**
     * 结果回调
     * @param rc
     * @param path
     * @param ctx
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

    }
}
