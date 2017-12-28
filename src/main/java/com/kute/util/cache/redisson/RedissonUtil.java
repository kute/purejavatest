package com.kute.util.cache.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by longbai on 2017年12月27日下午5:56:29
 **/
public class RedissonUtil {

    private static final String SINGLE_SERVER_CONFIG_FILE = "/redisson/single-server-config.json";

    public static RedissonClient initSingleServerClient() throws IOException {
        return initRedissonClient(SINGLE_SERVER_CONFIG_FILE);
    }

    public static RedissonClient initRedissonClient(String fileName) throws IOException {
        InputStream inputStream = RedissonUtil.class.getClassLoader().getResourceAsStream(SINGLE_SERVER_CONFIG_FILE);
        Config config;
        if(null != inputStream) {
            config = Config.fromJSON(fileName);
        } else {
            config = new Config();
        }
        return Redisson.create(config);
    }

}
