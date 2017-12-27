package com.kute.util.cache.redis;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.google.common.collect.Lists;

public class DistributeRedisLock {

    /**
     * SETNX
     */
    public static final String NX = "NX";

    /**
     * EXPIRE key seconds
     */
    public static final String EX = "EX";

    /**
     * 调用set后的返回值
     */
    public static final String OK = "OK";

    /**
     * 默认请求锁的超时时间(ms 毫秒)
     */
    private static final long TIME_OUT = 100;

    /**
     * 默认锁的有效时间(s)
     */
    public static final int EXPIRE = 60;

    /**
     * 解锁的lua脚本
     */
    public static final String UNLOCK_LUA;

    // TODO 注入替换
    private StringRedisTemplate redisTemplate;

    /**
     * 锁对应的值
     */
    private String lockValue;

    /**
     * 锁标记
     */
    private volatile boolean locked = false;

    final Random random = new Random();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * 尝试获取锁 超时返回
     *
     * @return
     */
    public boolean tryLock(final String lockKey) {
        lockValue = UUID.randomUUID().toString();
        long timeout = TIME_OUT * 1000000;
        long nowTime = System.nanoTime();
        while ((System.nanoTime() - nowTime) < timeout) {
            if (OK.equalsIgnoreCase(this.set(lockKey, lockValue, EXPIRE))) {
                locked = true;
                return true;
            }
            seleep(10, 50000);
        }
        return locked;
    }

    /**
     * 尝试获取锁 立即返回
     *
     * @return 是否成功获得锁
     */
    public boolean lock(final String lockKey) {
        lockValue = UUID.randomUUID().toString();
        // 不存在则添加 且设置过期时间（单位ms）
        String result = set(lockKey, lockValue, EXPIRE);
        return OK.equalsIgnoreCase(result);
    }

    /**
     * 以阻塞方式的获取锁
     *
     * @return 是否成功获得锁
     */
    public boolean lockBlock(final String lockKey) {
        lockValue = UUID.randomUUID().toString();
        while (true) {
            // 不存在则添加 且设置过期时间（单位ms）
            String result = set(lockKey, lockValue, EXPIRE);
            if (OK.equalsIgnoreCase(result)) {
                return true;
            }
            // 每次请求等待一段时间
            seleep(10, 50000);
        }
    }

    /**
     * 解锁
     * <p>
     * 可以通过以下修改，让这个锁实现更健壮：
     * <p>
     * 不使用固定的字符串作为键的值，而是设置一个不可猜测（non-guessable）的长随机字符串，作为口令串（token）。 不使用 DEL 命令来释放锁，而是发送一个 Lua
     * 脚本，这个脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除。 这两个改动可以防止持有过期锁的客户端误删现有锁的情况出现。
     */
    public Boolean unlock(final String lockKey) {
        // 只有加锁成功并且锁还有效才去释放锁
        // 只有加锁成功并且锁还有效才去释放锁
        if (locked) {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    Object nativeConnection = connection.getNativeConnection();
                    Long result = 0L;

                    List<String> keys = Lists.newArrayList(lockKey);
                    List<String> values = Lists.newArrayList(lockValue);

                    // 集群模式
                    if (nativeConnection instanceof JedisCluster) {
                        result = (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, values);
                    }

                    // 单机模式
                    if (nativeConnection instanceof Jedis) {
                        result = (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, values);
                    }

                    locked = result == 0;
                    return result == 1;
                }
            });
        }

        return true;
    }

    /**
     * 重写redisTemplate的set方法
     * <p>
     * 命令 SET resource-name anystring NX EX max-lock-time 是一种在 Redis 中实现锁的简单方法。
     * <p>
     * 客户端执行以上的命令：
     * <p>
     * 如果服务器返回 OK ，那么这个客户端获得锁。 如果服务器返回 NIL ，那么客户端获取锁失败，可以在稍后再重试。
     *
     * @param key 锁的Key
     * @param value 锁里面的值
     * @param seconds 过去时间（秒）
     * @return
     */
    private String set(final String key, final String value, final long seconds) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String result = null;
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result = ((JedisCluster) nativeConnection).set(key, value, NX, EX, seconds);
                }
                // 单机模式
                if (nativeConnection instanceof Jedis) {
                    result = ((Jedis) nativeConnection).set(key, value, NX, EX, seconds);
                }
                return result;
            }
        });
    }

    /**
     * @param millis 毫秒
     * @param nanos 纳秒
     * @Title: seleep
     * @Description: 线程等待时间
     * @author yuhao.wang
     */
    private void seleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, random.nextInt(nanos));
        } catch (InterruptedException e) {
        }
    }

}
