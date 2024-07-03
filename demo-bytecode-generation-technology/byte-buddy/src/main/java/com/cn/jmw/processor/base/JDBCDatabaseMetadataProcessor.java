package com.cn.jmw.processor.base;

import com.cn.jmw.processor.BaseProcessor;
import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.NoSqlAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseTypeEnum;
import com.cn.jmw.processor.datasource.factory.DatabaseAdapterFactory;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.util.List;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.INPUT_GET_DB_TYPE_IS_NULL;
import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.INPUT_IS_NULL;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 数据库元数据处理器
 * <p>
 *     通过数据库连接字符串获取数据库元数据
 *     数据库元数据包括数据库名、表名、列名
 *     数据库名 -> 表名 -> 列名
 */
public class JDBCDatabaseMetadataProcessor extends BaseProcessor<JDBCConnectionEntity, List<DatabaseEntity>> {


    @Override
    public List<DatabaseEntity> process(JDBCConnectionEntity input, Object... data) throws Exception {
        if (input == null) {
            throw exception(INPUT_IS_NULL);
        }
        if (input.getDbType() == null) {
            throw exception(INPUT_GET_DB_TYPE_IS_NULL);
        }
        if (input.getDbType().getDatabaseCategory().equals(DatabaseTypeEnum.SQL.getDatabaseCategory())){
            JDBCAdapter sqlAdapter = DatabaseAdapterFactory.getSQLAdapter(input);
            return sqlAdapter.getDatabaseMetadata();
        }
        NoSqlAdapter adapter = DatabaseAdapterFactory.getNoSQLAdapter(input);
        return adapter.getDatabaseMetadata();
    }
}