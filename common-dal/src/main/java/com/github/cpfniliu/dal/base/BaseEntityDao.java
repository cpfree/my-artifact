package com.github.cpfniliu.dal.base;

import com.github.cpfniliu.dal.sql.ele.SqlEleBean;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/1 18:28
 */
public class BaseEntityDao<T> extends BaseDao {

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    private Class<T> getClazz(){
        if (clazz == null) {
            return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            return clazz;
        }
    }

    public static <E> BaseEntityDao<E> of(Class<E> eClass) {
        return new BaseEntityDao<>(null, eClass);
    }

    public static <E> BaseEntityDao<E> of(Connection connection, Class<E> eClass) {
        return new BaseEntityDao<>(connection, eClass);
    }

    protected BaseEntityDao(Connection connection) {
        super(connection);
        clazz = getClazz();
    }

    protected BaseEntityDao() {
        clazz = getClazz();
    }

    private BaseEntityDao(Connection connection, Class<T> eClass) {
        super(connection);
        clazz = eClass;
    }

    public boolean insert(T t) {
        try {
            SqlEleBean sqlEleBean = StandardSqlPool.insertEntity(t);
            return queryRunner.update(connection, sqlEleBean.sql, sqlEleBean.params) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("插入语句报错: " + t.toString(), e);
        }
    }

    /**
     * 通过主键删除
     */
    public int deleteByPrimaryKey(T t) {
        Class<?> aClass = t.getClass();
        String primaryKey = DbEntityUtil.getPrimaryKey(aClass);
        try {
            Field declaredField = aClass.getDeclaredField(primaryKey);
            declaredField.setAccessible(true);
            Object primaryVal = declaredField.get(t);
            SqlEleBean sqlEleBean = StandardSqlPool.deleteEntity(aClass, primaryKey, primaryVal);
            return queryRunner.update(connection, sqlEleBean.sql, sqlEleBean.params);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            throw new RuntimeException("删除语句报错: " + t.toString(), e);
        }
    }

    public int updateByPrimaryKey(T t) {
        Class<?> aClass = t.getClass();
        String primaryKey = DbEntityUtil.getPrimaryKey(aClass);
        try {
            Field declaredField = aClass.getDeclaredField(primaryKey);
            declaredField.setAccessible(true);
            Object primaryVal = declaredField.get(t);
            SqlEleBean sqlEleBean = StandardSqlPool.updateEntity(t, null, primaryKey, primaryVal);
            return queryRunner.update(connection, sqlEleBean.sql, sqlEleBean.params);
        } catch (SQLException e) {
            throw new RuntimeException("更新语句报错: " + t.toString(), e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("实体类字段映射异常", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("非法入口", e);
        }
    }

    public T selectByPrimaryKey(String primaryVal) {
        Class<T> aClass = getClazz();
        String primaryKey = DbEntityUtil.getPrimaryKey(aClass);
        return selectByKey(primaryKey, primaryVal);
    }

    public T selectByKey(String keyName, String keyVal) {
        Class<T> aClass = getClazz();
        return selectByKey(keyName, keyVal, aClass);
    }

    public <R> R selectByKey(String keyName, String keyVal, Class<R> returnClass) {
        Class<T> aClass = getClazz();
        SqlEleBean sqlEleBean = StandardSqlPool.selectEntityByKey(aClass, keyName, keyVal);
        return find(sqlEleBean.sql, returnClass, sqlEleBean.params);
    }

    /**
     * 查找单个实体
     */
    public <R> R find(String sql, Class<R> returnClass, Object... params) {
        try {
            return queryRunner.query(connection, sql, new BeanHandler<>(returnClass), params);
        } catch (SQLException e) {
            throw new RuntimeException("查询语句报错: sql: " + sql + "params: " + Arrays.toString(params), e);
        }
    }

    /**
     * 查找列表实体
     */
    public List<T> findList(String keyName, String keyVal) {
        Class<T> aClass = getClazz();
        return findList(keyName, keyVal, aClass);
    }

    /**
     * 查找列表实体
     */
    public <R> List<R> findList(String keyName, String keyVal, Class<R> returnClass) {
        Class<T> aClass = getClazz();
        SqlEleBean sqlEleBean = StandardSqlPool.selectEntityByKey(aClass, keyName, keyVal);
        return findList(sqlEleBean.sql, returnClass, sqlEleBean.params);
    }

    /**
     * 查找单个实体
     */
    public <R> List<R> findList(String sql, Class<R> returnClass, Object... params) {
        try {
            return queryRunner.query(connection, sql, new BeanListHandler<>(returnClass), params);
        } catch (SQLException e) {
            throw new RuntimeException("查询列表语句报错: sql: " + sql + "params: " + Arrays.toString(params), e);
        }
    }


}
