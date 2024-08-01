package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * GoldenDBJDBCAdapter类用于适配GoldenDB数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与GoldenDB相关的数据库操作。
 * </p>
 */
public class GoldenDBJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建GoldenDBJDBCAdapter实例。
     *
     * @param hostname     GoldenDB服务器的主机名
     * @param port         GoldenDB服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public GoldenDBJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取GoldenDB的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        // 请根据实际情况修改连接字符串
        return "jdbc:goldendb://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }
}