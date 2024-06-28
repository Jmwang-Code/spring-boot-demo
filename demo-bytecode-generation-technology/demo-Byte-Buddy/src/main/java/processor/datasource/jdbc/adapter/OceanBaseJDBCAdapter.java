package processor.datasource.jdbc.adapter;

import processor.datasource.JDBCAdapter;
import processor.datasource.enums.DatabaseEnum;

import java.util.Arrays;
import java.util.List;

public class OceanBaseJDBCAdapter extends JDBCAdapter {

    public OceanBaseJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:oceanbase://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.OCEANBASE;
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("information_schema", "mysql", "SYS", "LBACSYS","ORAAUDITOR","oceanbase");
    }
}