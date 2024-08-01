package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * MogDBJDBCAdapter类用于适配MogDB数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与MogDB相关的数据库操作。
 * </p>
 */
public class MogDBJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建MogDBJDBCAdapter实例。
     *
     * @param hostname     MogDB服务器的主机名
     * @param port         MogDB服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public MogDBJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取MogDB的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        // 请根据实际情况修改连接字符串
        return "jdbc:mogdb://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }
}