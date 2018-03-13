package com.kute.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by kute on 2018-02-08 16:51
 */
public class ForEachTest {

    @Test
    public void test() {
        Map<String, Integer> itemMap = new HashMap<>();
        itemMap.put("A", 10);
        itemMap.put("B", 20);
        itemMap.put("C", 30);
        itemMap.put("D", 40);
        itemMap.put("E", 50);
        itemMap.put("F", 60);

        itemMap.forEach((k, v) -> System.out.println(k + v));

        List<String> itemList = new ArrayList<>();
        itemList.add("A");
        itemList.add("B");
        itemList.add("C");
        itemList.add("D");
        itemList.add("E");

        itemList.forEach(System.out::println);

        itemList.forEach(v -> System.out.println(v));

        itemList.stream().filter(s -> !s.equals("B")).forEach(System.out::println);

    }

}
