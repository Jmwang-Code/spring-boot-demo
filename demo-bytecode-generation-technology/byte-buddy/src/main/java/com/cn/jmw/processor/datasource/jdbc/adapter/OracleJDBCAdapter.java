package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;

/**
 * OracleJDBCAdapter类用于适配Oracle数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与Oracle相关的数据库操作。
 * </p>
 */
public class OracleJDBCAdapter extends JDBCAdapter {

    /**
     * 构造函数用于创建OracleJDBCAdapter实例。
     *
     * @param hostname     Oracle服务器的主机名
     * @param port         Oracle服务器的端口号
     * @param sid          Oracle数据库的SID
     * @param username     用户名
     * @param password     密码
     */
    public OracleJDBCAdapter(String hostname, Integer port, String sid, String username, String password) {
        super(hostname, port, sid, username, password);
    }

    /**
     * 获取Oracle的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:oracle:thin:@" + super.hostname + ":" + super.port + ":" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    }

    /**
     * 获取当前数据库的类型。
     *
     * @return 返回数据库枚举类型
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.ORACLE;
    }
}