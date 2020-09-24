package com.github.cpfniliu.dal.sql.ele;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/13 11:35
 */
public class SqlEleBean {

    public final String sql;
    public final Object[] params;

    public SqlEleBean(String sql, Object[] params) {
        this.sql = sql;
        this.params = params;
    }

}
