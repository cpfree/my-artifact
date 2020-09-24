package com.github.cpfniliu.dal.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbField {

    /**
     * 字段名称
     */
    String name() default "";

    /**
     * 字段类型
     */
    String type() default "";

    /**
     * 必填
     */
    boolean required() default false;

    /**
     * 长度
     */
    int length() default 0;

    /**
     * 默认值
     */
    String defaultValue() default "";
}
