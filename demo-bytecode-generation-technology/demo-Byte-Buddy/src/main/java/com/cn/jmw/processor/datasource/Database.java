package com.cn.jmw.processor.datasource;

public abstract class Database implements DatabaseAdapter{

    protected String hostname;
    protected Integer port;
    protected String databaseName;
    protected String username;
    protected String password;

    public Database(String hostname, Integer port, String databaseName, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    // ... 其他方法 ...
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
