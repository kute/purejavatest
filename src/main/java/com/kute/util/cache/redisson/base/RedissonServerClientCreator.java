package com.kute.util.cache.redisson.base;

import com.google.common.base.Joiner;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.function.Supplier;

/**
 * @author longbai
 * @date 2017/12/29
 */
@FunctionalInterface
public interface RedissonServerClientCreator {

    Logger logger = LoggerFactory.getLogger(RedissonServerClientCreator.class);

    String fileName = null;

    static RedissonServerClientCreator create(final Supplier<RedissonServerClientCreator> supplier) {
        return supplier.get();
    }

    RedissonClient newClient(Config oldConf) throws IOException;

    default RedissonClient initRedissonClient(String fileName, Config oldConf){
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        Config config;
        try {
            config = Config.fromJSON(url);
        } catch (IOException e) {
            logger.warn(Joiner.on("").join("initRedissonClient resource[", fileName, "] illegal"), e);
            config = null == oldConf ? new Config() : oldConf;
        }
        return Redisson.create(config);
    }

}
