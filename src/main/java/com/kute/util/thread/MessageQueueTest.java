package com.kute.util.thread;

import com.kute.util.thread.execute.MessageProducer;
import com.kute.util.thread.execute.User;

public class MessageQueueTest {

    public static void main(String[] args) {
        
        for(int i=1; i<=1; i++) {
            User user = new User(i, "kute-" + i, 0);
//            System.out.println(user);
            
            MessageProducer.getInstance().addUser(user);
        }
    }

}
