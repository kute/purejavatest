package com.kute.util.cache.redisson;

import com.kute.util.cache.redisson.base.SingleServerClientCreator;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;

/**
 * Created by longbai on 2017/12/28.
 */
public class MockTest {

    public static void main(String[] args) {
        try {
            RedissonClient client = SingleServerClientCreator.getInstance().newClient();
            RFuture<Object> future = client.getBucket("last-url-id").getAndSetAsync(5);
            Object o = future.handle((obj, exception) -> obj);
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
