package processor.datasource.jdbc.adapter;

import processor.datasource.JDBCAdapter;

public class GreenplumJDBCAdapter extends JDBCAdapter {

    public GreenplumJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:pivotal:greenplum://" + super.hostname + ":" + super.port + ";DatabaseName=" + super.databaseName
                + ";UseUnicode=true;CharacterEncoding=UTF-8;AutoReconnect=true";
    }
}