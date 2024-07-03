package com.cn.jmw.processor.datasource.instantiation;

import java.sql.SQLException;

public interface Instantiation {
    Object instantiate(String databaseName,String tableName) throws SQLException;

}