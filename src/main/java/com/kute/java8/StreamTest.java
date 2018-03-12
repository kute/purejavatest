package com.kute.java8;

import com.google.common.collect.Lists;
import com.kute.po.Book;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

        Stream.generate(Math::random).limit(5).forEachOrdered(System.out::println);

        // 词频统计
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");
        Map<String, Long> result = items.stream()
                .collect(
                        Collectors.groupingBy(Function.identity(), Collectors.counting())
                );
        System.out.println(result);

        // flatMap
        List<List<String >> lists = Lists.newArrayList(
                Lists.newArrayList("apple", "orange"),
                Lists.newArrayList("papaya", "banana")
        );
        // [apple, orange, papaya, banana]
        List<String > flatResult = lists.stream().flatMap(strings -> strings.stream()).collect(Collectors.toList());
        System.out.println(flatResult);
        // [a, p, p, l, e, o, r, a, n, g, e, p, a, p, a, y, a, b, a, n, a, n, a]
        flatResult = lists.stream().flatMap(strings -> strings.stream()).flatMap(s -> Arrays.stream(s.split(""))).collect(Collectors.toList());
        System.out.println(flatResult);
        List<String[]> aryResult = lists.stream().flatMap(strings -> strings.stream()).map(s -> s.split("")).collect(Collectors.toList());
        aryResult.forEach(ary -> System.out.println(Arrays.asList(ary)));

    }

    public static void main(String[] args) throws IOException {
        List<String > strList = Lists.newArrayList(
                "a,b,c,d",
                "b,d,e,a,a"
        );
//        Files.readAllLines(Paths.get(""), StandardCharsets.UTF_8);
        Set<Map.Entry<String, Long>> s = strList.parallelStream().flatMap(line -> Arrays.stream(line.split(","))).map(word -> new AbstractMap.SimpleEntry<>(word, 1)).collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.counting())).entrySet();
        System.out.println(s);
        System.out.println(strList.parallelStream().flatMap(line -> Arrays.stream(line.split(","))).collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum)));
    }

}
