package com.cn.jmw.processor.datasource.pojo;

import lombok.Data;

/**
 * ColumnEntity类用于表示数据库表中的列信息。
 * <p>
 * 该类包含列名和列类型的基本信息。
 * </p>
 */
@Data
public class ColumnEntity {
    /**
     * 列名。
     */
    private String columnName;

    /**
     * 列类型。
     */
    private String columnType;
}