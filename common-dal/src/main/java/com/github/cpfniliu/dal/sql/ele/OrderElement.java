package com.github.cpfniliu.dal.sql.ele;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/12 10:10
 */
public class OrderElement {

    public String field;
    public OrderDirection orderDirection;

    public OrderElement(String field, OrderDirection orderDirection) {
        this.field = field;
        this.orderDirection = orderDirection;
    }
}
