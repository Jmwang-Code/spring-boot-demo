package com.cn.jmw.processor.datasource.enums;

import com.cn.jmw.processor.datasource.jdbc.adapter.*;
import com.cn.jmw.processor.datasource.nosql.adapter.*;

/**
 * 数据源映射表
 * <p>
 * 该枚举定义了支持的各种数据库类型及其对应的适配器类，便于在应用程序中进行数据库连接的适配。
 * </p>
 */
public enum DatabaseEnum {
    /**
     * 阿里云RDS数据库
     */
    ALIYUN_RDS("ALIYUN_RDS", 1, "SQL", AliyunRDSJDBCAdapter.class),

    /**
     * 阿里云ODPS数据库
     */
    ALIYUN_ODPS("ALIYUN_ODPS", 2, "NoSQL", AliyunODPSJDBCAdapter.class),

    /**
     * ClickHouse数据库
     */
    CLICKHOUSE("CLICKHOUSE", 3, "SQL", ClickHouseJDBCAdapter.class),

    /**
     * DB2数据库
     */
    DB2("DB2", 4, "SQL", DB2JDBCAdapter.class),

    /**
     * Derby数据库
     */
    DERBY("DERBY", 5, "SQL", DerbyJDBCAdapter.class),

    /**
     * DM数据库
     */
    DM("DM", 6, "SQL", DMJDBCAdapter.class),

    /**
     * Doris数据库
     */
    DORIS("DORIS", 7, "SQL", DorisJDBCAdapter.class),

    /**
     * GaussDB数据库
     */
    GAUSSDB("GAUSSDB", 8, "SQL", GaussDBJDBCAdapter.class),

    /**
     * GBase8A数据库
     */
    GBASE8A("GBASE8A", 9, "SQL", GBase8AJDBCAdapter.class),

    /**
     * GBase数据库
     */
    GBASE("GBASE", 10, "SQL", GBaseJDBCAdapter.class),

    /**
     * GoldenDB数据库
     */
    GOLDENDB("GOLDENDB", 11, "SQL", GoldenDBJDBCAdapter.class),

    /**
     * Greenplum数据库
     */
    GREENPLUM("GREENPLUM", 12, "SQL", GreenplumJDBCAdapter.class),

    /**
     * Hive数据库
     */
    HIVE("HIVE", 13, "SQL", HiveJDBCAdapter.class),

    /**
     * KingBase8数据库
     */
    KINGBASE8("KINGBASE8", 14, "SQL", KingBase8JDBCAdapter.class),

    /**
     * KunDB数据库
     */
    KUNDB("KUNDB", 16, "NoSQL", KunDBJDBCAdapter.class),

    /**
     * MongoDB数据库
     */
    MONGODB("MONGODB", 17, "NoSQL", MongoDBJDBCAdapter.class),

    /**
     * MySQL数据库
     */
    MYSQL("MYSQL", 18, "SQL", MySQLJDBCAdapter.class),

    /**
     * OceanBase数据库
     */
    OCEANBASE("OCEANBASE", 19, "SQL", OceanBaseJDBCAdapter.class),

    /**
     * OpenGauss数据库
     */
    OPENGAUSS("OPENGAUSS", 20, "SQL", OpenGaussJDBCAdapter.class),

    /**
     * Oracle数据库
     */
    ORACLE("ORACLE", 21, "SQL", OracleJDBCAdapter.class),

    /**
     * OSCAR数据库
     */
    OSCAR("OSCAR", 22, "SQL", OSCARJDBCAdapter.class),

    /**
     * PostgreSQL数据库
     */
    POSTGRESQL("POSTGRESQL", 23, "SQL", PostgreSQLJDBCAdapter.class),

    /**
     * SelectDB数据库
     */
    SELECTDB("SELECTDB", 24, "SQL", SelectDBJDBCAdapter.class),

    /**
     * SQLite3数据库
     */
    SQLITE3("SQLITE3", 25, "SQL", SQLite3JDBCAdapter.class),

    /**
     * SQL Server数据库
     */
    SQLSERVER("SQLSERVER", 26, "SQL", SQLServerJDBCAdapter.class),

    /**
     * StarRocks数据库
     */
    STARROCKS("STARROCKS", 27, "SQL", StarRocksJDBCAdapter.class),

    /**
     * Sybase数据库
     */
    SYBASE("SYBASE", 28, "SQL", SybaseJDBCAdapter.class),

    /**
     * TDSQL数据库
     */
    TDSQL("TDSQL", 29, "SQL", TDSQLJDBCAdapter.class),

    /**
     * TiDB数据库
     */
    TIDB("TIDB", 30, "SQL", TIDBJDBCAdapter.class),

    /**
     * Polar数据库
     */
    POLAR("POLAR", 31, "SQL", PolarJDBCAdapter.class);

    private final String name;
    private final int type;
    private final String databaseCategory;
    private final Class adapterClass;

    /**
     * 构造函数用于初始化数据库枚举类型。
     *
     * @param name 数据库名称
     * @param type 数据库类型ID
     * @param databaseCategory 数据库分类
     * @param adapterClass 适配器类
     */
    DatabaseEnum(String name, int type, String databaseCategory, Class adapterClass) {
        this.name = name;
        this.type = type;
        this.databaseCategory = databaseCategory;
        this.adapterClass = adapterClass;
    }

    /**
     * 获取数据库名称。
     *
     * @return 返回数据库名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取数据库类型ID。
     *
     * @return 返回类型ID
     */
    public int getTypeId() {
        return type;
    }

    /**
     * 获取适配器类。
     *
     * @return 返回适配器类
     */
    public Class getAdapterClass() {
        return adapterClass;
    }

    /**
     * 获取数据库分类。
     *
     * @return 返回数据库分类
     */
    public String getDatabaseCategory() {
        return databaseCategory;
    }

    /**
     * 根据类型ID获取对应的数据库类型枚举。
     *
     * @param typeId 整型的类型ID
     * @return 返回对应的DatabaseEnum类型
     */
    public static DatabaseEnum getDatabaseType(Integer typeId) {
        for (DatabaseEnum databaseEnum : DatabaseEnum.values()) {
            if (databaseEnum.getTypeId() == typeId) {
                return databaseEnum;
            }
        }
        return null;
    }
}