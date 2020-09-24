package com.github.cpfniliu.dal.sql.gene;

import com.github.cpfniliu.dal.sql.ele.WhereElement;
import com.github.cpfniliu.dal.sql.ele.SqlOperateType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/12 10:46
 */
public class WhereCondition {

    private List<WhereElement> whereElements;

    public void addEle(WhereElement whereElement) {
        if (whereElements == null) {
            whereElements = new ArrayList<>();
        }
        whereElements.add(whereElement);
    }

    public List<WhereElement> getWhereElements() {
        return whereElements;
    }

    /**
     * 等于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition eq(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.EQ, fieldValue));
        return this;
    }

    /**
     * 不等于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition nq(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.NQ, fieldValue));
        return this;
    }

    /**
     * 大于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition gt(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.GT, fieldValue));
        return this;
    }

    /**
     * 时间类型大于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition gt(String fieldName, Date fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.GT, fieldValue));
        return this;
    }

    /**
     * 大于等于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition ge(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.GE, fieldValue));
        return this;
    }

    /**
     * 时间类型大于等于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition ge(String fieldName, Date fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.GE, fieldValue));
        return this;
    }

    /**
     * 小于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition lt(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LT, fieldValue));
        return this;
    }

    /**
     * 时间类型小于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition lt(String fieldName, Date fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LT, fieldValue));
        return this;
    }

    /**
     * 小于等于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition le(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LE, fieldValue));
        return this;
    }

    /**
     * 时间类型小于等于
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition le(String fieldName, Date fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LE, fieldValue));
        return this;
    }

    /**
     * like
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition like(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LIKE, "%" + fieldValue.replace("%", "\\%") + "%"));
        return this;
    }

    /**
     * startWidth
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition startWidth(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LIKE, "%" + fieldValue.replace("%", "\\%")));
        return this;
    }

    /**
     * endWidth
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public WhereCondition endWidth(String fieldName, String fieldValue) {
        addEle(new WhereElement(fieldName, SqlOperateType.LIKE, fieldValue.replace("%", "\\%") + "%"));
        return this;
    }

    /**
     * in
     * @param fieldName 字段名
     * @param fieldValues 字段值,括号中的内容
     */
    public WhereCondition in(String fieldName, List<Object> fieldValues) {
        addEle(new WhereElement(fieldName, SqlOperateType.IN, fieldValues));
        return this;
    }

    /**
     * not in
     * @param fieldName 字段名
     * @param fieldValues 字段值，括号中的内容
     */
    public WhereCondition notIn(String fieldName, List<Object> fieldValues) {
        addEle(new WhereElement(fieldName, SqlOperateType.NOT_IN, fieldValues));
        return this;
    }

    /**
     * 为空
     * @param fieldName 字段名
     */
    public WhereCondition isNull(String fieldName) {
        addEle(new WhereElement(fieldName, SqlOperateType.IS_NULL, null));
        return this;
    }

    /**
     * 不为空
     * @param fieldName 字段名
     */
    public WhereCondition isNotNull(String fieldName) {
        addEle(new WhereElement(fieldName, SqlOperateType.IS_NON, null));
        return this;
    }

    /**
     * 为空
     * @param fieldName 字段名
     */
    public WhereCondition isBlank(String fieldName) {
        addEle(new WhereElement(fieldName, SqlOperateType.IS_NULL, ""));
        return this;
    }

}
