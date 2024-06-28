package com.cn.jmw.processor.datasource.factory;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源连接池
 * 需要符合JDBC规范
 */
public class DataSourceConnectionPool {
    //连接池
    private static DataSourceConnectionPool instance;
    //德鲁伊数据源缓存池
    private final Map<String, DruidDataSource> dataSourceMap;
    //连接缓存池
    private final Map<String, Connection> connectionMap;

    /**
     * 热点连接（最近访问的连接）会被移动到队列的尾部，而冷点连接（最近最少访问的连接）会被移动到队列的头部。
     * 当队列满了，最老的元素（也就是队列头部的元素）会被移除，并且对应的数据库连接会被关闭。
     */
    private DataSourceConnectionPool() {
        this.dataSourceMap = new LinkedHashMap<>();
        this.connectionMap = new LinkedHashMap<String, Connection>(16, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Connection> eldest) {
                boolean shouldRemove = size() > 16;
                if (shouldRemove) {
                    try {
                        eldest.getValue().close();
                    } catch (SQLException e) {
                        throw new RuntimeException("Failed to close connection", e);
                    }
                }
                return shouldRemove;
            }
        };
    }

    //单例模式
    public static synchronized DataSourceConnectionPool getInstance() {
        if (instance == null) {
            instance = new DataSourceConnectionPool();
        }
        return instance;
    }

    //添加数据
    public void addDataSource(String id, DruidDataSource dataSource) {
        dataSourceMap.put(id, dataSource);
    }

    /**
     * getConnection方法首先尝试从connectionMap中获取连接。
     * 如果获取的连接为null或已经被关闭，那么它会从数据源中获取一个新的连接，并将其添加到connectionMap中。
     * 这样，每次访问一个连接（无论是获取还是添加），该连接都会被移动到队列的尾部。
     */
    public Connection getConnection(String id) throws SQLException {
        DruidDataSource dataSource = dataSourceMap.get(id);
        if (dataSource == null) {
            throw new SQLException("No available DataSource with id: " + id);
        }
        Connection connection = connectionMap.get(id);
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
            connectionMap.put(id, connection);
        }
        return connection;
    }

    //关闭连接·
    public void closeConnection(String id) throws SQLException {
        Connection connection = connectionMap.get(id);
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connectionMap.remove(id);
        }
    }

    //关闭所有连接池
    public void closeAllConnections() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        connectionMap.clear();
    }
}