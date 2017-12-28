package com.kute.util.cache.redisson;

import org.redisson.api.RedissonClient;

/**
 * Created by longbai on 2017/12/28.
 */
public class MockTest {

    public static void main(String[] args) {
        try {
            RedissonClient client = RedissonUtil.initSingleServerClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
