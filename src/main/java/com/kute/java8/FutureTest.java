package com.kute.java8;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * created by bailong001 on 2019/02/16 19:48
 * <p>
 * CompletableFuture
 * <p>
 * 1. supplyAsync: 用于有返回值的任务
 * 2. runAsync: 用于没有返回值的任务
 * 3. allOf: 是等待所有任务成功完成
 * 4. anyOf: 是只要有一个任务完成
 *
 * @see com.kute.google.guava.FutureTest   guava future
 */
public class FutureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureTest.class);

    @Test
    public void test() throws InterruptedException, ExecutionException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        CompletableFuture
                // 异步又返回值的任务
                .supplyAsync(() -> sleepFunction("4"))
                // 在当前线程，类似map
                .thenApply(data -> data + "thenApply")
                // 异步执行，类似map
                .thenCombine(CompletableFuture.completedFuture("myv"), (d1, d2) -> {
                    LOGGER.info("thenCombine d1={}, d2={}", d1, d2);
                    return Joiner.on("==").join(d1, d2);
                })
                .thenApplyAsync(data -> data + "thenApplyAsync")
                // 无返回值 消费, 接收上一阶段的输出作为本阶段的输入
                .thenAccept(result -> {
                    LOGGER.info("thenAccept:{}", result);
                })
                .thenAcceptAsync(result -> {
                    LOGGER.info("thenAcceptAsync:{}", result);
                }, MoreExecutors.directExecutor())
                // 不关心前一阶段的输出
                .thenRun(() -> {
                    LOGGER.info("thenRun");
                })
                .whenComplete((result, error) -> {
                    if (null == error) {
                        LOGGER.info("whenComplete normal:{}", result);
                    } else {
                        LOGGER.info("whenComplete error:{}", error.getClass().getName());
                    }
                    countDownLatch.countDown();
                })
                .get();

        countDownLatch.await();
    }

    /**
     * @throws InterruptedException
     */
    @Test
    public void testCompletableFuture() throws InterruptedException {

        List<String> list = Lists.newArrayList("1", "2", "3", "4");
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        Stopwatch stopwatch = Stopwatch.createStarted();
        list.parallelStream().map(data -> CompletableFuture.supplyAsync(() -> sleepFunction(data)))
                .map(future -> future.thenApply(d -> d + "s"))
                .map(future -> future.whenComplete((result, throwable) -> {
                    if (null != throwable) {
                        LOGGER.info("异常发生:{}", throwable.getClass().getName());
                    } else {
                        LOGGER.info("完成：{}", result);
                    }
                    countDownLatch.countDown();
                }))
                .map(future -> future.getNow("还未完成"))
                .forEach(LOGGER::info);
        countDownLatch.await();
        LOGGER.info("take total:{}", stopwatch.elapsed(TimeUnit.SECONDS));
    }

    private String sleepFunction(String data) {
        if ("3".equalsIgnoreCase(data)) {
            throw new RuntimeException("RuntimeException");
        }
        try {
            TimeUnit.SECONDS.sleep(Integer.parseInt(data));
        } catch (Exception e) {

        }
        return data + "-";
    }

    private String sleep(String data) {
        try {
            TimeUnit.SECONDS.sleep(Integer.parseInt(data));
        } catch (Exception e) {

        }
        return data + "-";
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // 告诉completableFuture任务发生异常了
                completableFuture.completeExceptionally(e);
            }
            // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();
        // 获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);

    }

    /**
     * 并行方式
     */
    @Test
    public void test2() throws Exception {

        LOGGER.info("=======");
        // method-1
        IntStream.rangeClosed(1, 5).boxed().parallel().forEach(i -> {
            LOGGER.info("==={}", i);
            sleep(i.toString());
        });

        //method-2
        LOGGER.info("********");
        List<CompletableFuture<String>> list = Lists.newArrayList(1, 2, 4)
                .stream()
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    LOGGER.info("******={}", i);
                    sleep(i.toString());
                    return i + "=";
                }))
                .collect(Collectors.toList());
        CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]))
//                .get()
                .join();
        LOGGER.info("done");
    }

    /**
     * 更改默认的parallelStream forkjoin大小
     */
    @Test
    public void test3() throws InterruptedException, ExecutionException, TimeoutException {
        // method-1
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "300");

        // method-2: 使用自己的forkjoin池
        ForkJoinPool forkJoinPool = new ForkJoinPool(100);
        forkJoinPool.submit(() -> {
            return Lists.newArrayList(1, 2, 3)
                    .parallelStream()
                    .map(Integer::byteValue)
                    .collect(Collectors.toList());
        }).get(2, TimeUnit.SECONDS);
    }

}
