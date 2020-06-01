package com.github.sinjar.common.util.common;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/5/27 13:46
 */
public class SingletonObj {

    private SingletonObj(){}

    private static final Map<String, Object> singleTonMap = new HashMap<>();

    public static void register(String key, Object object) {
        if (singleTonMap.containsKey(key)) {
            throw new RuntimeException("单例对象中值不能重复注册 key: " + key);
        }
        singleTonMap.put(key, object);
    }

    public static <T> T get(String key, Class<T> clazz) {
        Object obj = singleTonMap.get(key);
        if (obj == null) {
            throw new RuntimeException("单例对象获取失败, map中不包含此单例");
        }
        return clazz.cast(obj);
    }

    public static <T> T getWithNonable(String key, Class<T> clazz) {
        Object obj = singleTonMap.get(key);
        if (obj == null) {
            return null;
        }
        return clazz.cast(obj);
    }

    public static Object get(String key) {
        return singleTonMap.get(key);
    }

}
