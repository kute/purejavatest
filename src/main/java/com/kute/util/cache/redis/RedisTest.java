package com.kute.util.cache.redis;

import com.kute.util.cache.redis.hash.HashRedisUtil;
import com.kute.util.cache.redis.shared.ShardedRedisUtil;

/**
 * Created by longbai on 2017年12月27日上午10:02:57
 *
 **/
public class RedisTest {
    
    public static void main(String[] args) {

        try {
//            System.out.println(ShardedRedisUtil.getInstance().get("last-url-id"));
            System.out.println(HashRedisUtil.getInstance().get("last-url-id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
