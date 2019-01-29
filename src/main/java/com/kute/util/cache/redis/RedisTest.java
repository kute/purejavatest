package com.kute.util.cache.redis;

import com.alibaba.fastjson.JSON;
import com.kute.po.Book;
import com.kute.util.cache.redis.hash.HashRedisUtil;
import redis.clients.jedis.Jedis;

/**
 * Created by longbai on 2017年12月27日上午10:02:57
 **/
public class RedisTest {

    private static final HashRedisUtil redis = HashRedisUtil.getInstance();

    public static void main(String[] args) {

        try {
            Book book = new Book();
            book.setName("name");
            Object o = JSON.toJSON(JSON.toJSONString(book));

            Jedis jedis = new Jedis("localhost", 6379, 1000);
            jedis.auth("kuteredis");
            jedis.clusterAddSlots(0);

            jedis.set("kute", o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
