package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * GBase8AJDBCAdapter类用于适配GBase8数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与GBase8相关的数据库操作。
 * </p>
 */
public class GBase8AJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建GBase8AJDBCAdapter实例。
     *
     * @param hostname     GBase8服务器的主机名
     * @param port         GBase8服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public GBase8AJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取GBase8的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:gbase://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }
}