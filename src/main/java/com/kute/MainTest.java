package com.kute;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Booleans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kute on 2017/10/14.
 */
public class MainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    public static void main(String[] args) throws  Exception{

        String message = "权限操作 %s 不存在";
        System.out.println(String.format(message,"aa"));

        new MainTest().test2(3);

    }


    public void test2(int i) {
        if(i >0) {
            try {
                test();
            } catch(Exception e) {
                throw new NullPointerException("null point");
            }
        }
    }


    public void test() {
        throw new RuntimeException("runtime");
    }
}
