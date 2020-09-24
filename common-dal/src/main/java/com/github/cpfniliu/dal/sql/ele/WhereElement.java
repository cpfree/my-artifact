package com.github.cpfniliu.dal.sql.ele;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/12 9:10
 */
public class WhereElement {

    public final String field;

    public final SqlOperateType sqlOperateType;

    public final Object val;

    public WhereElement(String field, SqlOperateType sqlOperateType, Object val) {
        this.field = field;
        this.sqlOperateType = sqlOperateType;
        this.val = val;
    }

}
