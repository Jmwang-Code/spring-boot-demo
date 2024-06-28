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

public class ClickHouseJDBCAdapter extends JDBCAdapter {

    public ClickHouseJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:clickhouse://" + super.hostname + ":" + super.port + "/" + super.databaseName + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true&compress=0";
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.CLICKHOUSE;
    }

    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        List<String> ignoreDatabases = getIgnoreDatabaseList();

        try (Connection connection = DriverManager.getConnection(getConnectionString(), username, password)) {
            // 获取所有数据库
            try (PreparedStatement ps = connection.prepareStatement("SELECT name FROM system.databases"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String databaseName = rs.getString("name");

                    // Skip if the database is in the ignore list
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

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("INFORMATION_SCHEMA","information_schema","system","mydatabase");
    }
}