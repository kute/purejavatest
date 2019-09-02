package com.kute.google.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * created by bailong001 on 2019/05/24 18:31
 *
 * 布隆过滤器测试
 * 1、若不存在，则一定不存在
 * 2、若存在，则可能不存在，有一定的误判率
 */
public class BloomFilterTest {

    public static void main(String[] args) {

        int size = 1000000;
        double fpp = 0.01d;

        // 创建一个 预计有 100w数据，且 误判率为 0.01的布隆过滤器
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

        // 填充数据
        IntStream.rangeClosed(1, 1000000).forEach(bloomFilter::put);

        // 测试误判率
        int start = 2000000, end = 3000000;
        AtomicInteger missCount = new AtomicInteger(0);
        IntStream.rangeClosed(start, end).forEach(i -> {
            if(bloomFilter.mightContain(i)) {
                missCount.incrementAndGet();
            }
        });
        double result = (double)missCount.get()/(end - start);
        System.out.println("误判率为：" + result);

    }

}
