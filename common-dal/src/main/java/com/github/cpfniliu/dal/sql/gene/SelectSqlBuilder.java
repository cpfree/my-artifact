package com.github.cpfniliu.dal.sql.gene;

import com.github.cpfniliu.dal.sql.ele.OrderDirection;
import com.github.cpfniliu.dal.sql.ele.OrderElement;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/10 17:56
 */
public class SelectSqlBuilder {

    @Setter
    @Getter
    private String table;

    @Getter
    private List<String> selectFields;

    private WhereCondition whereCondition;

    @Getter
    private List<OrderElement> orderElements;

    @Setter
    @Getter
    private int limit;

    void addSelectFields(String... fieldNames) {
        if (selectFields == null) {
            selectFields = new ArrayList<>();
        }
        selectFields.addAll(Arrays.asList(fieldNames));
    }

    public WhereCondition where() {
        if (whereCondition == null) {
            whereCondition = new WhereCondition();
        }
        return whereCondition;
    }

    /**
     * 多表 TODO
     *
     * @param fieldName 字段名称
     * @param orderDirection 排序方向
     */
    public void orderBy(@NonNull String fieldName, OrderDirection orderDirection) {
        if (orderElements == null) {
            orderElements = new ArrayList<>();
        }
        orderElements.add(new OrderElement(fieldName, orderDirection));
    }

}
