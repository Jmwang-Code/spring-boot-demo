package com.cn.jmw.processor.datasource.enums;

/**
 * DatabaseTypeEnum 枚举用于表示数据库类型的分类。
 * <p>
 * 该枚举提供了两种主要的数据库类别：NoSQL 和 SQL，便于在应用程序中进行数据库类型的管理和区分。
 * </p>
 */
public enum DatabaseTypeEnum {
    /**
     * 表示 NoSQL 数据库类型。
     */
    NOSQL("NoSQL"),

    /**
     * 表示 SQL 数据库类型。
     */
    SQL("SQL");

    private final String databaseCategory;

    /**
     * 构造函数用于初始化数据库类型枚举。
     *
     * @param databaseCategory 数据库类别的字符串表示
     */
    DatabaseTypeEnum(String databaseCategory) {
        this.databaseCategory = databaseCategory;
    }

    /**
     * 获取数据库类别的字符串表示。
     *
     * @return 返回数据库类别的描述字符串
     */
    public String getDatabaseCategory() {
        return databaseCategory;
    }
}