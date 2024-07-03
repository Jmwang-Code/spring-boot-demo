package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;
import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.factory.DatabaseAdapterFactory;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.util.List;
import java.util.Map;

public class JDBCQueryBatchProcessor extends BaseProcessor<JDBCConnectionEntity, List<Map<String,Object>>> {

    @Override
    public List<Map<String, Object>> process(JDBCConnectionEntity input, Object... data) throws Exception {
        JDBCAdapter adapter = DatabaseAdapterFactory.getSQLAdapter(input);
        return adapter.queryBatch(input.getSql(), input.getParams());
    }
}