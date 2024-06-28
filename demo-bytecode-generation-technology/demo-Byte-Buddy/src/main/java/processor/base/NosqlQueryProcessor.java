package processor.base;

import processor.BaseProcessor;
import processor.datasource.NoSqlAdapter;
import processor.datasource.factory.DatabaseAdapterFactory;
import processor.datasource.pojo.JDBCConnectionEntity;

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
