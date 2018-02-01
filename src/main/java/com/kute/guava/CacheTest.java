package com.kute.guava;

import com.google.common.cache.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheTest {

    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

    /**
     * 当缓存中元素被移除时触发
     */
    private static final RemovalListener<String , AtomicInteger> removalListener = removalNotification -> {
        logger.info("[key={}, value={}] is removed from cache", removalNotification.getKey(), removalNotification.getValue());
    };

    /**
     * new builder from spec
     */
    String spec = "maximumSize=10000,expireAfterWrite=10m";
    CacheBuilder<Object, Object> CACHE_BUILD = CacheBuilder.from(spec);

    /**
     * new builder from spec
     */
    CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
    CacheBuilder<Object, Object> BUILD = CacheBuilder.from(cacheBuilderSpec);


    private static final LoadingCache<String , AtomicInteger> CACHE = CacheBuilder.newBuilder()
            .maximumSize(10)
            // 5s 内未再创建或者替换 自动失效移除
//            .expireAfterWrite(5, TimeUnit.SECONDS)
            // 5s 内无读写自动失效移除
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .removalListener(removalListener)
            .recordStats()
            .build(
                    // 默认值
                    new CacheLoader<String, AtomicInteger>() {
                        @Override
                        public AtomicInteger load(String s) throws Exception {
                            return new AtomicInteger(0);

                    }
            });

    @Test
    public void test() throws Exception {

        String key = "docId";

        Assert.assertEquals(CACHE.get(key).get(), 0);

        Assert.assertEquals(CACHE.get(key).addAndGet(1), 1);

        Assert.assertEquals(CACHE.get(key).get(), 1);


        // 缓存统计：命中率，逐出个数 等
        logger.info(CACHE.stats().toString());
        // concurrentMap
        logger.info(CACHE.asMap().toString());


        TimeUnit.SECONDS.sleep(5);

        Assert.assertEquals(CACHE.get(key).get(), 0);

        logger.info(CACHE.stats().toString());

    }
}
