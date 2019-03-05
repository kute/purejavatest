package com.kute.guava;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
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

    public static ListenableFuture submitRunnable(Runnable runnable) {
        return pool.submit(runnable);
    }

    public static <T> ListenableFuture<T> submitCallable(Callable<T> callable) {
        return pool.submit(callable);
    }

    @Test
    public void test() {

        List<String> resultList = Lists.newArrayList();

        List<ListenableFuture<Object>> listenableFutureList = Lists.newArrayListWithCapacity(18);

        Stopwatch stopwatch = Stopwatch.createStarted();

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    ListenableFuture listenableFuture = submitRunnable(() -> resultList.add(sleepFunction(i)));
                    listenableFutureList.add(listenableFuture);
                });

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

    @Test
    public void testListenerFuture() throws InterruptedException {

        ListenableFutureTask<String> task = ListenableFutureTask.create(() -> {
            TimeUnit.SECONDS.sleep(3);
            return "test";
        });

        CountDownLatch countDownLatch = new CountDownLatch(1);

        task.addListener(() -> {
            try {
                System.out.println("done with result:" + task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }, coreThreadPoolExecutor);

        new Thread(task).start();

        countDownLatch.await();

    }

    @Test
    public void test2() throws InterruptedException {
        ListenableFuture<Object> listenableFuture = submitCallable(() -> sleepFunction(3));

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("addCallback result:" + result);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("addCallback failure:" + t);
                countDownLatch.countDown();
            }
        }, coreThreadPoolExecutor);

        countDownLatch.await();

    }

    /**
     * SettableFuture
     */
    @Test
    public void test3() {
        SettableFuture settableFuture = SettableFuture.create();

    }

}
