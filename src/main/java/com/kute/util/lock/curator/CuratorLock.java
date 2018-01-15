package com.kute.util.lock.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

/**
 * Created by kute on 2018/1/15.
 * <p>
 * 基于zookeeper 的分布式一致性算法的分布式锁
 */
public class CuratorLock {

    public static void main(String[] args) throws Exception {

        CuratorCreator creator = new CuratorCreator("127.0.0.1:2181");

        CuratorFramework client = creator.getClient();

        String path = "/lock";

        if(!creator.exists(path)) {
            creator.createOrUpdateNode(path, String.valueOf(System.currentTimeMillis()));
        }

        Thread t1 = new Thread(() -> {
            try {
                doWithLock(client, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                doWithLock(client, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    private static void doWithLock(CuratorFramework client, String path) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, path);
        if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {
            try {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(5000L);
                System.out.println(Thread.currentThread().getName() + " release lock");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
