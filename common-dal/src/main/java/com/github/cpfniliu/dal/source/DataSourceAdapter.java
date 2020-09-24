package com.github.cpfniliu.dal.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/1 16:21
 */
public abstract class DataSourceAdapter {

    /**
     * 获取Connection连接
     */
    public abstract Connection getConnection() throws SQLException;

    public abstract void registerDataSource(DataSourceConfig config);

}
