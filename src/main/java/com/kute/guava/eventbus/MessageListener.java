package com.kute.guava.eventbus;

import com.google.common.eventbus.Subscribe;

public class MessageListener implements MessageHander {

    @Subscribe
    @Override
    public void handle(MessageEvent messageEvent) throws Exception {

        System.out.println("message handle");

    }

}
