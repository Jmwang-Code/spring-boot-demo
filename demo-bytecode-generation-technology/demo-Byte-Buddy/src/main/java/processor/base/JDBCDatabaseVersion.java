package processor.base;

import processor.BaseProcessor;
import processor.datasource.DatabaseAdapter;
import processor.datasource.factory.DatabaseAdapterFactory;
import processor.datasource.pojo.JDBCConnectionEntity;

/**
 * JDBC 数据库版本处理器
 * <p>
 *     通过数据库连接字符串获取数据库版本
 *     通过 {@link DatabaseAdapter#getDatabaseVersion()} 获取数据库版本
 * </p>
 */
public class JDBCDatabaseVersion extends BaseProcessor<JDBCConnectionEntity, String> {

    @Override
    public String process(JDBCConnectionEntity input, Object... data) throws Exception {
        DatabaseAdapter adapter = DatabaseAdapterFactory.getSQLAdapter(input);
        return adapter.getDatabaseVersion();
    }
}