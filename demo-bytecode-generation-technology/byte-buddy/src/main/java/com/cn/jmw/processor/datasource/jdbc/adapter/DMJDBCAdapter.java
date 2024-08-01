package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.pojo.ColumnEntity;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;
import com.cn.jmw.processor.datasource.pojo.TableEntity;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.UNABLE_TO_RETRIEVE_DATABASE_METADATA;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

/**
 * DMJDBCAdapter类用于适配DM数据库连接。
 * <p>
 * 该类扩展了JDBCAdapter，提供了与DM相关的数据库操作，包括获取数据库元数据。
 * </p>
 */
public class DMJDBCAdapter extends JDBCAdapter {
    /**
     * 构造函数用于创建DMJDBCAdapter实例。
     *
     * @param hostname     DM服务器的主机名
     * @param port         DM服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public DMJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取DM数据库的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:dm://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    /**
     * 获取数据库类型。
     *
     * @return 返回数据库类型枚举，表示当前数据库为DM
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.DM;
    }

    /**
     * 获取数据库元数据，包括所有数据库、表及其列的信息。
     *
     * @return  返回数据库实体列表，包含数据库及其表的详细信息
     */
    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        List<String> ignoreDatabases = getIgnoreDatabaseList();
        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getSchemas();
            while (catalogs.next()) {
                String schemaName = catalogs.getString("TABLE_SCHEM");
                // 跳过在忽略列表中的数据库
                if (ignoreDatabases.contains(schemaName)) {
                    continue;
                }
                // 创建一个新的DatabaseEntity对象，并添加到databaseEntities列表中
                DatabaseEntity databaseEntity = new DatabaseEntity();
                databaseEntity.setDatabaseName(schemaName);
                databaseEntity.setDatabaseEnum(getDatabaseType());
                List<TableEntity> tableEntities = new ArrayList<>();

                // 获取所有表的信息
                ResultSet tables = metaData.getTables(null, schemaName, "%", new String[]{"TABLE"});
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    // 创建一个新的TableEntity对象
                    TableEntity tableEntity = new TableEntity();
                    tableEntity.setTableName(tableName);
                    List<ColumnEntity> columnEntities = new ArrayList<>();

                    // 获取列的信息
                    ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String columnType = columns.getString("TYPE_NAME");
                        // 创建一个新的ColumnEntity对象，并添加到columnEntities列表中
                        ColumnEntity columnEntity = new ColumnEntity();
                        columnEntity.setColumnName(columnName);
                        columnEntity.setColumnType(columnType);
                        columnEntities.add(columnEntity);
                    }
                    tableEntity.setColumns(columnEntities);
                    tableEntities.add(tableEntity);
                }
                databaseEntity.setTables(tableEntities);
                databaseEntities.add(databaseEntity);
            }
        } catch (SQLException e) {
            throw exception(UNABLE_TO_RETRIEVE_DATABASE_METADATA);
        }
        return databaseEntities;
    }
}