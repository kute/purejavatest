package com.kute.util.cache.redis.hash;

import java.util.ResourceBundle;

public class HashRedisConfig {
    private static final String DEFAULT_REDIS_PROPERTIES = "hashRedis";
    private static ResourceBundle REDIS_CONFIG = ResourceBundle.getBundle(DEFAULT_REDIS_PROPERTIES);

    public static String getConfigProperty(String key) {
        return REDIS_CONFIG.getString(key);
    }
}
