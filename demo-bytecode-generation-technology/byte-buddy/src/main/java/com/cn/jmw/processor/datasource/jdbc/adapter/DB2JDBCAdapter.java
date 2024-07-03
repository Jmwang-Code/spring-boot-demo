package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;

public class DB2JDBCAdapter extends JDBCAdapter {

    public DB2JDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:db2://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + ":user=" + super.username + ";password=" + super.password + ";";
    }
}