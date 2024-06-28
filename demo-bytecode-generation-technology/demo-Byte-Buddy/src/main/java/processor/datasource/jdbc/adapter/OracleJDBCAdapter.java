package processor.datasource.jdbc.adapter;

import processor.datasource.JDBCAdapter;


public class OracleJDBCAdapter extends JDBCAdapter {

    public OracleJDBCAdapter(String hostname, Integer port, String sid, String username, String password) {
        super(hostname, port, sid, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:oracle:thin:@//" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

}