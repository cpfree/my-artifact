package com.github.cpfniliu.dal.sqlhandle;//package com.cpfframe4j.sqlhandle;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//
//
//public class SqlHandle
//{
//    /**
//     * 查询字段
//     */
//    private String fields = null;
//
//    /**
//     * 表名
//     */
//    private String tablename = null;
//
//    /**
//     * 排序
//     */
//    private String order = null;
//
//    /**
//     * Sql类型
//     */
//    private SqlType sqlType = null;
//
//    /**
//     * 限制
//     * limit[0] : 方式
//     */
//    private int limit[] = null;
//
//    /**
//     * 条件列表
//     */
//    private List<SingleSqlCondition> conditionlist = null;
//
//    /**
//     * 清除条件
//     */
//    public void init(){
//        this.fields = null;
//        this.tablename = null;
//        this.order = null;
//        this.limit = null;
//        this.conditionlist = null;
//    }
//
//    protected class SingleSqlCondition{
//        final String field;
//        final SqlOperateType operateType;
//        final ValueType objType;
//        final Object mapping;
//        SingleSqlCondition(String field, SqlOperateType operateType, ValueType objType, Object mapping){
//            this.field = field.trim();
//            this.operateType = operateType;
//            this.objType = objType;
//            this.mapping = mapping;
//        }
//    }
//
//    private void putMap(String field, SqlOperateType operateType, ValueType objType, String mapping) {
//        if (conditionlist == null) {
//            conditionlist = new ArrayList<>(3);
//        }
//        conditionlist.add(new SingleSqlCondition(field, operateType, objType, mapping));
//    }
//
//    /**
//     *  纯Sql语句
//     */
//    public SqlHandle addSqlCondition(String sql) {
//        putMap(sql, SqlOperateType.SQL, ValueType.S, "");
//        return this;
//    }
//
//    /**
//     * 等于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle eq(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.EQ, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 不等于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle nq(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.NQ, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 大于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle gt(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.GT, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 时间类型大于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle gt(String fieldName, Date fieldValue) {
//        putMap(fieldName, SqlOperateType.GT, ValueType.D,
//                EpointDateUtil.convertDate2String(fieldValue, EpointDateUtil.DATE_TIME_FORMAT));
//        return this;
//    }
//
//    /**
//     * 大于等于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle ge(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.GE, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 时间类型大于等于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle ge(String fieldName, Date fieldValue) {
//        putMap(fieldName, SqlOperateType.GE, ValueType.D,
//                EpointDateUtil.convertDate2String(fieldValue, EpointDateUtil.DATE_TIME_FORMAT));
//        return this;
//    }
//
//    /**
//     * 小于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle lt(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.LT, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 时间类型小于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle lt(String fieldName, Date fieldValue) {
//        putMap(fieldName, SqlOperateType.LT, ValueType.D,
//                EpointDateUtil.convertDate2String(fieldValue, EpointDateUtil.DATE_TIME_FORMAT));
//        return this;
//    }
//
//    /**
//     * 小于等于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle le(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.LE, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 时间类型小于等于
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle le(String fieldName, Date fieldValue) {
//        putMap(fieldName, SqlOperateType.LE, ValueType.D,
//                EpointDateUtil.convertDate2String(fieldValue, EpointDateUtil.DATE_TIME_FORMAT));
//        return this;
//    }
//
//    /**
//     * like
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle like(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.LIKE, ValueType.S, "%" + fieldValue.replace("%", "\\%") + "%");
//        return this;
//    }
//
//    /**
//     * startWidth
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle startWidth(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.LIKE, ValueType.S, "%" + fieldValue.replace("%", "\\%"));
//        return this;
//    }
//
//    /**
//     * endWidth
//     * @param fieldName 字段名
//     * @param fieldValue 字段值
//     */
//    public SqlHandle endWidth(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.LIKE, ValueType.S, fieldValue.replace("%", "\\%") + "%");
//        return this;
//    }
//
//    /**
//     * in
//     * @param fieldName 字段名
//     * @param fieldValue 字段值,括号中的内容
//     */
//    public SqlHandle in(String fieldName, String fieldValues) {
//        putMap(fieldName, SqlOperateType.IN, ValueType.S, fieldValues);
//        return this;
//    }
//
//    /**
//     * in
//     * @param fieldName 字段名
//     * @param fieldValue 字段值集合
//     */
//    public SqlHandle in(String fieldName, List<String> fieldValues) {
//        in(fieldName, "'" + StringUtils.join(fieldValues, "','") + "'");
//        return this;
//    }
//
//    /**
//     * not in
//     * @param fieldName 字段名
//     * @param fieldValue 字段值，括号中的内容
//     */
//    public SqlHandle notin(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.NOT_IN, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * not in
//     * @param fieldName 字段名
//     * @param fieldValue 字段值集合
//     */
//    public SqlHandle notin(String fieldName, List<String> fieldValues) {
//        notin(fieldName, "'" + StringUtils.join(fieldValues, "','") + "'");
//        return this;
//    }
//
//    /**
//     * between,只用于时间类型
//     * @param fieldName 字段名
//     * @param fromDate	开始时间
//     * @param endDate	结束时间
//     */
//    public SqlHandle between(String fieldName, Date fromDate, Date endDate) {
//        putMap(fieldName, SqlOperateType.BETWEEN, ValueType.D,
//                EpointDateUtil.convertDate2String(fromDate, EpointDateUtil.DATE_TIME_FORMAT) + ISqlConstant.SQL_OPERATE_SPLIT
//                        + EpointDateUtil.convertDate2String(endDate, EpointDateUtil.DATE_TIME_FORMAT));
//        return this;
//    }
//
//    /**
//     * 为空
//     * @param fieldName 字段名
//     */
//    public SqlHandle isBlank(String fieldName) {
//        isBlankOrValue(fieldName, "");
//        return this;
//    }
//
//    /**
//     * 为空或者等于某个值
//     * @param fieldName 字段名
//     * @param fieldValue 等于的值
//     */
//    public SqlHandle isBlankOrValue(String fieldName, String fieldValue) {
//        putMap(fieldName, SqlOperateType.IS_NULL, ValueType.S, fieldValue);
//        return this;
//    }
//
//    /**
//     * 不为空
//     * @param fieldName 字段名
//     */
//    public SqlHandle isNotBlank(String fieldName) {
//        putMap(fieldName, SqlOperateType.IS_NON, ValueType.S, "");
//        return this;
//    }
//
//
//    public SqlHandle setTableName(String tableName) {
//        this.tablename = tableName;
//        return this;
//    }
//
//    /**
//     * 需要查询的字段
//     * @param fields select的字段
//     */
//    public SqlHandle setSelectFields(String fields) {
//        this.fields = fields.trim();
//        return this;
//    }
//
//    /**
//     * 设置倒序排列的字段
//     * @param fieldName
//     */
//    public SqlHandle orderDesc(String fieldName) {
//        return orderBy(fieldName, OrderDirection.DESC);
//    }
//
//    /**
//     * 设置正序排列的字段
//     * @param fieldName
//     */
//    public SqlHandle orderAsc(String fieldName) {
//        return orderBy(fieldName, OrderDirection.ASC);
//    }
//
//    /**
//     * 设置排序字段和方向
//     * @param sortField 字段名
//     * @param sortOrder 排序方向（asc,desc）
//     */
//    public SqlHandle orderBy(String sortField, OrderDirection sortOrder) {
//        if (order == null) {
//            order = sortField + " " + sortOrder.name();
//        } else {
//            order += "," + sortField + " " + sortOrder.name();
//        }
//        return this;
//    }
//
//    public SqlHandle setSqlType(SqlType sqlType) {
//        this.sqlType = sqlType;
//        return this;
//    }
//
//    public SqlHandle limit(int mode, int start, int end) {
//        if (limit == null) {
//            limit = new int[3];
//        }
//        this.limit[0] = mode;
//        this.limit[1] = start;
//        this.limit[2] = end;
//        return this;
//    }
//
//    public SqlHandle limit(int limitnum) {
//        if (limitnum < 0) {
//            throw new IllegalArgumentException("limit " + limitnum);
//        }
//        return limit(1, limitnum, -1);
//    }
//
//    public SqlHandle limitse(int start, int end) {
//        if (start < 0 || end < 0 || start > end) {
//            throw new IllegalArgumentException("start " + start + ", end : " + end);
//        }
//        return limit(2, start, end);
//    }
//
//    public SqlHandle limitOffset(int num, int offset) {
//        if (num < 0 || offset < 0) {
//            throw new IllegalArgumentException("num " + num + ", offset : " + offset);
//        }
//        return limit(3, num, offset);
//    }
//
//
//    public boolean hasFields() {
//        return StringUtils.isNotBlank(fields);
//    }
//
//    public boolean hasTablename() {
//        return StringUtils.isNotBlank(tablename);
//    }
//
//    public boolean isnullSqlType() {
//        return sqlType == null;
//    }
//
//    public boolean hasOrder() {
//        return StringUtils.isNotBlank(order);
//    }
//
//    public boolean hasLimit() {
//        return limit != null;
//    }
//
//    public boolean hasConditionlist() {
//        return conditionlist != null && !conditionlist.isEmpty();
//    }
//
//    public boolean validateSqlHandle() {
//        if (!hasFields()){
//            throw new RuntimeException("无tablename");
//        }
//        return true;
//    }
//
//    public String getFields() {
//        return fields;
//    }
//
//    public String getTablename() {
//        return tablename;
//    }
//
//    public SqlType getSqlType() {
//        return sqlType;
//    }
//
//    public String getOrder() {
//        return order;
//    }
//
//    public int[] getLimit() {
//        return limit;
//    }
//
//    public List<SingleSqlCondition> getConditionlist() {
//        return conditionlist;
//    }
//
//}
