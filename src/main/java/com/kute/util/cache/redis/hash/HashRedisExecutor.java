package com.kute.util.cache.redis.hash;

import redis.clients.jedis.Jedis;

public interface HashRedisExecutor<T> {
    T execute(Jedis jedis);
}
