package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;

/**
 * SQLServerJDBCAdapter类用于适配SQL Server数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与SQL Server相关的数据库操作。
 * </p>
 */
public class SQLServerJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建SQLServerJDBCAdapter实例。
     *
     * @param hostname     SQL Server服务器的主机名
     * @param port         SQL Server服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public SQLServerJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取SQL Server的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:sqlserver://" + super.hostname + ":" + super.port + ";" +
                "database=" + super.databaseName + ";" +
                "Encrypt=true;trustServerCertificate=true;loginTimeout=30;";
    }

    /**
     * 获取当前数据库的类型。
     *
     * @return 返回数据库枚举类型
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.SQLSERVER;
    }
}