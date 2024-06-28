package processor.datasource.jdbc.adapter;

import processor.datasource.JDBCAdapter;

public class SQLite3JDBCAdapter extends JDBCAdapter {

    public SQLite3JDBCAdapter(String databaseName) {
        super(null, null, databaseName, null, null);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:sqlite:" + super.databaseName;
    }
}