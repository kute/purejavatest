package com.kute.google.guava;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.kute.po.Book;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Ordering.from;

/**
 * Created by kute on 2018/1/28.
 */
public class OrderingTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderingTest.class);

    private final List<Integer> list = Lists.newArrayList(2,5,3,1,null,56,7);

    private final List<Book> bookList = Lists.newArrayList(
            new Book(1, "kute", 100.0),
            new Book(5, "bai", 200.0),
            new Book(3, "li", 300.0),
            new Book(10, "zh", 150.0),
            new Book(7, "sun", 210.0)
    );

    @Test
    public void test() {

        logger.info(list.toString());
        logger.info(Ordering.natural().nullsLast().greatestOf(list.iterator(), list.size()).toString());
        logger.info(Ordering.natural().nullsFirst().greatestOf(list.iterator(), list.size()).toString());

        // 按 price 排序
        Ordering<Book> order = Ordering.natural().nullsFirst().onResultOf((book -> book.getPrice()));

        logger.info(order.greatestOf(FluentIterable.from(bookList), bookList.size()).toString());

        logger.info("{}", order.max(bookList.iterator()));

        // <>
        Ordering.<Book>from(((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()))).greatestOf(FluentIterable.from(bookList), bookList.size());
        Ordering.<Book>from(Comparator.comparing(Book::getId)).greatestOf(FluentIterable.from(bookList), bookList.size());

        // 找到非0返回
        bookList.sort(Comparator.comparing((Book b) -> b.getId()).thenComparing(b -> b.getName()).thenComparing(b -> b.getPrice()));
    }
}
