package com.kute.vavr;

import io.vavr.*;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;

/**
 * created by bailong001 on 2019/01/28 10:55
 */
public class FunctionTest {

    @Test
    public void test() {

        Function2<Integer, String, Integer> function2 = (a, b) -> a + Integer.valueOf(b);
        int sum = function2.apply(1, "1");

        Assert.assertEquals(2, sum);

        // partial function
        Function1<String, Integer> function1 = function2.apply(1);

        sum = function1.apply("1");

        Assert.assertEquals(2, sum);

        Function2<Integer, String, Integer> function22 = Function2.of(this::methodWhichAccepts2Parameters);

        sum = function22.apply(1, "1");

        Assert.assertEquals(2, sum);

    }

    private Integer methodWhichAccepts2Parameters(Integer a, String b) {
        return a + Integer.valueOf(b);
    }

    /**
     * Composition  组成
     */
    @Test
    public void test1() {

        Function1<Integer, Integer> f = a -> a + 1;
        Function1<Integer, Integer> g = a -> a * 2;

        // after:  先 f 后  g
        Function1<Integer, Integer> fg = f.andThen(g);

        int value = fg.apply(5);

        Assert.assertEquals(12, value);

        // before : 先 f 后 g
        Function1<Integer, Integer> gf = g.compose(f);

        value = gf.apply(5);

        Assert.assertEquals(12, value);

    }

    /**
     * lift
     * 1. A lifted function returns None instead of throwing an exception, if the function is invoked with disallowed input values.
     * 2. A lifted function returns Some, if the function is invoked with allowed input values.
     */
    @Test
    public void test2() {

        Function2<Integer, Integer, Integer> f = (a, b) -> a / b;

        Function2<Integer, Integer, Option<Integer>> fo = Function2.lift(f);

        Assert.assertFalse(fo.apply(1, 0).isDefined());
        Assert.assertTrue(fo.apply(1, 0).isEmpty());
        Assert.assertTrue(fo.apply(1, 1).isDefined());

        Function2<Integer, Integer, Option<Integer>> go = Function2.lift(this::divide);

        Assert.assertFalse(go.apply(1, 0).isDefined());
        Assert.assertTrue(go.apply(1, 0).isEmpty());
        Assert.assertTrue(go.apply(1, 1).isDefined());

        Function2<Integer, Integer, Try<Integer>> ko = Function2.liftTry(this::divide);

        Assert.assertFalse(ko.apply(1, 0).isSuccess());
        Assert.assertTrue(ko.apply(1, 0).isEmpty());
        Assert.assertTrue(ko.apply(1, 0).isFailure());
        Assert.assertEquals(0, ko.apply(1, 0).getOrElseGet(throwable -> {
            if ("IllegalArgumentException".equals(throwable.getClass().getSimpleName())) {
                return 0;
            }
            return -1;
        }).intValue());
        Assert.assertEquals(1, ko.apply(1, 1).get().intValue());
    }

    private Integer divide(Integer a, Integer b) {
        if (b == 0) {
            throw new IllegalArgumentException();
        }
        return a / b;
    }

    /**
     * 部分应用 和  柯里化
     */
    @Test
    public void test3() {

        Function4<Integer, Integer, Integer, Integer, Integer> sum = (a, b, c, d) -> a + b + c + d;

        // 部分应用

        Function3<Integer, Integer, Integer, Integer> f = sum.apply(1);

        int value = f.apply(2, 3, 4);
        Assert.assertEquals(10, value);

        Function2<Integer, Integer, Integer> g = sum.apply(1, 2);

        value = g.apply(3, 4);
        Assert.assertEquals(10, value);

        // 柯里化

        Function1<Integer, Function1<Integer, Function1<Integer, Integer>>> ff = sum.curried().apply(1);

        value = ff.apply(2).apply(3).apply(4);
        Assert.assertEquals(10, value);
    }

    /**
     * Memoization  ： 记忆化，即 缓存
     */
    @Test
    public void test4() {

        Function0<Double> f = Function0.of(Math::random).memoized();

        Assert.assertTrue(f.isMemoized());

        double v1 = f.apply();

        double v2 = f.apply();

        Assert.assertEquals(v1, v2, 0D);

    }

}
