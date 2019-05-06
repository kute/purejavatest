package com.kute.google.guava;

import com.google.common.cache.*;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheTest {

    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

    /**
     * 当缓存中元素被移除时触发(同步调用，耗时操作 应装饰为异步）
     */
    private static final RemovalListener<String, AtomicInteger> removalListener = removalNotification -> {
        logger.info("[key={}, value={}] is removed from cache", removalNotification.getKey(), removalNotification.getValue());
    };

    /**
     * 装饰为 异步调用
     */
    private static final RemovalListener<String, AtomicInteger> asyncRemovalListener = RemovalListeners.asynchronous(removalListener, Executors.newCachedThreadPool());

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

    static ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    private static final LoadingCache<String, AtomicInteger> CACHE = CacheBuilder.newBuilder()
//    设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            .maximumSize(10)
//            设置缓存容器的初始容量为10
            .initialCapacity(5)
            // 5s 内未再创建或者替换 自动失效移除
//            .expireAfterWrite(5, TimeUnit.SECONDS)
            // 5s 内无读写自动失效移除
            .expireAfterAccess(5, TimeUnit.SECONDS)
//            定时刷新缓存
            .refreshAfterWrite(10, TimeUnit.SECONDS)
            .removalListener(removalListener)
//            .removalListener(asyncRemovalListener)
            .recordStats()
            .build(
                    // 当缓存未命中时，通过此重新设置缓存，注意：此不能返回 null
                    new CacheLoader<String, AtomicInteger>() {
                        @Override
                        public AtomicInteger load(String s) throws Exception {
                            return new AtomicInteger(0);
                        }

                        // 缓存刷新时调用，这里使用线程池异步刷新
                        @Override
                        public ListenableFuture<AtomicInteger> reload(String key, AtomicInteger oldValue) throws Exception {
                            return executorService.submit(() -> {
                                return new AtomicInteger(0);
                            });
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

        // 如果缓存存在，则返回； 否则 回调函数 计算结果后缓存，并返回
        CACHE.get(key, () -> new AtomicInteger(0));

        // 清除 key
        CACHE.invalidate(key);
        CACHE.invalidateAll();
        CACHE.invalidateAll(Iterables.cycle("a", "b"));

//        刷新缓存，即重新取缓存数据，更新缓存
        CACHE.refresh(key);

        CACHE.cleanUp();

        // 当 CacheLoader 中未声明定义任何异常时使用，可能抛异常
        CACHE.getUnchecked(key);


        TimeUnit.SECONDS.sleep(5);

        Assert.assertEquals(CACHE.get(key).get(), 0);

        logger.info(CACHE.stats().toString());

    }
}
