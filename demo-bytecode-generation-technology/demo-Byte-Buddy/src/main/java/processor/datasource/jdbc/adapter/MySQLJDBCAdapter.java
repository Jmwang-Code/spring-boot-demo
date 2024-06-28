package processor.datasource.jdbc.adapter;

import processor.datasource.JDBCAdapter;
import processor.datasource.enums.DatabaseEnum;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MySQLJDBCAdapter extends JDBCAdapter {

    public MySQLJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    /**
     * Query data using batch
     */
    public List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException {
        return super.runner.query(pool.getConnection(hostname+port+databaseName), sql, new MapListHandler(), params);
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("information_schema", "mysql", "performance_schema", "sys");
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.MYSQL;
    }
}