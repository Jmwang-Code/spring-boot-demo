package com.cn.jmw.processor.datasource;


public abstract class NoSqlAdapter extends Database implements NoSqlDatabaseQuery {

    public NoSqlAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

}