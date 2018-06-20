package com.kute.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Enums;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kute.enums.GoodsSourceEnum;
import com.kute.enums.MtaCustomResourceTypeEnum;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        List<Long> data = Lists.newArrayList(1L, 1L, 2L, 3L, 3L);
        List<Long> var = Lists.newArrayList(3L, 4L, 5L);

        List<List<Long>> l1 = Lists.cartesianProduct(data, var);
        System.out.println(l1);

        System.out.println(Sets.newHashSet(l1));

        Map<Long, Boolean> map = Maps.asMap(Sets.newHashSet(data), new Function<Long, Boolean>() {
            @Override
            public Boolean apply(Long input) {
                return Boolean.TRUE;
            }
        });
        Map<Long, Boolean> map1 = Maps.newHashMap(map);
        try {
            map1.put(8L, true);
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(Lists.newArrayList(MtaCustomResourceTypeEnum.values()));

        System.out.println(JSONObject.toJSONString(MtaCustomResourceTypeEnum.values()));

        System.out.println(GoodsSourceEnum.findByValue(9));

        DateTime test = DateTime.now();

        Interval interval = new Interval(new DateTime("2018-06-11T00:00:00"), new DateTime("2018-06-18T00:00:00"));
        System.out.println(interval.contains(DateTime.now()));
        System.out.println(DateTime.now().isBefore(new DateTime("2018-06-18T00:00:00")));

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(""));
            String result = IOUtils.toString(fis, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
        }

    }

}
