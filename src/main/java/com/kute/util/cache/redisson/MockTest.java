package com.kute.util.cache.redisson;

import com.kute.util.cache.redisson.base.SingleServerClientCreator;
import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.api.RFuture;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by longbai on 2017/12/28.
 */
public class MockTest {

    private static final Logger logger = LoggerFactory.getLogger(MockTest.class);

    static RedissonClient client = SingleServerClientCreator.getInstance().newClient(null);

    public static void main(String[] args) {
        try {
            basic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void basic() {

        RFuture<Object> future = client.getBucket("last-url-id").getAndSetAsync(5);
        if(future.awaitUninterruptibly(3L, TimeUnit.MILLISECONDS)) {
            try {
                logger.info(String.valueOf(future.get()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        RKeys keys = client.getKeys();

        keys.getKeys().forEach(acceptKey -> {
            logger.info(acceptKey);
        });

        keys.count();


    }

}
