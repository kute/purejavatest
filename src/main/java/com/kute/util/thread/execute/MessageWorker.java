package com.kute.util.thread.execute;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageWorker extends Thread {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageWorker.class);
    
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Channel channel = null;
    
    private BlockingQueue<MessageData> queue;
    
    public MessageWorker(QueueConfig config, BlockingQueue<MessageData> queue) {
        super("message-worker-thread-" + config.getQueueName());
        this.setDaemon(true);
        this.queue = queue;
        
        initConnectionFactory();
    }
    
    /**
     * 初始化rabbitmq factory
     */
    private void initConnectionFactory() {
        try {
            factory = new ConnectionFactory();
            factory.setHost(QueueConstant.HOST);
            factory.setPort(QueueConstant.PORT);
            factory.setUsername(QueueConstant.USERNAME);
            factory.setPassword(QueueConstant.PASSWORD);
            factory.setVirtualHost(QueueConstant.VHOST);
        } catch (Throwable e) {
            logger.error("Initial connection factory fail.", e);
        }
    }
    
    /**
     * 连接  rabbitmq
     */
    private void makeConnection() {
        boolean isConnected = false;
        while (!isConnected) {
            try {
                connection = factory.newConnection();
                channel = connection.createChannel();
                for (QueueConfig type: QueueConfig.values()) {
                    channel.queueDeclare(type.getQueueName(), type.isDurable(),
                            type.isExclusive(), type.isAutoDelete(), type.getArguments());
                }
                isConnected = true;
            } catch (Throwable e) {
                logger.error("Connect to message queue error. Retry in "
                        + QueueConstant.RETRY_INTERVAL + " million seconds");
                QueueUtil.close(connection, channel);
                QueueUtil.sleep(QueueConstant.RETRY_INTERVAL);
            }
        }
    }

    @Override
    public void run() {
        
        makeConnection();
        
        //循环一直取队列中的数据，并发送到 mq
        MessageData message = null;
        while (true) {
            while (true) {
                try {
                    message = queue.take();
                    channel.basicPublish("", message.getQueue().getQueueName(), null, message.getMessageJsonBody().getBytes(Charsets.UTF_8));
                    if (logger.isDebugEnabled()) {
                        logger.debug("Send message success.");
                    }
                } catch (Throwable e) {
                    queue.offer(message);
                    logger.error("Send messge to message queue error.", e);
                    QueueUtil.sleep(QueueConstant.RETRY_INTERVAL);
                    QueueUtil.close(connection, channel);
                    makeConnection();
                }
            }
        }
        
    }
    
}
