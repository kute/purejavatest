package com.kute.util.thread.execute;

import java.util.Map;

public enum QueueConfig {

    ADD_USER("add_user", true, false, false, null);

    private final String queueName;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;
    private Map<String, Object> arguments;

    QueueConfig(String queueName, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        this.queueName = queueName;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.arguments = arguments;
    }

    public String getQueueName() {
        return queueName;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }
}
