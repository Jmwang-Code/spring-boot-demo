package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.pojo.ColumnEntity;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;
import com.cn.jmw.processor.datasource.pojo.TableEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.UNABLE_TO_RETRIEVE_DATABASE_METADATA;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

/**
 * ClickHouseJDBCAdapter类用于适配ClickHouse数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与ClickHouse相关的数据库操作，包括获取数据库元数据。
 * </p>
 */
public class ClickHouseJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建ClickHouseJDBCAdapter实例。
     *
     * @param hostname     ClickHouse服务器的主机名
     * @param port         ClickHouse服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public ClickHouseJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取ClickHouse的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:clickhouse://" + super.hostname + ":" + super.port + "/" + super.databaseName + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true&compress=0";
    }

    /**
     * 获取数据库类型。
     *
     * @return 返回数据库类型枚举，表示当前数据库为ClickHouse
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.CLICKHOUSE;
    }

    /**
     * 获取数据库元数据，包括所有数据库、表及其列的信息。
     *
     * @return 返回数据库实体列表，包含数据库及其表的详细信息
     */
    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        List<String> ignoreDatabases = getIgnoreDatabaseList();
        try (Connection connection = DriverManager.getConnection(getConnectionString(), username, password)) {
            // 获取所有数据库
            try (PreparedStatement ps = connection.prepareStatement("SELECT name FROM system.databases"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String databaseName = rs.getString("name");
                    // 跳过在忽略列表中的数据库
                    if (ignoreDatabases.contains(databaseName)) {
                        continue;
                    }
                    DatabaseEntity databaseEntity = new DatabaseEntity();
                    databaseEntity.setDatabaseName(databaseName);
                    databaseEntity.setDatabaseEnum(getDatabaseType());
                    List<TableEntity> tableEntities = new ArrayList<>();
                    // 获取指定数据库的所有表
                    try (PreparedStatement psTables = connection.prepareStatement("SELECT name FROM system.tables WHERE database = ?")) {
                        psTables.setString(1, databaseName);
                        ResultSet rsTables = psTables.executeQuery();
                        while (rsTables.next()) {
                            String tableName = rsTables.getString("name");
                            TableEntity tableEntity = new TableEntity();
                            tableEntity.setTableName(tableName);
                            List<ColumnEntity> columnEntities = new ArrayList<>();
                            // 获取指定表的所有列
                            try (PreparedStatement psColumns = connection.prepareStatement("SELECT name, type FROM system.columns WHERE database = ? AND table = ?")) {
                                psColumns.setString(1, databaseName);
                                psColumns.setString(2, tableName);
                                ResultSet rsColumns = psColumns.executeQuery();
                                while (rsColumns.next()) {
                                    ColumnEntity columnEntity = new ColumnEntity();
                                    columnEntity.setColumnName(rsColumns.getString("name"));
                                    columnEntity.setColumnType(rsColumns.getString("type"));
                                    columnEntities.add(columnEntity);
                                }
                                rsColumns.close();
                            }
                            tableEntity.setColumns(columnEntities);
                            tableEntities.add(tableEntity);
                        }
                        rsTables.close();
                    }
                    databaseEntity.setTables(tableEntities);
                    databaseEntities.add(databaseEntity);
                }
            }
        } catch (SQLException e) {
            throw exception(UNABLE_TO_RETRIEVE_DATABASE_METADATA);
        }
        return databaseEntities;
    }

    /**
     * 获取要忽略的数据库列表。
     *
     * @return 返回一个包含不需要检索的数据库名称的列表
     */
    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("INFORMATION_SCHEMA", "information_schema", "system", "mydatabase");
    }
}