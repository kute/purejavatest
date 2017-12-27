package com.kute.util.cache.redis;

import java.util.Collections;

import redis.clients.jedis.Jedis;

/**
 * Created by longbai on 2017年12月27日下午3:11:57
 **/
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    
    private static final  String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 
     * @param jedis
     * @param lockKey
     * @param requestId
     *        设置锁值，客户端标识，表示是此客户端业务加的锁
     * @param expireTime
     * @return
     */
    public static boolean tryGetLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        return LOCK_SUCCESS.equals(jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime));
    }
    
    /**
     * 
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean releaseLock(Jedis jedis, String lockKey, String requestId) {
        return RELEASE_SUCCESS.equals(jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId)));
    }

}
