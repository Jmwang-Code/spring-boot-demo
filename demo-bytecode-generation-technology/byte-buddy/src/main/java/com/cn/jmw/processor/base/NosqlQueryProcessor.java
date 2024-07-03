package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;
import com.cn.jmw.processor.datasource.NoSqlAdapter;
import com.cn.jmw.processor.datasource.factory.DatabaseAdapterFactory;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.util.List;

/**
 * Nosql查询处理器
 */
public class NosqlQueryProcessor extends BaseProcessor<JDBCConnectionEntity, List> {
    @Override
    public List process(JDBCConnectionEntity input, Object... data) throws Exception {
        NoSqlAdapter adapterNosql = DatabaseAdapterFactory.getNoSQLAdapter(input);
        return adapterNosql.query(input.getNoSQLQuery());
    }
}
