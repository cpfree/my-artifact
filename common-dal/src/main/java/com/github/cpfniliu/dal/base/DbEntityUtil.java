package com.github.cpfniliu.dal.base;

import com.github.cpfniliu.common.ext.bean.DoubleBean;
import com.github.cpfniliu.common.util.common.StrUtils;
import com.github.cpfniliu.dal.anno.DbTable;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>Description : </b> 基础类
 *
 * @author CPF
 * Date: 2020/7/9 17:23
 */
public class DbEntityUtil {

    private DbEntityUtil() { }

    public static final Object NULL = new Object();

    /**
     * 获取实体对象的
     *
     * @param object 实体对象
     * @return
     */
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

    public static DoubleBean<List<String>, List<Object>> mark(Object object, Object[] mark) {
        final Field[] declaredFields = object.getClass().getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            try {
                Object object1 = field.get(object);
                if (! equals(object1, mark[i])) {
                    if (object1 == NULL) {
                        object1 = null;
                    }
                    fieldNames.add(field.getName());
                    params.add(object1);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("实体类字段映射异常", e);
            }
        }
        return DoubleBean.of(fieldNames, params);
    }

    private static boolean equals(final Object object1, final Object object2) {
        if (object1 == null) {
            return object2 == null;
        }
        return object1.equals(object2);
    }

    /**
     * 获取数据表名称
     * 1. 如果 class 有 DbTable 注解, 从注解中获取 表名
     * 2. 表的小写下划线形式作为表名.
     *
     * @param clazz
     * @return
     */
    public static String getTableName(@NonNull Class<?> clazz) {
        if (clazz.isAnnotationPresent(DbTable.class)) {
            DbTable annotation = clazz.getAnnotation(DbTable.class);
            return annotation.table();
        }
        return StrUtils.lowerDownLine(clazz.getSimpleName());
    }

    /**
     * 获取主键
     *
     * @param clazz 类
     * @return
     */
    public static String getPrimaryKey(Class<?> clazz) {
        if (clazz.isAnnotationPresent(DbTable.class)) {
            DbTable annotation = clazz.getAnnotation(DbTable.class);
            return annotation.id()[0];
        }
        return "keyId";
    }

    /**
     * @param clazz
     * @return
     */
    public static Field[] getDbFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    public static String[] getDbFieldString(Class<?> clazz) {
        return Arrays.stream(getDbFields(clazz)).map(Field::getName).map(StrUtils::lowerDownLine).toArray(String[]::new);
    }
}
