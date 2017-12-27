package com.kute.util.cache.redis.shared;

import redis.clients.jedis.ShardedJedis;

public interface ShardedRedisExecutor<T> {
    T execute(ShardedJedis jedis);
}
