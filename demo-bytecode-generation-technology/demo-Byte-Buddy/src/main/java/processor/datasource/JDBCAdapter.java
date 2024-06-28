package processor.datasource;

import processor.datasource.enums.DatabaseEnum;
import processor.datasource.factory.DataSourceConnectionPool;
import processor.datasource.pojo.ColumnEntity;
import processor.datasource.pojo.DatabaseEntity;
import processor.datasource.pojo.TableEntity;
import com.alibaba.druid.pool.DruidDataSource;
import jodd.util.StringUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.exception.enums.StructuredErrorCodeConstants.*;
import static common.exception.util.ServiceExceptionUtil.exception;

public abstract class JDBCAdapter extends Database implements SQLDatabaseQuery {

    public QueryRunner runner;
    public static DataSourceConnectionPool pool = DataSourceConnectionPool.getInstance();

    public JDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, StringUtil.isBlank(databaseName)?"":databaseName, username, password);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(getConnectionString());
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        //ID 为 hostname+port+databaseName
        if (StringUtil.isBlank(databaseName)) {
            databaseName = "";
        }
        pool.addDataSource(hostname + port + databaseName, dataSource);
        this.runner = new QueryRunner(dataSource);
    }

    @Override
    public boolean testConnection() {
        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        List<String> ignoreDatabases = getIgnoreDatabaseList();

        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            DatabaseMetaData metaData = connection.getMetaData();

            try (ResultSet catalogs = metaData.getCatalogs()) {
                while (catalogs.next()) {
                    String databaseName = catalogs.getString("TABLE_CAT");

                    // Skip if the database is in the ignore list
                    if (ignoreDatabases.contains(databaseName)) {
                        continue;
                    }

                    DatabaseEntity databaseEntity = new DatabaseEntity();
                    databaseEntity.setDatabaseName(databaseName);
                    //存入DatabaseType
                    databaseEntity.setDatabaseEnum(getDatabaseType());
                    List<TableEntity> tableEntities = new ArrayList<>();

                    try (ResultSet tables = metaData.getTables(databaseName, null, "%", new String[]{"TABLE"})) {
                        while (tables.next()) {
                            String tableName = tables.getString("TABLE_NAME");
                            TableEntity tableEntity = new TableEntity();
                            tableEntity.setTableName(tableName);
                            List<ColumnEntity> columnEntities = new ArrayList<>();

                            try (ResultSet cols = metaData.getColumns(databaseName, null, tableName, "%")) {
                                while (cols.next()) {
                                    ColumnEntity columnEntity = new ColumnEntity();
                                    columnEntity.setColumnName(cols.getString("COLUMN_NAME"));
                                    columnEntity.setColumnType(cols.getString("TYPE_NAME"));
                                    columnEntities.add(columnEntity);
                                }
                            }

                            tableEntity.setColumns(columnEntities);
                            tableEntities.add(tableEntity);
                        }
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
    public String getDatabaseVersion() {
        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            DatabaseMetaData metaData = connection.getMetaData();
            return metaData.getDatabaseProductVersion();
        } catch (Exception e) {
            throw exception(UNABLE_TO_RETRIEVE_DATABASE_VERSION);
        }
    }

    @Override
    public List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException {
        throw exception(NOT_IMPLEMENTED_METHOD);
    }

    /**
     * Query data using stream
     */
    @Override
    public void queryStream(String sql, ResultSetHandler handler) throws SQLException {
        try (Connection connection = pool.getConnection(hostname+port+databaseName);
             PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            statement.setFetchSize(Integer.MIN_VALUE); // This is important for MySQL to enable streaming
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    handler.handle(resultSet);
                }
            }
        }
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList();
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        throw exception(NOT_IMPLEMENTED_METHOD);
    }
}
