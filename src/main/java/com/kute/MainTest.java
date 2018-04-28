package com.kute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kute.java8.design.Service;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by kute on 2017/10/14.
 */
public class MainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    public static void main(String[] args) throws  Exception{

//        System.out.println(DateUtils.parseDate("2018-06-31", "yyyy-MM-dd").getTime());
//
//        System.out.println(15111742 % 50);
//        System.out.println(21686733 % 50);
//
//        Object o = Service.getInstance().execute(2, count -> {
//            LOGGER.info("==========count:{}", count);
//            return (Integer)count + 1;
//        });
//        System.out.println(o);
//
//        Map<Long, Integer> map = Maps.newHashMap();
//        System.out.println(map.get(2L));
//
//        System.out.println(DateTime.now().plusDays(-1).withMillisOfDay(0).toDate());
//        System.out.println(738%200);

        List<List<Object>> apartmentList = Lists.newArrayList(
                Lists.newArrayList()
        );


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
