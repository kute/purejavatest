package com.kute.guava;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * created by bailong001 on 2019/02/18 18:38
 */
public class FutureTest {

    public static final ExecutorService coreThreadPoolExecutor = new ThreadPoolExecutor(20, 100,
            1, TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static final ListeningExecutorService pool = MoreExecutors.listeningDecorator(coreThreadPoolExecutor);

    public static ListenableFuture submit(Runnable runnable) {
        return pool.submit(runnable);
    }

    @Test
    public void test() {

        List<String> resultList = Lists.newArrayList();

        List<ListenableFuture<Object>> listenableFutureList = Lists.newArrayListWithCapacity(18);

        Stopwatch stopwatch = Stopwatch.createStarted();

        IntStream.rangeClosed(1, 5)
                .forEach(i -> listenableFutureList.add(submit(() -> resultList.add(sleepFunction(i)))));

        try {
            // 会忽略异常数据
            Futures.successfulAsList(listenableFutureList).get();
            // 遇到异常会终止
//            Futures.allAsList(listenableFutureList).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resultList);
        System.out.println("take:" + stopwatch.elapsed(TimeUnit.SECONDS) + "s");

    }

    private String sleepFunction(Integer data) {
        if (3 == data) {
//            throw new RuntimeException("RuntimeException");
        }
        try {
            TimeUnit.SECONDS.sleep(data);
        } catch (Exception e) {

        }
        return data + "-";
    }

}
