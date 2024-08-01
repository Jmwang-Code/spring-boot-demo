package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;

/**
 * PolarJDBCAdapter类用于适配Polar数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与Polar相关的数据库操作。
 * </p>
 */
public class PolarJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建PolarJDBCAdapter实例。
     *
     * @param hostname     Polar服务器的主机名
     * @param port         Polar服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public PolarJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取Polar的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    /**
     * 获取当前数据库的类型。
     *
     * @return 返回数据库枚举类型
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.POLAR;
    }
}