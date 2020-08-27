package com.github.cpfniliu.common.ext.bean;

import com.github.cpfniliu.common.base.IMapGetter;
import com.github.cpfniliu.common.util.common.StrUtils;

import java.util.HashMap;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2019/4/23 11:16
 **/
public class CamelRecord extends HashMap<String, Object> implements IMapGetter<String, Object> {

    /**
     * 将Key转换为驼峰式
     */
    @Override
    public Object put(String key, Object value) {
        if (key == null || "".equals(key.trim())) {
            return "";
        }
        return super.put(StrUtils.lowerCamel(key), value);
    }

    /**
     * 将Key转换为驼峰式
     */
    public Object set(String key, Object value) {
        return super.put(key, value);
    }

}
