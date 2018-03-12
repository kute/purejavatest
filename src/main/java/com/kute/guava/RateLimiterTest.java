package com.kute.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * 流控(速率)
 * 令牌通
 * created by kute on 2018-03-12 13:51
 */
public class RateLimiterTest {

    private void time(int size, int tasks, int acquire) {

        /**
         * 创建大小为size的令牌桶，每秒向桶中放入size个令牌
         */
        RateLimiter rate1 = RateLimiter.create(size);

        for(int task=1; task <= tasks; task ++) {
            if(acquire > 0) {
                // 获取 多个令牌
                rate1.acquire(acquire);
            } else {
                // 获取一个令牌， acquire() == acquire(1)
                rate1.acquire();
            }
            System.out.println(new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd HH:mm:ss"));
        }
        System.out.println("-------------------------------------------");
    }

    @Test
    public void test() {

        // 每秒一个任务, 可以看到 一秒内输出 一个时间
//        time(1, 10, 0);

        // 每秒2个任务, 可以看到 一秒内输出 2个时间
//        time(2, 10, 0);

        // 每秒向桶中放3个令牌，所以每次获取一个令牌时可以执行3个任务
//        time(3, 10, 0);

        // 每秒获取2个令牌，即 2秒一个任务
//        time(1, 10, 2);

        // 每秒放2个令牌，但是每秒也取2个令牌，所以最终结果就是每秒只能执行一个任务
//        time(2, 10, 2);

        // 每秒放2个，每秒取3个，结果就是有的任务隔2秒执行，有的隔1秒
//        time(2, 10, 3);

        // 每秒放2个，每秒取5个，结果就是有的任务隔2秒执行，有的隔3秒
//        time(2, 10, 5);

    }

}
