package com.kute.util.thread.execute;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 对于 声明在 QueueConfig中的每个mq队列，分别初始化一个线程，循环一直取队列中的消息并发送到mq
 * usage： 
 * @see com.kute.util.thread.MessageQueueTest
 *
 */
public class MessageProducer {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    
    //存储 所有的mq队列以及队列所拥有的消息
    private static Map<QueueConfig, BlockingQueue<MessageData>> queueMap;
    private final ExecutorService messageThreadPool;
    
    private static MessageProducer instance = new MessageProducer();
    
    public MessageProducer() {
        
        logger.info("Begin to init message queue producer.");
        int queueNum = QueueConfig.values().length;
        
        queueMap = new HashMap<QueueConfig, BlockingQueue<MessageData>>(queueNum);
        
        messageThreadPool = Executors.newFixedThreadPool(queueNum);
        
        for(QueueConfig config : QueueConfig.values()) {
            //每个mq队列所能够处理的消息
            BlockingQueue<MessageData> queue = new LinkedBlockingDeque<MessageData>(QueueConstant.QUEUE_CAPACITY);
            queueMap.put(config, queue);
            messageThreadPool.execute(new MessageWorker(config, queue));
        }
        
        logger.info("Message queue producer initialization finished.");
    }
    
    public static MessageProducer getInstance() {
        return instance;
    }
    
    public void addUser(User user) {
        if(null == user) return;
        try {
            if(!queueMap.get(QueueConfig.ADD_USER).offer(new MessageData(QueueConfig.ADD_USER, user.toString()))) {
                throw new RuntimeException("addUser queue is full and current size:" + queueMap.get(QueueConfig.ADD_USER).size());
            }
        } catch (Throwable e) {
            logger.error("addUser message to local queueu fail:" + user.toString(), e);
        }
    }
    
}
