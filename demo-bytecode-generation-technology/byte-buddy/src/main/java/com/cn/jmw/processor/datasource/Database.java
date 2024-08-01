package com.cn.jmw.processor.datasource;

/**
 * Database是一个抽象类，用于定义与数据库相关的基本信息和操作。
 * 该类实现了DatabaseAdapter接口，并为具体数据库适配器提供了通用的构造函数和字段。
 */
public abstract class Database implements DatabaseAdapter{
    protected String hostname; // 数据库主机名
    protected Integer port; // 数据库端口
    protected String databaseName; // 数据库名称
    protected String username; // 数据库用户名
    protected String password; // 数据库密码

    /**
     * 构造函数用于创建Database实例。
     *
     * @param hostname     数据库主机名
     * @param port         数据库端口
     * @param databaseName 数据库名称
     * @param username     数据库用户名
     * @param password     数据库密码
     */
    public Database(String hostname, Integer port, String databaseName, String username, String password) {
        this.hostname = hostname; // 初始化主机名
        this.port = port; // 初始化端口
        this.databaseName = databaseName; // 初始化数据库名
        this.username = username; // 初始化用户名
        this.password = password; // 初始化密码
    }

    // ... 其他方法 ...

    /**
     * 获取数据库用户名。
     *
     * @return 数据库用户名
     */
    @Override
    public String getUsername() {
        return this.username; // 返回用户名
    }

    /**
     * 获取数据库密码。
     *
     * @return 数据库密码
     */
    @Override
    public String getPassword() {
        return this.password; // 返回密码
    }
}