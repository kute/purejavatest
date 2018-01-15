package com.kute.util.cache.redisson.base;

import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


/**
 * Created by longbai on 2017/12/29.
 */
public class SingleServerClientCreator implements RedissonServerClientCreator {

    private static final String SINGLE_SERVER_CONFIG_FILE = "redisson/single-server-config.json";

    private static final SingleServerClientCreator creator = new SingleServerClientCreator();

    @Override
    public RedissonClient newClient(Config oldConf) {
        if(null == oldConf) {
            oldConf = new Config();
        }
        return initRedissonClient(SINGLE_SERVER_CONFIG_FILE, oldConf);
    }

    public static SingleServerClientCreator getInstance() {
        return creator;
    }

}
