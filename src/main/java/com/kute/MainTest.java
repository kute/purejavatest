package com.kute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kute.util.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kute on 2017/10/14.
 */
public class MainTest {

    public static void main(String[] args) throws  Exception{

        System.out.println(DateUtils.parseDate("2018-06-31", "yyyy-MM-dd").getTime());
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
