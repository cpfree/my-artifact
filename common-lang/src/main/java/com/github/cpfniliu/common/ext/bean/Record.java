package com.github.cpfniliu.common.ext.bean;

import com.github.cpfniliu.common.base.IMapGetter;

import java.util.HashMap;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2019/4/3 20:03
 **/
public class Record extends HashMap<String, Object> implements IMapGetter<String, Object> {

    public void set(String key, String value) {
        super.put(key, value);
    }

}
