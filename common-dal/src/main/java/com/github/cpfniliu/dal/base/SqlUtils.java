package com.github.cpfniliu.dal.base;

import com.github.cpfniliu.common.util.common.StrUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SqlUtils {

    public static String geneInsertSql(Class<?> clazz) {
        final Field[] declaredFields = clazz.getDeclaredFields();
        final String collect = Arrays.stream(declaredFields).map(Field::getName).map(StrUtils::lowerDownLine).collect(Collectors.joining(", "));
        return String.format("insert into %s(%s) values(%s)", StrUtils.lowerDownLine(clazz.getSimpleName()), collect, StringUtils.repeat("?", ",", declaredFields.length));
    }

    public static Object[] geneObjectParam(Object object) {
        final Field[] declaredFields = object.getClass().getDeclaredFields();
        Object[] param = new Object[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            try {
                param[i] = field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("实体类字段映射异常", e);
            }
        }
        return param;
    }

}
