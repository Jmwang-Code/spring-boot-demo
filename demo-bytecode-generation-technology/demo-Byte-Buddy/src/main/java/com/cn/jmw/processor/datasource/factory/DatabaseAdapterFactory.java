package com.cn.jmw.processor.datasource.factory;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.NoSqlAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.jdbc.adapter.*;
import com.cn.jmw.processor.datasource.nosql.adapter.HiveJDBCAdapter;
import com.cn.jmw.processor.datasource.nosql.adapter.MongoDBJDBCAdapter;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.DATABASE_INPUT_TYPE_ERROR;
import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.UNSUPPORTED_DATABASE_TYPE;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

public class DatabaseAdapterFactory {
    //JDBC适配器字典
    private static final Map<DatabaseEnum, Class<? extends JDBCAdapter>> ADAPTER_MAP = new HashMap<>();

    static {
        ADAPTER_MAP.put(DatabaseEnum.ALIYUN_RDS, AliyunRDSJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.ALIYUN_ODPS, AliyunODPSJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.CLICKHOUSE, ClickHouseJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.DB2, DB2JDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.DERBY, DerbyJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.DM, DMJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.DORIS, DorisJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.GAUSSDB, GaussDBJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.GBASE8A, GBase8AJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.GBASE, GBaseJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.GOLDENDB, GoldenDBJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.GREENPLUM, GreenplumJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.HIVE, HiveJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.KINGBASE8, KingBase8JDBCAdapter.class);
//        ADAPTER_MAP.put(DatabaseType.KINGBASE, KingBaseJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.KUNDB, KunDBJDBCAdapter.class);
//        ADAPTER_MAP.put(DatabaseType.MONGODB, MongoDBJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.MYSQL, MySQLJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.OCEANBASE, OceanBaseJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.OPENGAUSS, OpenGaussJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.ORACLE, OracleJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.OSCAR, OSCARJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.POSTGRESQL, PostgreSQLJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.SELECTDB, SelectDBJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.SQLITE3, SQLite3JDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.SQLSERVER, SQLServerJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.STARROCKS, StarRocksJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.SYBASE, SybaseJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.TDSQL, TDSQLJDBCAdapter.class);
        ADAPTER_MAP.put(DatabaseEnum.TIDB, TIDBJDBCAdapter.class);
    }

    //NOSQL适配器字典
    private static final Map<DatabaseEnum, Class<? extends NoSqlAdapter>> ADAPTER_NO_MAP = new HashMap<>();

    static {
        ADAPTER_NO_MAP.put(DatabaseEnum.MONGODB, MongoDBJDBCAdapter.class);
    }

    //获取JDBC适配器
    private static JDBCAdapter getSQLAdapter(DatabaseEnum dbType, String hostname, Integer port, String databaseName, String username, String password) throws IllegalAccessException, InstantiationException {
        if (dbType==null){
            throw exception(DATABASE_INPUT_TYPE_ERROR);
        }
        Class<? extends JDBCAdapter> adapterClass = ADAPTER_MAP.get(dbType);
        if (adapterClass == null) {
            throw exception(UNSUPPORTED_DATABASE_TYPE);
        }
        try {
            return adapterClass.getConstructor(String.class, Integer.class, String.class, String.class, String.class).newInstance(hostname, port, databaseName, username, password);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //获取NOSQL适配器
    private static NoSqlAdapter getNoSQLAdapter(DatabaseEnum dbType, String hostname, Integer port, String databaseName, String username, String password) throws IllegalAccessException, InstantiationException {
        if (dbType==null){
            throw exception(DATABASE_INPUT_TYPE_ERROR);
        }
        Class<? extends NoSqlAdapter> adapterClass = ADAPTER_NO_MAP.get(dbType);
        if (adapterClass == null) {
            throw exception(UNSUPPORTED_DATABASE_TYPE);
        }
        try {
            return adapterClass.getConstructor(String.class, Integer.class, String.class, String.class, String.class).newInstance(hostname, port, databaseName, username, password);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取SQL适配器
     * @param jdbcConnectionEntity 数据库连接实体
     * @return JDBCAdapter
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static JDBCAdapter getSQLAdapter(JDBCConnectionEntity jdbcConnectionEntity) throws IllegalAccessException, InstantiationException {
        return getSQLAdapter(jdbcConnectionEntity.getDbType(), jdbcConnectionEntity.getAssetIp(), jdbcConnectionEntity.getPort(), jdbcConnectionEntity.getDbName(), jdbcConnectionEntity.getUsername(), jdbcConnectionEntity.getPassword());
    }

    /**
     * 获取NoSQL适配器
     * @param jdbcConnectionEntity 数据库连接实体
     * @return NoSqlAdapter
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static NoSqlAdapter getNoSQLAdapter(JDBCConnectionEntity jdbcConnectionEntity) throws IllegalAccessException, InstantiationException {
        return getNoSQLAdapter(jdbcConnectionEntity.getDbType(), jdbcConnectionEntity.getAssetIp(), jdbcConnectionEntity.getPort(), jdbcConnectionEntity.getDbName(), jdbcConnectionEntity.getUsername(), jdbcConnectionEntity.getPassword());
    }


    private static <T> T getAdapter(Class<T> adapterClass, String hostname, Integer port, String databaseName, String username, String password) throws IllegalAccessException, InstantiationException {
        if (adapterClass == null) {
            throw exception(DATABASE_INPUT_TYPE_ERROR);
        }
        for (Map.Entry<DatabaseEnum, Class<? extends JDBCAdapter>> entry : ADAPTER_MAP.entrySet()) {
            if (entry.getValue().equals(adapterClass)) {
                try {
                    return adapterClass.getConstructor(String.class, Integer.class, String.class, String.class, String.class).newInstance(hostname, port, databaseName, username, password);
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (Map.Entry<DatabaseEnum, Class<? extends NoSqlAdapter>> entry : ADAPTER_NO_MAP.entrySet()) {
            if (entry.getValue().equals(adapterClass)) {
                try {
                    return adapterClass.getConstructor(String.class, Integer.class, String.class, String.class, String.class).newInstance(hostname, port, databaseName, username, password);
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw exception(UNSUPPORTED_DATABASE_TYPE);
    }


    /**
     * 通过CLASS模板,获取具体适配器
     * @param adapterClass 适配器类
     * @param jdbcConnectionEntity 数据库连接实体
     * @return 适配器
     * @param <T> 适配器类型
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T getAdapter(JDBCConnectionEntity jdbcConnectionEntity, Class<T> adapterClass) throws IllegalAccessException, InstantiationException {
        return getAdapter(adapterClass, jdbcConnectionEntity.getAssetIp(), jdbcConnectionEntity.getPort(), jdbcConnectionEntity.getDbName(), jdbcConnectionEntity.getUsername(), jdbcConnectionEntity.getPassword());
    }
}
