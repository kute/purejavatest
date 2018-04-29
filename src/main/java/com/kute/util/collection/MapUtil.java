package com.kute.util.collection;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.CollectionUtils;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapUtil {
    
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

    /**
     * collection to map
     * @param values
     * @param keyFunction
     * @param valueFunction
     * @param <K>
     * @param <V>
     * @param <T>
     * @return
     */
    public static <K, V, T> ImmutableMap<K, V> uniqueIndex(
            Iterator<T> values,
            Function<? super T, K> keyFunction,
            Function<? super T, V> valueFunction) {
        checkNotNull(keyFunction);
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        while (values.hasNext()) {
            T value = values.next();
            builder.put(keyFunction.apply(value), valueFunction.apply(value));
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException duplicateKeys) {
            throw new IllegalArgumentException(
                    duplicateKeys.getMessage()
                            + ". To index multiple values under a key, use Multimaps.index.");
        }
    }

    public static Map<String, Object> toMap(Object o, String ... excludeAttributes) throws Exception {
        if(null == o) {
            return null;
        }
        List<String > attributeList = Lists.newArrayList(excludeAttributes);
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
        for(PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            Method method = pd.getReadMethod();
            String attri = method.getName();
            if(null != method && !attributeList.contains(attri)) {
                result.put(attri, method.invoke(o));
            }
        }
        return result;
    }

    public static Map<String, Object> toMap(Object o) throws Exception {
        return toMap(o, new String[]{});
    }

    public static Map<String, Object> toMapWithBeanMap(Object o) throws Exception {
        return new BeanMap(o);
    }

    public static Map<String, Object> toMapWithObjectMapper(Object o) throws Exception {
        return new ObjectMapper().convertValue(o, Map.class);
    }

    public static Map<String, Object> toMapWithJSONParse(Object o) throws Exception {
        return JSON.parseObject(JSON.toJSONString(o), new TypeReference<Map<String, Object>>() {
        });
    }

}
