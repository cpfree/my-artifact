package com.github.cpfniliu.common.validate;

import java.util.Collection;
import java.util.Map;

/**
 * @author CPF
 * @version [版本号, 2017年10月31日]
 */

public class ValidateUtil {

    private ValidateUtil() {}

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isBlankArray(Object[] objarr) {
        return objarr == null || objarr.length == 0;
    }

    public static boolean isNotBlankArray(Object[] objarr) {
        return !isBlankArray(objarr);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isBlankMap(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotBlankMap(Map<?, ?> map) {
        return !isBlankMap(map);
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
