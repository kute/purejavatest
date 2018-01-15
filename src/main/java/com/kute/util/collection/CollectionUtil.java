package com.kute.util.collection;

import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.springframework.util.CollectionUtils;

public class CollectionUtil {
    
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if(!CollectionUtils.isEmpty(map) && map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

    /**
     *   {a=a, b=b}   to map format
     * @param origin
     * @return
     */
    public Map<String , String > mapString2Map(String origin) {
        if(!Strings.isNullOrEmpty(origin)) {
            origin = origin.substring(1, origin.length() - 1);
            return Splitter.on(",").trimResults().omitEmptyStrings().withKeyValueSeparator("=").split(origin);
        }
        return null;
    }

}
