package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;
import com.cn.jmw.processor.datasource.DatabaseAdapter;
import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseTypeEnum;
import com.cn.jmw.processor.datasource.factory.DatabaseAdapterFactory;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.INPUT_GET_DB_TYPE_IS_NULL;
import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.INPUT_IS_NULL;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

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
        if (input == null) {
            throw exception(INPUT_IS_NULL);
        }
        if (input.getDbType() == null) {
            throw exception(INPUT_GET_DB_TYPE_IS_NULL);
        }
        if (input.getDbType().getDatabaseCategory().equals(DatabaseTypeEnum.SQL.getDatabaseCategory())){
            JDBCAdapter sqlAdapter = DatabaseAdapterFactory.getSQLAdapter(input);
            return sqlAdapter.testConnection();
        }
        DatabaseAdapter adapter = DatabaseAdapterFactory.getNoSQLAdapter(input);
        return adapter.testConnection();
    }
}