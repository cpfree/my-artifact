package com.github.cpfniliu.dal.sqlhandle;//package com.cpfframe4j.sqlhandle;
//
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//
//
///**
// * sql工具, 进行仅限于生成SQL语句
// *
// * @author CPF
// * @version [版本号, 2017年12月29日]
// */
//public class SqlBuilder
//{
//    /**
//     * 通过实体类获取Entity实体类
//     *
//     * @param baseClass
//     * @return baseClass.getAnnotation(Entity.class)
//     */
//    public static Entity getEntityByClass(Class<? extends BaseEntity> baseClass) {
//        return baseClass.getAnnotation(Entity.class);
//    }
//
//    /**
//     * 通过实体类获取数据表名
//     *
//     * @param baseClass
//     * @return baseClass.getAnnotation(Entity.class).table()
//     */
//    public static String getTablenameByClass(Class<? extends BaseEntity> baseClass) {
//        Entity en = baseClass.getAnnotation(Entity.class);
//        RequireUtil.requireStringNonBlank(en.table());
//        return en.table();
//    }
//
//    /**
//     * 生成SQL语句
//     *
//     * @param baseClass
//     *            实体类
//     * @param fields
//     *            查询字段
//     * @param conditionSql
//     *            条件语句
//     * @param sortKey
//     *            排序字段
//     * @param sortDir
//     *            排序方式 若sortKey无值则无效
//     * @param limit
//     *            限制数量 负数表示则无限制
//     * @return SELECT fields FROM tablename WHERE 1=1 conditionSql ORDER BY
//     *         sortKey sortDir LIMIT limit
//     */
//    public static String generateSql(String fields, String tablename, String conditionSql, String sortKey,
//            String sortDir, int limit) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT ");
//        sb.append(fields);
//        sb.append(" FROM ");
//        sb.append(tablename);
//        sb.append(" WHERE 1=1 ");
//        if (StringUtil.isNotBlank(conditionSql)) {
//            sb.append(conditionSql);
//        }
//        if (StringUtil.isNotBlank(sortKey) && StringUtil.isNotBlank(sortDir)) {
//            String[] sortKeys = sortKey.split(",");
//            String[] sortDirs = sortDir.split(",");
//            if (sortKeys != null) {
//                sb.append(" ORDER BY ");
//                for (int i = 0, len = sortKeys.length; i < len; i++) {
//                    sb.append(sortKeys[i]);
//                    sb.append(" ");
//                    if (sortDirs != null && sortDirs.length >= i + 1) {
//                        sb.append(sortDirs[i]);
//                    }
//                    sb.append(",");
//                }
//                sb.deleteCharAt(sb.length() - 1);
//            }
//        }
//        if (StringUtil.isNotBlank(sortKey)) {
//            sb.append(" ORDER BY ");
//            sb.append(sortKey);
//            sb.append(" ");
//            sb.append(sortDir);
//        }
//        if (limit >= 0) {
//            sb.append(" LIMIT " + limit);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成SQL语句 获取数据总数
//     *
//     * @param tablename
//     * @param conditionSql
//     * @return SELECT COUNT(0) FROM tablename WHERE 1=1 conditionSql
//     */
//    public static String generateCountSql(String tablename, String conditionSql) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT COUNT(0)");
//        sb.append(" FROM ");
//        sb.append(tablename);
//        sb.append(" WHERE 1=1 ");
//        if (StringUtil.isNotBlank(conditionSql)) {
//            sb.append(conditionSql);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成SQL语句
//     *
//     * @param baseClass
//     *            实体类
//     * @param fields
//     *            查询字段
//     * @param conditionSql
//     *            条件语句
//     * @param sortKey
//     *            排序字段
//     * @param sortDir
//     *            排序方式
//     * @return SELECT fields FROM tablename WHERE 1=1 conditionSql ORDER BY
//     *         sortKey sortDir
//     */
//    public static String generateSql(Class<? extends BaseEntity> baseClass, String fields, String conditionSql,
//            String sortKey, String sortDir) {
//        return generateSql(fields, getTablenameByClass(baseClass), conditionSql, sortKey, sortDir, -1);
//    }
//
//    /**
//     * 生成SQL语句
//     *
//     * @param tablename
//     *            数据表名
//     * @param fields
//     *            查询字段
//     * @param conditionSql
//     *            条件语句
//     * @return SELECT fields FROM tablename WHERE 1=1 conditionSql
//     */
//    public static String generateSql(String fields, String tablename, String conditionSql) {
//        return generateSql(fields, tablename, conditionSql, null, null, -1);
//    }
//
//    /**
//     * 生成SQL语句
//     *
//     * @param fields
//     *            查询字段
//     * @param baseClass
//     *            实体类
//     * @param conditionSql
//     *            条件语句
//     * @return SELECT fields FROM tablename WHERE 1=1 conditionSql
//     */
//    public static String generateSql(String fields, Class<? extends BaseEntity> baseClass, String conditionSql) {
//        return generateSql(fields, getTablenameByClass(baseClass), conditionSql, null, null, -1);
//    }
//
//    /**
//     * 生成SQL语句
//     *
//     * @param tablename
//     *            数据表名
//     * @param fields
//     *            查询字段
//     * @param conditionSql
//     *            条件语句
//     * @param sortKey
//     *            排序字段
//     * @param sortDir
//     *            排序方式
//     * @return SELECT fields FROM tablename WHERE 1=1 conditionSql ORDER BY
//     *         sortDir
//     */
//    public static String generateSql(String fields, String tablename, String conditionSql, String sortKey,
//            String sortDir) {
//        return generateSql(fields, tablename, conditionSql, sortKey, sortDir, -1);
//    }
//
//    /**
//     * 生成SQL语句
//     *
//     * @param tablename
//     *            表名
//     * @param returnfield
//     *            查询的字段
//     * @param infield
//     *            通过查询的字段
//     * @return SELECT returnfield FROM tablename WHERE infield = ?
//     */
//    public static String sqlQueryString(String tablename, String returnfield, String infield) {
//        return "SELECT " + returnfield + " FROM " + tablename + " WHERE " + infield + " = ?";
//    }
//
//    /**
//     * 生成SQL语句 获取数据总数
//     *
//     * @param baseClass
//     * @param conditionSql
//     * @return SELECT COUNT(1) FROM tablename WHERE 1=1 conditionSql
//     */
//    public static String generateCountSql(Class<? extends BaseEntity> baseClass, String conditionSql) {
//        return generateCountSql(getTablenameByClass(baseClass), conditionSql);
//    }
//
//    /**
//     * @param tablename
//     *            表名
//     * @param keyfield
//     *            字段
//     * @param guidlist
//     *            删除字符列表
//     * @return DELETE FROM tablename WHERE keyfield IN ('guidlistStrs')
//     */
//    public static String sqlDeleteInIds(String tablename, String keyfield, List<String> guidlist) {
//        return "DELETE FROM " + tablename + " WHERE " + keyfield + " IN ('" + StringUtils.join(guidlist, "', '") + "')";
//    }
//
//    /**
//     * @param tablename
//     *            表名
//     * @param keyfield
//     *            字段
//     * @param guidlist
//     *            删除字符列表
//     * @return DELETE FROM tablename WHERE keyfield IN ('guidlistStrs')
//     */
//    public static String sqlDeleteInIds(Class<? extends BaseEntity> baseClass, String keyfield, List<String> guidlist) {
//        return sqlDeleteInIds(getTablenameByClass(baseClass), keyfield, guidlist);
//    }
//
//    /**
//     *  加上排序后缀
//     *  @param sql
//     *  @param sortField
//     *  @param sortOrder
//     *  @return    sql order by sortField sortOrder
//     */
//    public static String generateSortSql(String sortField, String sortOrder) {
//        if (StringUtil.isNotBlank(sortField)) {
//            return " ORDER BY " + sortField + " " + sortOrder;
//        }
//        return "";
//    }
//
//}
