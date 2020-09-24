package com.github.cpfniliu.dal;

import com.github.cpfniliu.dal.base.CommonDao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/31 16:49
 */
public class AnTo<T> {

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    private Class<T> getClazz(){
        if (clazz == null) {
            return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            return clazz;
        }
    }

    public AnTo(Class<T> eClass) {
        clazz = eClass;
    }

    protected AnTo() {}

}
