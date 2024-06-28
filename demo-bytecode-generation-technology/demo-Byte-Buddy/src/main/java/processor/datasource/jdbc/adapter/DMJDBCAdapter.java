package processor.datasource.jdbc.adapter;

import processor.datasource.JDBCAdapter;
import processor.datasource.enums.DatabaseEnum;
import processor.datasource.pojo.ColumnEntity;
import processor.datasource.pojo.DatabaseEntity;
import processor.datasource.pojo.TableEntity;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static common.exception.enums.StructuredErrorCodeConstants.UNABLE_TO_RETRIEVE_DATABASE_METADATA;
import static common.exception.util.ServiceExceptionUtil.exception;

public class DMJDBCAdapter extends JDBCAdapter {

    public DMJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:dm://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.DM;
    }

    @Override
    public List<DatabaseEntity> getDatabaseMetadata(){
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        List<String> ignoreDatabases = getIgnoreDatabaseList();

        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getSchemas();

            while (catalogs.next()){
                String schemaName = catalogs.getString("TABLE_SCHEM");
//                System.out.println("Schema Name: " + schemaName);

                // Skip if the database is in the ignore list
                if (ignoreDatabases.contains(schemaName)) {
                    continue;
                }

                // 创建一个新的DatabaseEntity对象，并添加到databaseEntities列表中
                DatabaseEntity databaseEntity = new DatabaseEntity();
                databaseEntity.setDatabaseName(schemaName);
                databaseEntity.setDatabaseEnum(getDatabaseType());

                List<TableEntity> tableEntities = new ArrayList<>();

                // 获取并打印所有表的信息
                ResultSet tables = metaData.getTables(null, schemaName, "%", new String[] {"TABLE"});
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
//                    System.out.println("Table Name: " + tableName);

                    // 创建一个新的TableEntity对象
                    TableEntity tableEntity = new TableEntity();
                    tableEntity.setTableName(tableName);
                    List<ColumnEntity> columnEntities = new ArrayList<>();

                    // 获取并打印列的信息
                    ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String columnType = columns.getString("TYPE_NAME");
//                        System.out.println("    Column Name: " + columnName + ", Column Type: " + columnType);

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