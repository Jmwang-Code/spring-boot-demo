package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * DerbyJDBCAdapter类用于适配Derby数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与Derby数据库相关的连接操作。
 * </p>
 */
public class DerbyJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建DerbyJDBCAdapter实例。
     *
     * @param databaseName 数据库名称
     */
    public DerbyJDBCAdapter(String databaseName) {
        super(null, null, databaseName, null, null);
    }

    /**
     * 获取Derby数据库的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:derby:" + super.databaseName;
    }
}