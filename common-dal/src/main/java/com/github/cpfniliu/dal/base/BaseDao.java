package com.github.cpfniliu.dal.base;

import com.github.cpfniliu.dal.source.DataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * <b>Description : </b> 仅存放连接相关数据和方法
 *
 * @author CPF
 * Date: 2020/7/12 22:20
 */
public class BaseDao implements AutoCloseable {

    protected Connection connection;

    protected QueryRunner queryRunner = new QueryRunner();

    public BaseDao(Connection connection) {
        if (connection != null) {
            this.connection = connection;
        } else {
            this.connection = DataSource.getConnection();
        }
    }

    public BaseDao() {
        this(null);
    }

    /**
     * 执行命令后自动关闭
     */
    public <R> R autoClose(Function<BaseDao, R> commonDao) {
        final R apply = commonDao.apply(this);
        close();
        return apply;
    }

    /**
     *  开始事务
     */
    public void beginTransaction() {
        DataSource.beginTransaction(connection);
    }

    /**
     *  提交
     */
    public void commitTransaction() {
        DataSource.commitTransaction(connection);
    }

    /**
     *  回滚事务
     */
    public void rollBackTransaction() {
        DataSource.rollBackTransaction(connection);
    }

    /**
     * @param booleanSupplier
     */
    public void transaction(BooleanSupplier booleanSupplier) {
        DataSource.transaction(connection, booleanSupplier);
    }

    @Override
    public void close() {
        DataSource.releaseConnection(connection);
    }
}
