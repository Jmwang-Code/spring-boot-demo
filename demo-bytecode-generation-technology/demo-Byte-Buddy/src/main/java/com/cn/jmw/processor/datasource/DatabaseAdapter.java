package com.cn.jmw.processor.datasource;

import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;

import java.util.List;

public interface DatabaseAdapter {

    /**
     * 获取数据库连接字符串
     */
    String getConnectionString();

    /**
     * 测试数据库连接
     */
    boolean testConnection();

    /**
     * 获取数据库用户名
     */
    String getUsername();

    /**
     * 获取数据库密码
     */
    String getPassword();

    /**
     * 获取数据库元数据
     */
    List<DatabaseEntity> getDatabaseMetadata();

    /**
     * 获取数据库版本
     */
    String getDatabaseVersion();

    /**
     * 获取忽略的数据库列表
     */
    List<String> getIgnoreDatabaseList();

    /**
     * getDatabaseType
     */
    DatabaseEnum getDatabaseType();
}
