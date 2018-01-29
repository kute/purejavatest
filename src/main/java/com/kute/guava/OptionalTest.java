package com.kute.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kute on 2018/1/28.
 */
public class OptionalTest {

    @Test
    public void test() {

        Optional<String > optional = Optional.fromNullable("a");

        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(optional.get(), "a");
        Assert.assertNotNull(optional.orNull());


        Optional<Integer> op = Optional.absent();

        Assert.assertEquals(op.or(5), new Integer(5));
        Assert.assertNull(op.orNull());


        Optional<Integer> op2 = Optional.of(8);

        Assert.assertEquals(op2.get(), new Integer(8));

        Assert.assertEquals(MoreObjects.firstNonNull(new Integer(8), new Integer(9)), new Integer(8));
        Assert.assertEquals(MoreObjects.firstNonNull(null, new Integer(9)), new Integer(9));

        String s = " ";
        Assert.assertFalse(s.isEmpty());
        Assert.assertFalse(Strings.isNullOrEmpty(s));
        Assert.assertTrue(Strings.isNullOrEmpty(s.trim()));
        Assert.assertNull(Strings.emptyToNull(s.trim()));

    }

}
