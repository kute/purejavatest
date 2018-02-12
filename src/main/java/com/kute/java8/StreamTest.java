package com.kute.java8;

import com.google.common.collect.Lists;
import com.kute.po.Book;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * created by kute on 2018-02-08 16:58
 */
public class StreamTest {

    @Test
    public void test() {

        List<String> lines = Arrays.asList("spring", "node", "mkyong");

        // 过滤
        List<String > filterList = lines.stream()
                .distinct()
                .skip(1)
                .filter(s -> !s.equals("node"))
                .limit(3)
                .collect(Collectors.toList());

        lines.stream()
                .filter(s -> s.startsWith("p"))
//                .findFirst()
                .findAny()
//                .orElse("nothing")
//                .ifPresent(s -> System.out.println());
                  .ifPresent(System.out::println)
                ;

        List<Book> list = Lists.newArrayList(
                new Book(1, "a"),
                new Book(2, "b"),
                new Book(3, "c")
        );

        // map 映射
        list.stream()
                .filter(book -> book.getId() > 1)
                .map(Book::getName)
                .collect(Collectors.toList());

        List<Integer> lengthList = lines.stream()
                .map(String::length)
                .collect(Collectors.toList());
        // [6, 4, 6]
        System.out.println(lengthList);

        // reduce
        int initValue = 1;
        int product = lengthList.stream()
                .reduce(initValue, (a, b) -> a + b);

        Assert.assertTrue(product == 17);

        product = lines.stream()
                .mapToInt(String::length)
                .sum();

        Assert.assertTrue(product == 16);

        int[] data = new int[]{1, 2, 3, 4, 5};
        IntStream intStream = Arrays.stream(data);
        intStream.sum();

        Stream<Integer> stream = Stream.of(1, 2, 3);
        stream.mapToInt(Integer::new).sum();

        //
        stream = Stream.iterate(1, n -> n + 10);
        System.out.println(stream.limit(20).collect(Collectors.toList()));

        // 词频统计
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");
        Map<String, Long> result = items.stream()
                .collect(
                        Collectors.groupingBy(Function.identity(), Collectors.counting())
                );
        System.out.println(result);
    }
}
