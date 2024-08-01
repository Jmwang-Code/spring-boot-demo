package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

/**
 * DB2JDBCAdapter类用于适配DB2数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与DB2相关的数据库操作。
 * </p>
 */
public class DB2JDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建DB2JDBCAdapter实例。
     *
     * @param hostname     DB2服务器的主机名
     * @param port         DB2服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public DB2JDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取DB2的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:db2://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + ":user=" + super.username + ";password=" + super.password + ";";
    }
}