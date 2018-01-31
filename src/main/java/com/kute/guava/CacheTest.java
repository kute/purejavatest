package com.kute.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CacheTest {

    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

    /**
     * 当缓存中元素被移除时触发
     */
    private static final RemovalListener<String , Integer> removalListener = removalNotification -> {
        logger.info("[key={}, value={}] is removed from cache", removalNotification.getKey(), removalNotification.getValue());
        System.out.println("=====");
    };

    private static final LoadingCache<String , Integer> CACHE = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(5, TimeUnit.SECONDS)
//            .expireAfterAccess(5, TimeUnit.SECONDS)
            .removalListener(removalListener)
            .recordStats()
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                    return 1;
                }
            });

    @Test
    public void test() throws Exception {

//        CACHE.get("ABCD");

        CACHE.put("ABCD", 4);


        TimeUnit.SECONDS.sleep(15L);
    }
}
