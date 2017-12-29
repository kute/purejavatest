package com.kute.util.cache.redisson.base;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.net.URL;
import java.util.function.Supplier;

/**
 * @author longbai
 * @date 2017/12/29
 */
@FunctionalInterface
public interface RedissonServerClientCreator {

    String fileName = null;

    static RedissonServerClientCreator create(final Supplier<RedissonServerClientCreator> supplier) {
        return supplier.get();
    }

    RedissonClient newClient() throws IOException;

    default RedissonClient initRedissonClient(String fileName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        return Redisson.create(Config.fromJSON(url));
    }

}
