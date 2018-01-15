package com.kute;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;

/**
 * Created by kute on 2017/10/14.
 */
public class MainTest {

    public static void main(String[] args) throws  Exception{

        Map<String, String> map = Maps.asMap(Sets.newHashSet("a", "b"), new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        });
        String mapString = map.toString();
        System.out.println(mapString);
        String fs = mapString.substring(1, mapString.length() - 1);
        Map result = Splitter.on(",").trimResults().omitEmptyStrings().withKeyValueSeparator("=").split(fs);
        System.out.println(result);

    }
}
