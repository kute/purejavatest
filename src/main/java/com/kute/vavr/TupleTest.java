package com.kute.vavr;

import io.vavr.*;
import io.vavr.collection.Seq;
import org.junit.Test;

/**
 * created by bailong001 on 2019/01/22 11:49
 */
public class TupleTest {

    @Test
    public void test1() {

        Tuple3<String, Integer, Integer> t3 = Tuple.of("java", 8, Tuple.MAX_ARITY);

        System.out.println(t3.arity()); // 元素数量
        System.out.println(t3._1);
        System.out.println(t3._2);
        System.out.println(t3._3);
        System.out.println(t3.toString());

        t3.map((e1, e2, e3) -> new Tuple3<>(e1 + "8", e2 + 1, e3));
        t3.map(e1 -> e1 + "8", e2 -> e2 + 1, e3 -> e3);
        t3.map1(e1 -> e1 + "8");
        t3.map2(e1 -> e1 + 1);

        Tuple4 t4 = t3.append("t4");
        System.out.println(t4.toString());
        System.out.println(t3.toString());

        Tuple7 t7 = t4.concat(t3);
        System.out.println(t7.toString());

        Seq s1 = t3.toSeq();

        String applyResult = t3.apply((e1, e2, e3) -> e1 + e2 + e3);
        System.out.println(applyResult);

    }

}
