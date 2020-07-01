package com.github.sinjar.common.util.common;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/6/15 16:26
 */
public class ObjUtils {

    private ObjUtils(){}

    /**
     * 判断两个对象是否相等, 两个对象均为 null 也算相等
     */
    public static boolean isEqualWithNullAble(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        } else {
            return obj1.equals(obj2);
        }
    }

}
