package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

public class DerbyJDBCAdapter extends JDBCAdapter {

    public DerbyJDBCAdapter(String databaseName) {
        super(null, null, databaseName, null, null);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:derby:" + super.databaseName;
    }
}