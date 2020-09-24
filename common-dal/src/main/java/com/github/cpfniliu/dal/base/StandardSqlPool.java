package com.github.cpfniliu.dal.base;

import com.github.cpfniliu.common.lang.WrongBranchException;
import com.github.cpfniliu.common.util.common.StrUtils;
import com.github.cpfniliu.common.ext.bean.DoubleBean;
import com.github.cpfniliu.dal.sql.ele.SqlEleBean;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/1 17:20
 */
public class StandardSqlPool {

    public static String geneStandardInsertSql(Class<?> clazz, Field[] insertFields) {
        final String collect = Arrays.stream(insertFields).map(Field::getName).map(StrUtils::lowerDownLine).collect(Collectors.joining(","));
        return String.format("insert into %s(%s) values(%s)", DbEntityUtil.getTableName(clazz), collect, StringUtils.repeat("?", ",", insertFields.length));
    }

    public static <E> String geneStandardDeleteSql(@NonNull Class<E> clazz, String whereKey) {
        final String tableName = DbEntityUtil.getTableName(clazz);
        final String dbWhereKey = StrUtils.lowerDownLine(whereKey);
        return String.format("delete from %s where %s = ?", tableName, dbWhereKey);
    }

    public static <E> String geneStandardUpdateSql(@NonNull E t, Field[] updateFields, String whereKey) throws NoSuchFieldException, IllegalAccessException {
        String[] objects = Stream.of(updateFields).map(Field::getName).toArray(String[]::new);
        return geneStandardUpdateSql(t, objects, whereKey);
    }

    public static <E> String geneStandardUpdateSql(@NonNull E t, String[] updateFields, String whereKey) throws NoSuchFieldException, IllegalAccessException {
        final Class<?> clazz = t.getClass();
        // 获取并检查 whereKey
        final Field primaryKeyNameField = clazz.getDeclaredField(whereKey);
        if (primaryKeyNameField == null) {
            throw new RuntimeException("not fount primaryKeyNameField: " + whereKey);
        }
        primaryKeyNameField.setAccessible(true);
        final Object primaryVal = primaryKeyNameField.get(t);
        // 反射获取的基本数据类型会自动装箱为其包装类型, 例如, 实体类中的 int 属性, 经由反射获取后会变成 Integer 类型
        if (primaryVal == null) {
            throw new RuntimeException("whereKey 值为空 => " + t.toString());
        }
        if (primaryVal instanceof String) {
            if (StringUtils.isBlank((String) primaryVal)) {
                throw new RuntimeException("primaryVal 对象为空" + whereKey + ", ==> " + t.toString());
            }
        } else if (!(primaryVal instanceof Integer)) {
            throw new WrongBranchException("不支持的whereKey属性 whereKey: " + whereKey + ", val:" + primaryVal);
        }
        final String tableName = DbEntityUtil.getTableName(clazz);
        // set 的字段
        final String setString = Arrays.stream(updateFields).map(it -> StrUtils.lowerDownLine(it) + " = ?").collect(Collectors.joining(","));
        final String dbWhereKey = StrUtils.lowerDownLine(whereKey);
        return String.format("update %s set %s where %s = ?", tableName, setString, dbWhereKey);
    }

    public static <E> String geneStandardSelectSql(@NonNull Class<E> clazz, Set<String> selectFields, String whereKey) {
        final String tableName = DbEntityUtil.getTableName(clazz);
        // set 的字段
        final String selectString = selectFields.stream().map(it -> StrUtils.lowerDownLine(it) + " " + it).collect(Collectors.joining(","));
        final String dbWhereKey = StrUtils.lowerDownLine(whereKey);
        return String.format("select %s from %s where %s = ?", selectString, tableName, dbWhereKey);
    }


    public static SqlEleBean selectEntityByKey(@NonNull Class<?> clazz, String primaryKeyName, String primaryVal) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Set<String> collect = Arrays.stream(declaredFields).map(it -> StrUtils.lowerDownLine(it.getName())).collect(Collectors.toSet());
        String sql = geneStandardSelectSql(clazz, collect, primaryKeyName);
        return new SqlEleBean(sql, new Object[]{primaryVal});
    }

    public static <T> SqlEleBean insertEntity(@NonNull T t) {
        Class<?> aClass = t.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        String sql = geneStandardInsertSql(aClass, declaredFields);
        Object[] param = new Object[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            try {
                param[i] = field.get(t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("实体类字段映射异常", e);
            }
        }
        return new SqlEleBean(sql, param);
    }

    public static <T> SqlEleBean updateEntity(@NonNull T t, Set<String> updateFields, String whereKey, Object whereKeyVal) throws NoSuchFieldException, IllegalAccessException {
        final Class<?> clazz = t.getClass();
        Field[] updateFieldList = (Field[]) Arrays.stream(clazz.getDeclaredFields()).filter(it -> updateFields == null || updateFields.contains(it.getName())).toArray();
        String sql = geneStandardUpdateSql(t, updateFieldList, whereKey);

        final int size = updateFieldList.length;
        Object[] param = new Object[size + 1];
        for (int i = 0; i < size; i++) {
            Field field = updateFieldList[i];
            field.setAccessible(true);
            param[i] = field.get(t);
        }
        param[size] = whereKeyVal;
        return new SqlEleBean(sql, param);
    }

    public static <T> SqlEleBean updateEntityOnDemand(@NonNull T t, @NonNull Object[] objects, String whereKey, Object whereKeyVal) throws NoSuchFieldException, IllegalAccessException {
        DoubleBean<List<String>, List<Object>> mark = DbEntityUtil.mark(t, objects);
        String[] objects1 = (String[])mark.getO1().toArray();
        Object[] param = mark.getTwo().toArray(new Object[objects1.length + 1]);
        String sql = geneStandardUpdateSql(t, objects1, whereKey);
        param[objects1.length] = whereKeyVal;
        return new SqlEleBean(sql, param);
    }

    public static SqlEleBean deleteEntity(@NonNull Class<?> clazz, String whereKey, Object whereKeyVal) {
        String sql = geneStandardDeleteSql(clazz, whereKey);
        return new SqlEleBean(sql, new Object[]{whereKeyVal});
    }


}
