package com.kute.util.cache.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by longbai on 2017年12月27日下午4:05:26
 *
 **/
public class RedisLock2 {
    
    private static final int LOCK_EXPIRE_TIME = 30;
    
    private Jedis jedis;
    
    public Boolean tryLock(String lockKey) {
        try {
            String lockExpireTimeStr = String.valueOf(System.currentTimeMillis() + (LOCK_EXPIRE_TIME * 1000));
            if (jedis.setnx(lockKey, lockExpireTimeStr) == 1L) {
                jedis.expire(lockKey, LOCK_EXPIRE_TIME);
                return true;
            }
            String currentLockExpireTime = jedis.get(lockKey);// 获取缓存中设置的锁的过期时间
            // 检查锁是否过期，这里主要是防止setNx命令执行后redis崩溃未执行expire操作造成死锁
            if (null != currentLockExpireTime
                    && Long.parseLong(currentLockExpireTime) < System.currentTimeMillis()) {
                String oldValue = jedis.getSet(lockKey, lockExpireTimeStr);
                // 防止锁过期后，多个线程并发获取到锁，通过对比获取的时间戳和设置时的时间戳是否相同判断是否当前线程获取到锁
                if (null != oldValue && oldValue.equals(currentLockExpireTime)) {
                    return true;
                }
            }
        } catch (Exception e) {
            unLock(lockKey);
        }
        return Boolean.FALSE;
    }

    public void unLock(String lockKey) {
        jedis.del(lockKey);
    }

}
