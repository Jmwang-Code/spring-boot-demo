package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * GreenplumJDBCAdapter类用于适配Greenplum数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与Greenplum相关的数据库操作。
 * </p>
 */
public class GreenplumJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建GreenplumJDBCAdapter实例。
     *
     * @param hostname     Greenplum服务器的主机名
     * @param port         Greenplum服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public GreenplumJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取Greenplum的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:pivotal:greenplum://" + super.hostname + ":" + super.port + ";DatabaseName=" + super.databaseName
                + ";UseUnicode=true;CharacterEncoding=UTF-8;AutoReconnect=true";
    }
}