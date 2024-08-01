package com.cn.jmw.processor.datasource.nosql.query;

import org.bson.conversions.Bson;

/**
 * MongoDBQuery类用于表示针对MongoDB的查询操作。
 * <p>
 * 该类包含了与MongoDB查询相关的参数，例如集合名称和过滤器。
 * </p>
 */
public class MongoDBQuery implements NoSQLQuery {
    /**
     * 集合名称，用于指定要查询的MongoDB集合。
     */
    private String collectionName;

    /**
     * 过滤器，用于指定MongoDB查询的条件。
     */
    private Bson filter;

    // ... 其他参数和方法 ...
}