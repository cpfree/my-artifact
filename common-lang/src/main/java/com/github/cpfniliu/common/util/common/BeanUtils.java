package com.github.cpfniliu.common.util.common;

import com.github.cpfniliu.common.ext.hub.SimpleCode;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 未知
 **/
@Slf4j
public class BeanUtils {

    private BeanUtils(){}

    /**
     * 对象转换为 Map<String, Object> 输出
     *
     * @param bean 待转换的bean
     * @return 转换后的Map对象
     * @param <T> bean的类型
     */
    public static <T> Map<String, Object> objectToStringObjectMap(T bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        SimpleCode.runtimeException(() -> {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method getter = property.getReadMethod();
                if (getter != null && !"class".equals(property.getName())) {
                    Object value = getter.invoke(bean);
                    if (value != null) {
                        map.put(property.getName(), value);
                    }
                }
            }
        });
        return map;
    }

    public static void copyProperties(Object source, Object target) throws InvocationTargetException, IllegalAccessException {
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }

    public static void populate(Object bean, Map<String, Object> properties) throws InvocationTargetException, IllegalAccessException {
        org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
    }

    public static <T> T mapToBean(Map<String, Object> properties, Class<T> beanClass) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        T t = beanClass.getDeclaredConstructor().newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(t, properties);
        return t;
    }

    public static <T> List<T> mapListToBeanList(List<Map<String, Object>> mapList, Class<T> beanClass) throws InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<T> result = new ArrayList<>();
        if (mapList == null) {
            return result;
        }
        for (Map<String, Object> map : mapList) {
            result.add(mapToBean(map, beanClass));
        }
        return result;
    }

}
