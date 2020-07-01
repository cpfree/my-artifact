package com.github.sinjar.common.base;

import java.util.Date;
import java.util.function.Function;

public interface IGetter {

    Object get(String key);

    default <T> T get(String key, Class<T> returnClass) {
        return returnClass.cast(get(key));
    }

    default String getString(String key) {
        return (String) get(key);
    }

    default Date getDate(String key) {
        return (Date) get(key);
    }

    default Integer getInteger(String key) {
        return (Integer) get(key);
    }

    default Object getDefault(String key, Object defaultObj) {
        Object o = get(key);
        if (o == null) {
            return defaultObj;
        }
        return o;
    }

    default <T> T getDefault(String key, Class<T> returnClass, T defaultObj){
        Object o = get(key);
        if (o == null) {
            return defaultObj;
        }
        return returnClass.cast(o);
    }

    default <R> R get(String key, Function<Object, R> function){
        Object o = get(key);
        return function.apply(o);
    }

}
