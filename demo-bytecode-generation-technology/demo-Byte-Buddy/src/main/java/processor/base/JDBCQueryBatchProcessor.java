package processor.base;

import processor.BaseProcessor;
import processor.datasource.JDBCAdapter;
import processor.datasource.factory.DatabaseAdapterFactory;
import processor.datasource.pojo.JDBCConnectionEntity;

import java.util.List;
import java.util.Map;

public class JDBCQueryBatchProcessor extends BaseProcessor<JDBCConnectionEntity, List<Map<String,Object>>> {

    @Override
    public List<Map<String, Object>> process(JDBCConnectionEntity input, Object... data) throws Exception {
        JDBCAdapter adapter = DatabaseAdapterFactory.getSQLAdapter(input);
        return adapter.queryBatch(input.getSql(), input.getParams());
    }
}