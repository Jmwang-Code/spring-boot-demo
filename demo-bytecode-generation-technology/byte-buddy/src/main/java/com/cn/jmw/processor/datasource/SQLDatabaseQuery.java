package com.cn.jmw.processor.datasource;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SQLDatabaseQuery {
    /**
     * 查询批
     */
    List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException;

    /**
     * 查询流
     */
    void queryStream(String sql, ResultSetHandler handler) throws SQLException;

}
