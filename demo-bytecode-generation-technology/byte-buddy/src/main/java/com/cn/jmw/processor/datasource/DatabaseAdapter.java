package com.cn.jmw.processor.datasource;

import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;
import java.util.List;

/**
 * DatabaseAdapter接口用于定义与数据库相关的操作。
 * 该接口提供与数据库连接、元数据检索和数据库信息获取的方法。
 */
public interface DatabaseAdapter {
    /**
     * 获取数据库连接字符串。
     *
     * @return 数据库连接字符串
     */
    String getConnectionString();

    /**
     * 测试数据库连接。
     *
     * @return 如果连接有效，返回true；否则返回false
     */
    boolean testConnection();

    /**
     * 获取数据库用户名。
     *
     * @return 数据库用户名
     */
    String getUsername();

    /**
     * 获取数据库密码。
     *
     * @return 数据库密码
     */
    String getPassword();

    /**
     * 获取数据库的元数据。
     *
     * @return 数据库实体列表，包含数据库中的表信息
     */
    List<DatabaseEntity> getDatabaseMetadata();

    /**
     * 获取数据库版本信息。
     *
     * @return 数据库版本字符串
     */
    String getDatabaseVersion();

    /**
     * 获取需要忽略的数据库名称列表。
     *
     * @return 需要忽略的数据库名称列表
     */
    List<String> getIgnoreDatabaseList();

    /**
     * 获取数据库类型。
     *
     * @return 数据库类型枚举
     */
    DatabaseEnum getDatabaseType();
}