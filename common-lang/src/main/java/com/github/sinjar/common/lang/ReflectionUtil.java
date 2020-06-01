package com.github.sinjar.common.lang;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionUtil {

    private ReflectionUtil(){}

    /**
     * 创建一个实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("new Instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 映射调用方法
     * @param obj 对象
     * @param method 方法
     * @param params 参数
     * @return 调用映射方法的结果
     */
    public static Object invokeMethod(Object obj, Method method, Object... params) {
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(obj, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("invokeMethod failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     *
     * @param obj 对象
     * @param field 成员变量
     * @param val 值
     */
    public static void setField(Object obj, Field field, Object val) {
        field.setAccessible(true);
        try {
            field.set(obj,val);
        } catch (IllegalAccessException e) {
            log.error("invoke set setField failure", e);
            throw new RuntimeException(e);
        }
    }

}
