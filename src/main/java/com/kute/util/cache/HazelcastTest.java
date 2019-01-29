package com.kute.util.cache;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Queue;

/**
 * Created by kute on 2017/5/6.
 *
 * Hazelcast是一个高度可扩展的数据分发和集群平台。特性包括：
 * 提供java.util.{Queue, Set, List, Map}分布式实现。
 * 提供java.util.concurrency.locks.Lock分布式实现。
 * 提供java.util.concurrent.ExecutorService分布式实现。
 * 提供用于一对多关系的分布式MultiMap。
 * 提供用于发布/订阅的分布式Topic（主题）。
 * 通过JCA与J2EE容器集成和事务支持。
 * 提供用于安全集群的Socket层加密。
 * 支持同步和异步持久化。
 * 为Hibernate提供二级缓存Provider 。
 * 通过JMX监控和管理集群。
 * 支持动态HTTP Session集群。
 * 利用备份实现动态分割。支持动态故障恢复。
 */
public class HazelcastTest {

    public static void main(String[] args) {
        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<Integer, String> mapCustomers = instance.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());

        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: "+ queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());




    }
}
