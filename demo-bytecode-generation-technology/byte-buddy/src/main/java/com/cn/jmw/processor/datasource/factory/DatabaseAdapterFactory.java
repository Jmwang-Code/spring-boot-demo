package com.cn.jmw.processor.datasource.factory;

import com.cn.jmw.processor.datasource.NoSqlAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.jdbc.adapter.*;
import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.nosql.adapter.HiveJDBCAdapter;
import com.cn.jmw.processor.datasource.nosql.adapter.MongoDBJDBCAdapter;
import com.cn.jmw.processor.datasource.pojo.JDBCConnectionEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.DATABASE_INPUT_TYPE_ERROR;
import static com.cn.jmw.common.exception.enums.StructuredErrorCodeConstants.UNSUPPORTED_DATABASE_TYPE;
import static com.cn.jmw.common.exception.util.ServiceExceptionUtil.exception;

/**
 * DatabaseAdapterFactory类用于提供多种数据库的适配器实例。
 * <p>
 * 类根据不同的数据库类型返回相应的JDBC适配器或NoSQL适配器实例。
 * </p>
 */
public class DatabaseAdapterFactory {
    // JDBC适配器字典，存储了数据库类型及其对应的适配器类
    private static final Map<DatabaseEnum, Class<? extends JDBCAdapter>> ADAPTER_MAP = new HashMap<>();

    static {
        // 注册支持的数据库适配器
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
        ADAPTER_MAP.put(DatabaseEnum.KUNDB, KunDBJDBCAdapter.class);
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
        ADAPTER_MAP.put(DatabaseEnum.POLAR, PolarJDBCAdapter.class);
    }

    //NOSQL适配器字典
    private static final Map<DatabaseEnum, Class<? extends NoSqlAdapter>> ADAPTER_NO_MAP = new HashMap<>();

    static {
        // 注册支持的数据库适配器
        ADAPTER_NO_MAP.put(DatabaseEnum.MONGODB, MongoDBJDBCAdapter.class);
    }

    /**
     * 获取SQL适配器。
     *
     * @param dbType          数据库类型
     * @param hostname        数据库主机名
     * @param port            数据库端口号
     * @param databaseName    数据库名称
     * @param username        数据库用户名
     * @param password        数据库密码
     * @return 返回对应的JDBC适配器实例
     * @throws IllegalAccessException 如果无法访问适配器类
     * @throws InstantiationException 如果实例化适配器失败
     */
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

    /**
     * 获取NoSQL适配器。
     *
     * @param dbType          数据库类型
     * @param hostname        数据库主机名
     * @param port            数据库端口号
     * @param databaseName    数据库名称
     * @param username        数据库用户名
     * @param password        数据库密码
     * @return 返回对应的NoSql适配器实例
     * @throws IllegalAccessException 如果无法访问适配器类
     * @throws InstantiationException 如果实例化适配器失败
     */
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
     * 获取SQL适配器。
     *
     * @param jdbcConnectionEntity 数据库连接实体
     * @return 返回JDBC适配器实例
     * @throws IllegalAccessException 如果无法访问适配器类
     * @throws InstantiationException 如果实例化适配器失败
     */
    public static JDBCAdapter getSQLAdapter(JDBCConnectionEntity jdbcConnectionEntity) throws IllegalAccessException, InstantiationException {
        return getSQLAdapter(jdbcConnectionEntity.getDbType(), jdbcConnectionEntity.getAssetIp(), jdbcConnectionEntity.getPort(), jdbcConnectionEntity.getDbName(), jdbcConnectionEntity.getUsername(), jdbcConnectionEntity.getPassword());
    }

    /**
     * 获取NoSQL适配器。
     *
     * @param jdbcConnectionEntity 数据库连接实体
     * @return 返回NoSql适配器实例
     * @throws IllegalAccessException 如果无法访问适配器类
     * @throws InstantiationException 如果实例化适配器失败
     */
    public static NoSqlAdapter getNoSQLAdapter(JDBCConnectionEntity jdbcConnectionEntity) throws IllegalAccessException, InstantiationException {
        return getNoSQLAdapter(jdbcConnectionEntity.getDbType(), jdbcConnectionEntity.getAssetIp(), jdbcConnectionEntity.getPort(), jdbcConnectionEntity.getDbName(), jdbcConnectionEntity.getUsername(), jdbcConnectionEntity.getPassword());
    }

    /**
     * 通过适配器类获取适配器实例。
     *
     * @param adapterClass          适配器类
     * @param hostname              数据库主机名
     * @param port                  数据库端口号
     * @param databaseName          数据库名称
     * @param username              数据库用户名
     * @param password              数据库密码
     * @param <T>                  适配器类型
     * @return 返回对应的适配器实例
     * @throws IllegalAccessException 如果无法访问适配器类
     * @throws InstantiationException 如果实例化适配器失败
     */
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
     * 通过适配器类和连接实体获取具体适配器。
     *
     * @param jdbcConnectionEntity 数据库连接实体
     * @param adapterClass         适配器类
     * @param <T>                 适配器类型
     * @return 返回对应的适配器实例
     * @throws IllegalAccessException 如果无法访问适配器类
     * @throws InstantiationException 如果实例化适配器失败
     */
    public static <T> T getAdapter(JDBCConnectionEntity jdbcConnectionEntity, Class<T> adapterClass) throws IllegalAccessException, InstantiationException {
        return getAdapter(adapterClass, jdbcConnectionEntity.getAssetIp(), jdbcConnectionEntity.getPort(), jdbcConnectionEntity.getDbName(), jdbcConnectionEntity.getUsername(), jdbcConnectionEntity.getPassword());
    }
}
