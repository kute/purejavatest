package com.kute.vavr;

import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * created by bailong001 on 2019/01/29 10:42
 */
public class CollectionTest {

    @Test
    public void test() {

        // java 8
        Integer value = Stream.of(1, 2, 3).reduce(0, (sum, e) -> sum + e);
        Assert.assertEquals(6, value.intValue());
        // 并行计算 第三个参数才起作用
        value = Stream.of(1, 2, 4, 3).parallel().reduce(10, (sum, e) -> {
            System.out.println(sum + "-" + e);
            return sum + e;
        }, (a, b) -> {
            System.out.println(a + "=" + b);
            return a - b;
        });
        Assert.assertEquals(-2, value.intValue());

        // vavr
        value = List.of(1, 2, 3).sum().intValue();
        Assert.assertEquals(6, value.intValue());

        // CharSeq
        CharSeq.of("a").replaceAll("a", "b")
                .transform(CharSeq::toUpperCase);

    }

}
