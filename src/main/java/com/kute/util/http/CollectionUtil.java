package com.kute.util.http;

import java.util.Map;

import org.springframework.util.CollectionUtils;

public class CollectionUtil {
    
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if(!CollectionUtils.isEmpty(map) && map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

}
