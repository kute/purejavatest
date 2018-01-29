package com.kute.guava;

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

        LOGGER.info("{}", Objects.toStringHelper(this).add("name", "kute").toString());
        LOGGER.info("{}", Objects.toStringHelper("testObj").add("name", "kute").toString());


        Book b1 = new Book(2, "kute", 1.0);
        Book b2 = new Book(1, "bai", 3.0);
        LOGGER.info("{}", b1.compareTo(b2));

    }

}
