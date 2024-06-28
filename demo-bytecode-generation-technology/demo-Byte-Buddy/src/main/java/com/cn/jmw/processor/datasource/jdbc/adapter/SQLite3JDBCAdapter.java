package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

public class SQLite3JDBCAdapter extends JDBCAdapter {

    public SQLite3JDBCAdapter(String databaseName) {
        super(null, null, databaseName, null, null);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:sqlite:" + super.databaseName;
    }
}