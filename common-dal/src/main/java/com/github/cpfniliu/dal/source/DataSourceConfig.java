package com.github.cpfniliu.dal.source;

import lombok.Data;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/1 16:21
 */
@Data
public class DataSourceConfig {

    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String password;
    private int initialPoolSize;
    private int maxPoolSize;
    private int minPoolSize;
    private int acquireIncrement;

}
