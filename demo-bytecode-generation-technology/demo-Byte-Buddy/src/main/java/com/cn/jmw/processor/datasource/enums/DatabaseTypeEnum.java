package com.cn.jmw.processor.datasource.enums;

public enum DatabaseTypeEnum {

    NOSQL("NoSQL"),
    SQL("SQL");

    private final String databaseCategory;

    DatabaseTypeEnum(String databaseCategory) {
        this.databaseCategory = databaseCategory;
    }

    public String getDatabaseCategory() {
        return databaseCategory;
    }
}
