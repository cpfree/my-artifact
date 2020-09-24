package com.github.cpfniliu.dal;

import com.github.cpfniliu.dal.source.DataSource;
import com.github.cpfniliu.dal.source.DataSourceAdapter;
import com.github.cpfniliu.dal.source.DataSourceConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class MainTest {

    public static void main(String[] args) {
        DataSourceConfig config = new DataSourceConfig();
        config.setDriverClass("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/my-project?useUnicode=true&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Hongkong&allowMultiQueries=true");
        config.setUser("root");
        config.setPassword("CPF@4823");
        config.setInitialPoolSize(1);
        config.setMaxPoolSize(3);
        config.setMinPoolSize(1);
        config.setAcquireIncrement(1);

        DataSourceAdapter adapter = new DataSourceAdapter() {
            private ComboPooledDataSource dataSource = null;

            @Override
            public Connection getConnection() throws SQLException {
                return dataSource.getConnection();
            }

            @Override
            public void registerDataSource(DataSourceConfig config) {
                dataSource = new ComboPooledDataSource();
                try {
                    dataSource.setDriverClass(config.getDriverClass());
                    dataSource.setJdbcUrl(config.getJdbcUrl());
                    dataSource.setUser(config.getUser());
                    dataSource.setPassword(config.getPassword());
                    dataSource.setInitialPoolSize(config.getInitialPoolSize());
                    dataSource.setMaxPoolSize(config.getMaxPoolSize());
                    dataSource.setMinPoolSize(config.getMinPoolSize());
                    dataSource.setAcquireIncrement(config.getAcquireIncrement());
                } catch (PropertyVetoException e) {
                    log.error("数据源初始化发生错误", e);
                    throw new RuntimeException("数据源初始化发生错误", e);
                }
            }
        };
        DataSource.register(config, adapter);

    }

//
//    private static ThreadLocal<Connection> threadLocal;
//
//    private static ComboPooledDataSource dataSource;
//
//    /**
//     * 配置DataSource
//     */
//    static {
//        initDataSource();
//        threadLocal = new ThreadLocal<>();
//    }
//
//    /**
//     * 获取Connection连接
//     */
//    public static Connection getLocalConnection() {
//        Connection connection = threadLocal.get();
//        if (connection == null) {
//            connection = getConnection();
//            threadLocal.set(connection);
//        }
//        return connection;
//    }
//
//    /**
//     * dbUtils 不需要关resultset
//     * 释放 Connection 连接
//     */
//    public static void releaseLocalConnection(){
//        final Connection connection = threadLocal.get();
//        releaseConnection(connection);
//        threadLocal.remove();
//    }
//
//    /**
//     * 获取Connection连接
//     */
//    public static Connection getConnection() {
//        try {
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            log.error("获取连接失败", e);
//        }
//        throw new RuntimeException("获取连接失败");
//    }
//
//    /**
//     * dbUtils 不需要关 resultset
//     * 释放 Connection 连接
//     */
//    public static void releaseConnection(Connection connection){
//        try {
//            if(connection != null){
//                connection.close();
//            }
//        } catch (Exception e) {
//            log.error("数据连接关闭发生错误", e);
//        }
//    }
//
//    public static void beginTransaction() {
//        beginTransaction(getLocalConnection());
//    }
//
//    public static void commitTransaction() {
//        commitTransaction(threadLocal.get());
//    }
//
//    public static void rollBackTransaction() {
//        rollBackTransaction(threadLocal.get());
//    }
//
//    public static void beginTransaction(@NonNull Connection connection) {
//        try {
//            connection.setAutoCommit(false);
//        } catch (SQLException e) {
//            log.error("transaction begin failure");
//            throw new ActionExecException("开始事物", e);
//        }
//    }
//
//    public static void commitTransaction(Connection connection) {
//        try {
//            if (connection == null) {
//                throw new NullPointerException("事物提交失败, 事物为null");
//            }
//            connection.commit();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            log.error("transaction commit failure");
//            throw new ActionExecException("事物提交", e);
//        }
//    }
//
//    public static void rollBackTransaction(Connection connection) {
//        try {
//            if (connection == null) {
//                throw new NullPointerException("事物回滚失败, 事物为null");
//            }
//            connection.rollback();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            log.error("transaction rollback failure");
//            throw new ActionExecException("事物回滚", e);
//        }
//    }
//
//    /**
//     * @param booleanSupplier
//     */
//    public static boolean transaction(BooleanSupplier booleanSupplier) {
//        return transaction(getLocalConnection(), booleanSupplier);
//    }
//
//    /**
//     * @param booleanSupplier
//     */
//    public static boolean transaction(Connection connection, BooleanSupplier booleanSupplier) {
//        beginTransaction(connection);
//        boolean asBoolean = booleanSupplier.getAsBoolean();
//        if (asBoolean) {
//            commitTransaction(connection);
//        } else {
//            rollBackTransaction(connection);
//        }
//        return asBoolean;
//    }



}
