package com.cn.jmw.processor.datasource;

import com.cn.jmw.processor.datasource.nosql.query.NoSQLQuery;

import java.util.List;

public interface NoSqlDatabaseQuery {

    // NO query
    List query(NoSQLQuery noSQLQuery);
}
