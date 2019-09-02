package com.kute.vavr;

import io.vavr.Lazy;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;

/**
 * created by bailong001 on 2019/01/28 11:42
 */
public class ValueTest {

    @Test
    public void testOption() {

        Option<Integer> o = Option.of(1);
        Assert.assertTrue(o.isDefined());
        Assert.assertFalse(o.isEmpty());

        Option<Integer> p = Option.none();
        Assert.assertTrue(p.isEmpty());
        Assert.assertFalse(p.isDefined());

    }

    @Test
    public void testMatch() {
        String v = "1";
        Match(v).of(
                Case($(isIn("1", "2")), () -> {
                    System.out.println("iiiii");
                    return "test";
                })
        );
    }

    @Test
    public void testTry() {

        Integer result = Try.of(this::bunchOfWork)
                .recover(x -> Match(x).of(
                        Case($(instanceOf(IllegalArgumentException.class)), this::somethingWithException),
                        Case($(instanceOf(IllegalStateException.class)), this::somethingWithException)
                ))
                .getOrElse(-1);
        System.out.println(result);


        String v = "v";
        Function<Object, Object> function = x -> Match(x).of(
                Case($(allOf(xx -> !"a".equals(xx), xx -> !"b".equals(xx))), "allOf"),
                Case($(anyOf("a"::equals, "v"::equals)), "anyOf"),
                Case($(is("b")), "is"),
                Case($(isIn("a", "b")), "isIn"),
                Case($(isNull()), "isNull"),
                Case($(isNotNull()), "isNotNull"),
                Case($(instanceOf(RuntimeException.class)), () -> "RuntimeException"),
                Case($(), () -> "default")
        );
        System.out.println(function.apply(v));

        Try.of(this::throwException)
                .onFailure(throwable -> {
                    System.out.println(throwable.getClass().getSimpleName());
                })
                .recover(x -> 2)
                .andThenTry(this::throwException)
                .recover(x -> 3)
                .andThen((Consumer<Object>) System.out::println)
                .andFinally(() -> System.out.println("done"));


        Try.of(this::throwException)
                .onFailure(ex -> System.out.println(ex.getClass().getSimpleName()))
                .andThenTry(() -> System.out.println("done2"));

        System.out.println(Try.of(this::throwException).recover(ex -> 2).getOrElse(1));


//        Try.ofCallable(this::throwException)
//                .onFailure(ex -> System.out.println("======="))
//                .getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);


        System.out.println(Try.ofCallable(this::throwException)
                .onFailure(throwable -> {
                    throw new RuntimeException(throwable);
                })
                .getOrElse(99));

    }

    public Object throwException() {
        throw new RuntimeException();
    }

    private Integer somethingWithException(RuntimeException t) {
        if (null != t) {
            if (t instanceof IllegalArgumentException) {
                return -2;
            }
            if (t instanceof IllegalStateException) {
                return -3;
            }
        }
        return -4;
    }

    private Integer bunchOfWork() {
        int v = RandomUtils.nextInt(3);
        System.out.println("bunchOfWork:" + v);
        if (v == 0) {
            throw new IllegalArgumentException();
        }
        if (v == 1) {
            throw new IllegalStateException();
        }
        return v;
    }

    /**
     * Lazy is memoizing
     */
    @Test
    public void testLazy() {

        Lazy<String> lazy = Lazy.of(() -> "1");

        Assert.assertFalse(lazy.isEvaluated());
        Assert.assertEquals("1", lazy.get());
        Assert.assertTrue(lazy.isEvaluated());

        CharSequence val = Lazy.val(() -> "1", CharSequence.class);
        Assert.assertEquals("1", val.toString());
    }

    @Test
    public void testEither() {
//        Either.Right<String, Integer> right = Right(8);
        Either<String, Integer> right = Right(8);
        Assert.assertTrue(right.isRight());
        Assert.assertFalse(right.isLeft());
        Assert.assertEquals(8, right.get().intValue());

        Either.Left<String, Integer> left = Left("0");
        Assert.assertTrue(left.isLeft());
        Assert.assertFalse(left.isRight());
        Assert.assertEquals("0", left.getLeft());

        Either either = eval();
        if (either.isLeft()) {
            // exception deal
            System.out.println(either.getLeft());
        } else {
            System.out.println(either.get());
        }

    }

    private Either<String, Integer> eval() {
        int v = RandomUtils.nextInt(3);
        System.out.println("eval:" + v);
        if (v < 2) {
            return Left("IllegalArgumentException");
        }
        return Right(v);
    }

}
