package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * AliyunRDSJDBCAdapter类用于适配Aliyun RDS数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与Aliyun RDS相关的数据库连接和操作。
 * </p>
 */
public class AliyunRDSJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建AliyunRDSJDBCAdapter实例。
     *
     * @param hostname     Aliyun RDS服务器的主机名
     * @param port         Aliyun RDS服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public AliyunRDSJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取Aliyun RDS的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }
}