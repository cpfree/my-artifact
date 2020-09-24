package com.github.cpfniliu.dal.sql.adapter;

import com.github.cpfniliu.dal.sql.ele.OrderElement;
import com.github.cpfniliu.dal.sql.gene.SelectSqlBuilder;
import com.github.cpfniliu.dal.sql.gene.SqlGeneAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/12 22:20
 */
public class MysqlAdapter extends SqlGeneAdapter {

    @Override
    public String generateSelectSql(SelectSqlBuilder selectSqlBuilder) {
        String table = selectSqlBuilder.getTable();
        Validate.isTrue(StringUtils.isNoneBlank(table));
        List<String> selectFields = selectSqlBuilder.getSelectFields();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        if (selectFields == null || selectFields.isEmpty()) {
            sb.append("*");
        } else {
            sb.append(StringUtils.join(selectFields, ","));
        }
        sb.append(" FROM ");
        sb.append(table);
        sb.append(" WHERE 1=1 ");
        String where = whereGenerate(selectSqlBuilder.where());
        if (StringUtils.isNotBlank(where)) {
            sb.append(where);
        }
        List<OrderElement> orderElements = selectSqlBuilder.getOrderElements();
        if (orderElements != null && !orderElements.isEmpty()) {
            sb.append(" ORDER BY");
            for (OrderElement orderElement : orderElements) {
                sb.append(" ").append(orderElement.field).append(" ").append(orderElement.orderDirection.name());
            }
        }
        int limit = selectSqlBuilder.getLimit();
        if (limit > 0) {
            sb.append(" LIMIT ").append(limit);
        }
        return sb.toString();
    }


}
