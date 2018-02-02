package com.kute.guava.eventbus;

public interface MessageHander {

    void handle(MessageEvent messageEvent) throws Exception;

}
