package com.kute.util.cache.redisson;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by longbai on 2017年12月27日下午6:03:27
 **/
public class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public static Config getConfig(String fileLocation) {
        Config config = null;
        try {
            config = Config.fromJSON(new File(fileLocation));
        } catch (IOException e) {
            config = new Config();
            logger.error(Joiner.on(" ").join("Get config from parsing file[", fileLocation, "] error and use default config"), e);
        }
        return config;
    }

    public static JSONObject getConfig(Config config) throws IOException {
        if (null != config) {
            return JSONObject.parseObject(config.toJSON());
        }
        return null;
    }

}
