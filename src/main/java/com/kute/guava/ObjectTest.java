package com.kute.guava;

import com.google.common.base.Defaults;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.kute.po.Book;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(ObjectTest.class);

    @Test
    public void test() {

        Assert.assertTrue(Objects.equal("a", "a"));

        LOGGER.info(String .valueOf(
                Objects.hashCode("a", "b")
        ));

        Book b1 = new Book(2, "kute", 1.0);
        Book b2 = new Book(1, "bai", 3.0);
        LOGGER.info("{}", b1.compareTo(b2));

        System.out.println(Integer.valueOf(1).equals(null));

        Assert.assertEquals(MoreObjects.firstNonNull(new Integer(8), new Integer(9)), new Integer(8));
        Assert.assertEquals(MoreObjects.firstNonNull(null, new Integer(9)), new Integer(9));

    }

    @Test
    public void test1() {

        // 默认值
        Integer defaultValue = Defaults.defaultValue(Integer.class);
        Assert.assertTrue(defaultValue == 0);

    }

}
