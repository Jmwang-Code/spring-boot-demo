package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * SQLite3JDBCAdapter类用于适配SQLite3数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与SQLite3相关的数据库操作。
 * </p>
 */
public class SQLite3JDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建SQLite3JDBCAdapter实例。
     *
     * @param databaseName 数据库名称
     */
    public SQLite3JDBCAdapter(String databaseName) {
        super(null, null, databaseName, null, null);
    }

    /**
     * 获取SQLite3的连接字符串。
     *
     * @return 返回连接字符串
     */
    @Override
    public String getConnectionString() {
        return "jdbc:sqlite:" + super.databaseName;
    }
}