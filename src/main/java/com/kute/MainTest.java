package com.kute;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kute on 2017/10/14.
 */
public class MainTest {

    public static void main(String[] args) throws  Exception{


        List<Integer> list = Collections.emptyList();
        list.add(2);
        System.out.println(list);

    }

    public void test() {
        for (int i = 0; i < 10; i++) {
            if(i == 5) {
                throw new RuntimeException("");
            }
            System.out.println(i);
        }
    }
}
