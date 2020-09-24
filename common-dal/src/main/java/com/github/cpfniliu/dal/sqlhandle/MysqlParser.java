package com.github.cpfniliu.dal.sqlhandle;//package com.cpfframe4j.sqlhandle;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.cpf.sqlhandle.SqlHandle.SingleSqlCondition;
//
//public class MysqlParser implements ISqlParser
//{
//
//    @Override
//    public String geneCountSql(SqlHandle sqlHandle) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public String geneFieldsSql(SqlHandle sqlHandle) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public String buildCompleteSql(SqlHandle sqlHandle) {
//        if (sqlHandle.validateSqlHandle()) {
//            throw new RuntimeException("SqlParser解析错误! : tablename 为空");
//        }
//
//        // (2)生成开头
//        StringBuffer sqlbuf = new StringBuffer();
//        sqlbuf.append("SELECT ");
//        sqlbuf.append(disposeSelectFields(sqlHandle));
//        sqlbuf.append(" FROM ");
//        sqlbuf.append(sqlHandle.getTablename());
//
//        // (3)添加where条件
//        if (sqlHandle.hasConditionlist()){
//            String conditionSql = geneSqlCondition(sqlHandle.getConditionlist());
//            if (StringUtils.isNotBlank(conditionSql)) {
//                sqlbuf.append(" WHERE 1=1");
//                sqlbuf.append(conditionSql);
//            }
//        }
//
//        // group By
//        // having
//        // order by
//
//        // (4)设置排序
//        if (sqlHandle.hasOrder()){
//            sqlbuf.append(" ORDER BY " + sqlHandle.getOrder());
//        }
//        // 设置limit TODO
//        if (sqlHandle.hasLimit()) {
//            sqlbuf.append(geneLimit(sqlHandle.getLimit()));
//        }
//        return sqlbuf.toString();
//    }
//
//    @Override
//    public String buildSqlParams(SqlHandle sqlHandle) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//
//
//    private String disposeSelectFields(SqlHandle sqlHandle) {
//        if (sqlHandle.isnullSqlType()){
//            System.out.println(sqlHandle.getSqlType());
//        }
//        switch (sqlHandle.getSqlType()) {
//            case COUNT:
//                return "COUNT(0)";
//            case FIELD:
//                return Optional.ofNullable(sqlHandle.getFields()).orElse("");
//            default:
//                throw new IllegalArgumentException("sqlType : sqlHandle");
//        }
//    }
//
//    private String geneSqlCondition(List<SingleSqlCondition> singleSqlConditions){
//        StringBuilder sb = new StringBuilder();
//        for (SingleSqlCondition condition : singleSqlConditions) {
//            switch (condition.operateType) {
//                case EQ:
//                case NQ:
//                case GT:
//                case GE:
//                case LT:
//                case LE:
//                case LIKE:
//                    sb.append(ISqlConstant._AND_ + condition.field + condition.operateType.getSign() + "'" + condition.mapping.toString() + "'");
//                    break;
//                case IN:
//                case NOT_IN:
//                    sb.append(ISqlConstant._AND_ + condition.field + condition.operateType.getSign() + " (" + condition.mapping.toString() + ")");
//                    break;
//                case IS_NULL:
//                case IS_NON: // 不为空
//                    sb.append(ISqlConstant._AND_ + condition.field + condition.operateType.getSign());
//                    break;
//                case BETWEEN:
//                    throw new RuntimeException("暂不支持butween");
//                case SQL:
//                    sb.append(" " + condition.field);
//                    break;
//                default:
//                    throw new RuntimeException(condition.operateType.toString());
//            }
//        }
//        return sb.toString();
//    }
//
//
//    private String geneLimit(int[] limit) {
//        if (limit == null ) {
//            throw new IllegalArgumentException("limit " + Arrays.toString(limit));
//        }
//        switch (limit[0]) {
//            case 1: //
//                return " limit " + limit[1];
//            case 2: //
//                return " limit " + limit[1] + " " + limit[2];
//            case 3: //
//                return " limit " + limit[1] + " OFFSET " + limit[2];
//            default:
//                throw new IllegalArgumentException("limit " + Arrays.toString(limit));
//        }
//    }
//
//}
