package com.github.cpfniliu.dal.base;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;

public class CommonDao<T> extends BaseDao {

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    private Class<T> getClazz(){
        if (clazz == null) {
            return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            return clazz;
        }
    }

    public static <E> CommonDao<E> of(Class<E> eClass) {
        return new CommonDao<>(null, eClass);
    }

    public static <E> CommonDao<E> of(Connection connection, Class<E> eClass) {
        return new CommonDao<>(connection, eClass);
    }

    protected CommonDao(Connection connection) {
        super(connection);
        clazz = getClazz();
    }

    protected CommonDao() {
        clazz = getClazz();
    }

    private CommonDao(Connection connection, Class<T> eClass) {
        super(connection);
        clazz = eClass;
    }

}
