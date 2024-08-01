package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import org.apache.commons.dbutils.handlers.MapListHandler;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * MySQLJDBCAdapter类用于适配MySQL数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与MySQL相关的数据库操作。
 * </p>
 */
public class MySQLJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建MySQLJDBCAdapter实例。
     *
     * @param hostname     MySQL服务器的主机名
     * @param port         MySQL服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public MySQLJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }
    /**
     * 获取MySQL的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }
    /**
     * 使用批量查询数据。
     *
     * @param sql    要执行的SQL查询
     * @param params 查询参数
     * @return 返回查询结果列表，包含每行数据的映射
     * @throws SQLException 如果SQL执行失败
     */
    public List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException {
        return super.runner.query(pool.getConnection(hostname + port + databaseName), sql, new MapListHandler(), params);
    }
    /**
     * 获取需要忽略的数据库列表。
     *
     * @return 返回一个包含被忽略的数据库名称的列表
     */
    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("information_schema", "mysql", "performance_schema", "sys");
    }
    /**
     * 获取当前数据库的类型。
     *
     * @return 返回数据库枚举类型
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.MYSQL;
    }
}