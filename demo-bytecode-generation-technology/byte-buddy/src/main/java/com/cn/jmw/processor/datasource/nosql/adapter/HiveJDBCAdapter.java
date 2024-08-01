package com.cn.jmw.processor.datasource.nosql.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * HiveJDBCAdapter类用于适配Hive数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与Hive相关的数据库操作。
 * </p>
 */
public class HiveJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建HiveJDBCAdapter实例。
     *
     * @param hostname     Hive服务器的主机名
     * @param port         Hive服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public HiveJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取Hive的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:hive2://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }
}