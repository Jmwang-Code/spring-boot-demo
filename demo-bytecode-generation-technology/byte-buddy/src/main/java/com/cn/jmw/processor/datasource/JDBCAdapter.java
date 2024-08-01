package com.cn.jmw.processor.datasource;

import com.cn.jmw.processor.datasource.factory.DataSourceConnectionPool;
import com.cn.jmw.processor.datasource.pojo.ColumnEntity;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;
import com.cn.jmw.processor.datasource.pojo.TableEntity;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.*;
import java.util.*;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.*;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;


/**
 * JDBCAdapter是一个抽象类，用于定义与关系型数据库的适配器。
 * 该类扩展了Database类，并实现了SQLDatabaseQuery接口。
 * <p>
 * 该类提供构造函数以初始化与数据库的连接参数，如主机名、端口、数据库名、用户名和密码。
 * </p>
 */
public abstract class JDBCAdapter extends Database implements SQLDatabaseQuery {
    public QueryRunner runner; // 用于执行SQL查询的QueryRunner实例
    public static DataSourceConnectionPool pool = DataSourceConnectionPool.getInstance(); // 数据源连接池实例

    /**
     * 构造函数用于创建JDBCAdapter实例。
     *
     * @param hostname     数据库主机名
     * @param port         数据库端口
     * @param databaseName 数据库名称
     * @param username     数据库用户名
     * @param password     数据库密码
     */
    public JDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, StringUtils.isBlank(databaseName) ? "" : databaseName, username, password);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(getConnectionString());
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // ID 为 hostname+port+databaseName
        if (StringUtils.isBlank(databaseName)) {
            databaseName = ""; // 如果数据库名为空，则设置为空字符串
        }
        pool.addDataSource(hostname + port + databaseName, dataSource);
        this.runner = new QueryRunner(dataSource); // 初始化QueryRunner
    }

    /**
     * 测试与数据库的连接是否正常。
     *
     * @return 如果连接有效，返回true，否则返回false
     * @throws Exception 如果无法连接数据库，将抛出异常
     */
    @Override
    public boolean testConnection() {
        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            return connection != null && !connection.isClosed(); // 检查连接是否有效
        } catch (Exception e) {
            throw exception(UNABLE_TO_CONNECT_DATABASE); // 抛出无法连接数据库的异常
        }
    }

    @Override
    public String getUsername() {
        return this.username; // 返回数据库用户名
    }

    @Override
    public String getPassword() {
        return this.password; // 返回数据库密码
    }

    /**
     * 获取数据库元数据信息。
     *
     * @return 数据库实体列表，包含数据库中的表信息
     * @throws SQLException 如果无法检索数据库元数据，将抛出异常
     */
    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        List<String> ignoreDatabases = getIgnoreDatabaseList(); // 获取需要忽略的数据库列表
        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet catalogs = metaData.getCatalogs()) {
                while (catalogs.next()) {
                    String databaseName = catalogs.getString("TABLE_CAT");
                    // 如果数据库在忽略列表中，则跳过
                    if (ignoreDatabases.contains(databaseName)) {
                        continue;
                    }
                    DatabaseEntity databaseEntity = new DatabaseEntity();
                    databaseEntity.setDatabaseName(databaseName);
                    // 存入DatabaseType
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
                                    columnEntity.setColumnName(cols.getString("COLUMN_NAME")); // 获取列名
                                    columnEntity.setColumnType(cols.getString("TYPE_NAME")); // 获取列类型
                                    columnEntities.add(columnEntity);
                                }
                            }
                            tableEntity.setColumns(columnEntities); // 设置表的列信息
                            tableEntities.add(tableEntity);
                        }
                    }
                    databaseEntity.setTables(tableEntities); // 设置数据库中的表信息
                    databaseEntities.add(databaseEntity);
                }
            }
        } catch (SQLException e) {
            throw exception(UNABLE_TO_RETRIEVE_DATABASE_METADATA); // 抛出无法检索数据库元数据的异常
        }
        return databaseEntities; // 返回数据库实体列表
    }

    /**
     * 获取数据库版本信息。
     *
     * @return 数据库版本字符串
     * @throws Exception 如果无法检索数据库版本，将抛出异常
     */
    @Override
    public String getDatabaseVersion() {
        try (Connection connection = pool.getConnection(hostname + port + databaseName)) {
            DatabaseMetaData metaData = connection.getMetaData();
            return metaData.getDatabaseProductVersion(); // 返回数据库版本
        } catch (Exception e) {
            throw exception(UNABLE_TO_RETRIEVE_DATABASE_VERSION); // 抛出无法检索数据库版本的异常
        }
    }

    /**
     * 执行批量查询操作，尚未实现。
     *
     * @param sql    要执行的SQL查询语句
     * @param params 查询参数数组，可能为null
     * @throws SQLException 如果数据库访问错误或其他错误
     */
    @Override
    public List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException {
        throw exception(NOT_IMPLEMENTED_METHOD); // 抛出未实现方法的异常
    }

    /**
     * 使用流执行查询操作。
     *
     * @param sql    要执行的SQL查询语句
     * @param handler 处理结果集的处理器
     * @param <T>    返回类型
     * @return 查询结果，由ResultSetHandler处理
     * @throws SQLException 如果数据库访问错误或其他错误
     */
    @Override
    public <T> T queryStream(String sql, ResultSetHandler<T> handler) throws SQLException {
        T results = null;
        try (Connection connection = pool.getConnection(hostname + port + databaseName);
             PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            statement.setFetchSize(Integer.MIN_VALUE); // 这是MySQL流式查询的重要设置
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T result = handler.handle(resultSet); // 处理结果集
                    results = result; // 更新结果
                }
            }
        }
        return results; // 返回最终结果
    }

    /**
     * 获取需要忽略的数据库列表。
     *
     * @return 需要忽略的数据库名称列表
     */
    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList(); // 默认返回空列表
    }

    /**
     * 获取数据库类型，尚未实现。
     *
     * @return 数据库类型枚举
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        throw exception(NOT_IMPLEMENTED_METHOD); // 抛出未实现方法的异常
    }
}