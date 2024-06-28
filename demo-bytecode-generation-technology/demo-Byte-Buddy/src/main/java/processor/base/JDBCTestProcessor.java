package processor.base;

import processor.BaseProcessor;
import processor.datasource.DatabaseAdapter;
import processor.datasource.factory.DatabaseAdapterFactory;
import processor.datasource.pojo.JDBCConnectionEntity;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC 测试处理器
 * <p>
 *     通过数据库连接字符串测试数据库连接是否正常
 *     连接成功返回 true，否则返回 false
 *     通过 {@link DriverManager} 获取数据库连接
 *     通过 {@link Connection#isClosed()} 判断连接是否关闭
 *     通过 {@link Connection#close()} 关闭连接
 */
public class JDBCTestProcessor extends BaseProcessor<JDBCConnectionEntity, Boolean> {

    @Override
    public Boolean process(JDBCConnectionEntity input, Object... data) throws Exception {
        DatabaseAdapter adapter = DatabaseAdapterFactory.getSQLAdapter(input);
        return adapter.testConnection();
    }
}