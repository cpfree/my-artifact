package com.github.cpfniliu.dal.source;

import com.github.cpfniliu.common.lang.ActionExecException;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;

/**
 * <b>Description : </b> 数据库连接池
 *
 * @author CPF
 * Date: 2020/7/1 16:21
 */
@Slf4j
public class DataSource {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Getter
    private static DataSourceConfig dataSourceConfig;

    private static DataSourceAdapter dataSourceAdapter;

    public static void register(DataSourceConfig config, DataSourceAdapter dataSourceAdapter) {
        // check
        log.info("加载数据源信息 config {}", config);
        if (StringUtils.isAnyBlank(config.getJdbcUrl(), config.getDriverClass(), config.getUser(), config.getPassword())) {
            throw new RuntimeException("数据源连接信息不全 ==> " + config.toString());
        }
        Validate.isTrue(config.getInitialPoolSize() > 0, "初始化数据库连接池需要大于0");
        Validate.isTrue(config.getMaxPoolSize() >= config.getMinPoolSize(), "最大线程池不能小于最小线程池数量");
        Validate.isTrue(config.getMinPoolSize() >= 0, "最小线程池数量不能小于0");
        Validate.isTrue(config.getMaxPoolSize() >= 1, "最大线程池数量不能小于1");
        Validate.isTrue(config.getAcquireIncrement() >= 1, "AcquireIncrement 不能小于1");
        DataSource.dataSourceConfig = config;
        DataSource.dataSourceAdapter = dataSourceAdapter;
        dataSourceAdapter.registerDataSource(dataSourceConfig);
    }

    /**
     * 获取Connection连接
     */
    public static Connection getConnection() {
        try {
            return dataSourceAdapter.getConnection();
        } catch (SQLException e) {
            log.error("获取连接失败", e);
        }
        throw new RuntimeException("获取连接失败");
    }

    /**
     * 获取Connection连接
     */
    public static Connection getLocalConnection() {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * dbUtils 不需要关resultset
     * 释放 Connection 连接
     */
    public static void releaseLocalConnection(){
        final Connection connection = threadLocal.get();
        releaseConnection(connection);
        threadLocal.remove();
    }

    public static void beginTransaction() {
        beginTransaction(getLocalConnection());
    }

    public static void commitTransaction() {
        commitTransaction(threadLocal.get());
    }

    public static void rollBackTransaction() {
        rollBackTransaction(threadLocal.get());
    }

    /**
     * @param booleanSupplier
     */
    public static boolean transaction(BooleanSupplier booleanSupplier) {
        return transaction(getLocalConnection(), booleanSupplier);
    }

    public static void beginTransaction(@NonNull Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("transaction begin failure");
            throw new ActionExecException("开始事物", e);
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            if (connection == null) {
                throw new NullPointerException("事物提交失败, 事物为null");
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("transaction commit failure");
            throw new ActionExecException("事物提交", e);
        }
    }

    public static void rollBackTransaction(Connection connection) {
        try {
            if (connection == null) {
                throw new NullPointerException("事物回滚失败, 事物为null");
            }
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("transaction rollback failure");
            throw new ActionExecException("事物回滚", e);
        }
    }

    /**
     * @param booleanSupplier
     */
    public static boolean transaction(Connection connection, BooleanSupplier booleanSupplier) {
        beginTransaction(connection);
        boolean asBoolean = booleanSupplier.getAsBoolean();
        if (asBoolean) {
            commitTransaction(connection);
        } else {
            rollBackTransaction(connection);
        }
        return asBoolean;
    }

    /**
     * dbUtils 不需要关 resultset
     * 释放 Connection 连接
     */
    public static void releaseConnection(Connection connection){
        try {
            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            log.error("数据连接关闭发生错误", e);
        }
    }

}
