package com.kute.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionsTest {

    /**
     * Lists
     */
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
        data.addAll(Ints.asList(3, 4, 5));

        System.out.println(data);

        System.out.println(Lists.reverse(data));
        System.out.println(Lists.partition(data, 1));

        // n-array
        List<List<Object>> result = Lists.cartesianProduct(ImmutableList.of(
                ImmutableList.of(1, 2),
                ImmutableList.of("A", "B", "C"),
                ImmutableList.of("I", "II", "III")
        ));
        System.out.println(result.size());

        // 写时复制，并发读写，读写分离
        List<Integer> list = Lists.newCopyOnWriteArrayList();

        List<String > slist = Lists.transform(result, eleList -> Joiner.on("-").join(eleList));
        System.out.println(slist);

    }

    /**
     * Iterables
     */
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

        // 元素比较，元素顺序需要一致
        System.out.println(
                Iterables.elementsEqual(Ints.asList(1, 2, 3), Ints.asList(2, 1, 3))
        );

        Collection<Integer> collection = Lists.newArrayList(concatenated);
        Iterables.removeIf(collection, ele -> ele > 4);
        System.out.println(collection);

        //多个条件组合

        concatenated = Iterables.unmodifiableIterable(concatenated);
        // 此操作不允许
        // Iterables.removeAll(concatenated, Ints.asList(2));
        concatenated.forEach(System.out::print);

    }

    /**
     * Sets
     */
    @Test
    public void test2() {

        Set<Integer> set = Sets.newHashSet(1, 3, 3, 4, 5, 5, 5, 9);
        Set<Integer> set2 = Sets.newHashSet(4, 9);
        System.out.println(set);

        // 并集
        System.out.println(Sets.union(set, set2));

        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");

        // 交集
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        // I can use intersection as a Set directly, but copying it can be more efficient if I use it a lot.
        System.out.println(intersection.immutableCopy());

        // 差集
        System.out.println(Sets.difference(primes, wordsWithPrimeLength).immutableCopy());

        // 对称差集
        System.out.println(Sets.symmetricDifference(primes, wordsWithPrimeLength).immutableCopy());

        // list 允许重复且有序；set不允许重复且无序；multiset允许重复，但无序（词频统计）
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
