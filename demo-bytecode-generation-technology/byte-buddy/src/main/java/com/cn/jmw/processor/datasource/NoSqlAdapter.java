package com.cn.jmw.processor.datasource;

/**
 * NoSqlAdapter是一个抽象类，用于定义与NoSQL数据库的适配器。
 * 该类扩展了Database类，并实现了NoSqlDatabaseQuery接口。
 * <p>
 * 该类提供构造函数以初始化与NoSQL数据库的连接参数，如主机名、端口、数据库名、用户名和密码。
 * </p>
 */
public abstract class NoSqlAdapter extends Database implements NoSqlDatabaseQuery {
    /**
     * 构造函数用于创建NoSqlAdapter实例。
     *
     * @param hostname     数据库主机名
     * @param port         数据库端口
     * @param databaseName 数据库名称
     * @param username     数据库用户名
     * @param password     数据库密码
     */
    public NoSqlAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }
}