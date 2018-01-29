package com.kute.guava;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.ObjIntConsumer;

public class CollectionsTest {

    @Test
    public void test() {

        // default sie = 10
        List<Integer> data = Lists.newArrayList();
        data = new ArrayList<>(2);
        data = Lists.newArrayListWithCapacity(2);

        data.add(1);
        data.add(2);
        data.add(3);
        System.out.println(data);

        System.out.println(Lists.asList(1, 2, new Integer[]{3, 4}));

        // 扩容方式
        // size = Ints.saturatedCast(5L + (long)arraySize + (long)(arraySize / 10))
        data = Lists.newArrayListWithExpectedSize(2);

        data.add(1);
        data.add(3);
        data.add(4);
        System.out.println(data);

        System.out.println(Lists.reverse(data));
        System.out.println(Lists.partition(data, 1));

    }

    @Test
    public void test1() {
        // 连接
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6, 3)
        );

        // 频率
        System.out.println(Iterables.frequency(concatenated, 3));

        // 分割： 每组 4 个元素
        Iterable<List<Integer>> iter = Iterables.partition(concatenated, 4);
        System.out.println(Lists.newArrayList(iter));

        System.out.println(Iterables.get(concatenated, 2));
        System.out.println(Iterables.getFirst(concatenated, 3));
        System.out.println(Iterables.getLast(concatenated, 2));

        List<Integer> data = Ints.asList(2, 3, 5, 6, 4, 1, 3);
        // 元素比较，顺序元素一致
        System.out.println(Iterables.elementsEqual(concatenated, FluentIterable.from(data)));

        concatenated = Iterables.unmodifiableIterable(concatenated);
        concatenated.forEach(ele -> System.out.print(ele));
    }

    @Test
    public void test2() {

        Set<Integer> set = Sets.newHashSet(1, 3, 3, 4, 5, 5, 5, 9);
        Set<Integer> set2 = Sets.newHashSet(4, 9);
        System.out.println(set);

        System.out.println(Sets.union(set, set2));

        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

        // contains "two", "three", "seven"
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);

        // I can use intersection as a Set directly, but copying it can be more efficient if I use it a lot.
        System.out.println(intersection.immutableCopy());

        // 允许重复，但不保证有序: 词频
        Multiset<Integer> multiset = HashMultiset.create();
        multiset.addAll(Ints.asList(1, 2, 3, 3, 3, 4, 5, 5, 8, 9));
        System.out.println(multiset);
        System.out.println(multiset.count(5));
        multiset.forEach(System.out::print);
        multiset.forEachEntry((ele, count) -> {
            System.out.println(ele + ":" + count);
        });

    }

}
