package com.kute.util.thread.execute;


public class MessageData {

    private final QueueConfig queue;
    private final String messageJsonBody;

    public MessageData(QueueConfig queue, String messageJsonBody) {
        this.queue = queue;
        this.messageJsonBody = messageJsonBody;
    }

    public QueueConfig getQueue() {
        return queue;
    }

    public String getMessageJsonBody() {
        return messageJsonBody;
    }
}
