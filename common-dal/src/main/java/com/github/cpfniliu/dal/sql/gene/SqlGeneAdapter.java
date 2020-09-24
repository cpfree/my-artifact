package com.github.cpfniliu.dal.sql.gene;

import com.github.cpfniliu.dal.sql.ele.WhereElement;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/12 22:21
 */
public abstract class SqlGeneAdapter {

    public abstract String generateSelectSql(SelectSqlBuilder selectSqlBuilder);

    public String whereGenerate(WhereCondition whereCondition) {
        List<WhereElement> whereElements = whereCondition.getWhereElements();
        if (whereElements == null || whereElements.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        whereElements.forEach(ele -> {
            sb.append(" AND ").append(ele.field).append(" ");
            switch (ele.sqlOperateType) {
                case EQ:
                case GE:
                case GT:
                case LE:
                case LT:
                case NQ:
                case LIKE:
                case IS_NON:
                case IS_NULL:
                    sb.append(ele.sqlOperateType.getSign()).append("?");
                    break;
                case IN:
                case NOT_IN:
                    Object[] params = (Object[]) ele.val;
                    sb.append(ele.sqlOperateType.getSign()).append("(").append(StringUtils.repeat("?", params.length)).append(")");
                    break;
                case BETWEEN:
                    sb.append(ele.sqlOperateType.getSign()).append("(?, ?)");
                    break;
                default:
                    throw new RuntimeException("not support");
            }
        });
        return sb.toString();
    }

}
