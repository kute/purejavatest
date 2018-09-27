package com.kute.util.cache.redis;

import com.kute.util.cache.redis.hash.HashRedisUtil;

/**
 * Created by longbai on 2017年12月27日上午10:02:57
 *
 **/
public class RedisTest {
    
    public static void main(String[] args) {

        try {
//            System.out.println(ShardedRedisUtil.getInstance().get("last-url-id"));
            System.out.println(HashRedisUtil.getInstance().get("kute"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
