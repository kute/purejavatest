package com.kute.util.collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longbai on 2017/5/4.
 */
public class CommonCollectionUtil {

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

    public static Map<String, Object> toMap2(Object o) throws Exception {
        return new BeanMap(o);
    }

    public static Map<String, Object> toMap3(Object o) throws Exception {
        return new ObjectMapper().convertValue(o, Map.class);
    }

    public static Map<String, Object> toMap4(Object o) throws Exception {
        return JSON.parseObject(JSON.toJSONString(o), new TypeReference<Map<String, Object>>() {
        });
    }

    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if(!CollectionUtils.isEmpty(map) && map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }
}
