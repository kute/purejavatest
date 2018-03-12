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


        int n = 0;
        do {
            int y = (n++) * 10;
            System.out.println(y);
            if(y == 0) {
                break;
            } else {
                System.out.println("n");
            }
        } while (true);
        System.out.println(n);

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
