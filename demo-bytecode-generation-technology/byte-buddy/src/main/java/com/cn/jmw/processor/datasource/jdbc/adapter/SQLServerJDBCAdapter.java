package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

public class SQLServerJDBCAdapter extends JDBCAdapter {

    public SQLServerJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:sqlserver://" + super.hostname + ":" + super.port + ";databaseName=" + super.databaseName
                + ";useUnicode=true;characterEncoding=UTF-8;autoReconnect=true";
    }
}