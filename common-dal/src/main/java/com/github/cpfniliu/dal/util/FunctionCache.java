package com.github.cpfniliu.dal.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/13 14:58
 */
public class FunctionCache {

    private static Map<Object, Map<Object, Object>> cache = new ConcurrentHashMap<>();

    public <T, R> R getFromCache(Object flag, T in, Function<T, R> function) {
        Map<Object, Object> objectObjectMap = cache.computeIfAbsent(flag, k -> new ConcurrentHashMap<>());
        return (R) objectObjectMap.computeIfAbsent(in, k -> function.apply(in));
    }


}
