package processor.datasource.enums;

import processor.datasource.jdbc.adapter.*;
import processor.datasource.nosql.adapter.*;

/**
 * 数据源映射表
 */
public enum DatabaseEnum {
    ALIYUN_RDS("ALIYUN_RDS", 1, "SQL", AliyunRDSJDBCAdapter.class),
    ALIYUN_ODPS("ALIYUN_ODPS", 2, "NoSQL", AliyunODPSJDBCAdapter.class),
    CLICKHOUSE("CLICKHOUSE", 3, "SQL", ClickHouseJDBCAdapter.class),
    DB2("DB2", 4, "SQL", DB2JDBCAdapter.class),
    DERBY("DERBY", 5, "SQL", DerbyJDBCAdapter.class),
    DM("DM", 6, "SQL", DMJDBCAdapter.class),
    DORIS("DORIS", 7, "SQL", DorisJDBCAdapter.class),
    GAUSSDB("GAUSSDB", 8, "SQL", GaussDBJDBCAdapter.class),
    GBASE8A("GBASE8A", 9, "SQL", GBase8AJDBCAdapter.class),
    GBASE("GBASE", 10, "SQL", GBaseJDBCAdapter.class),
    GOLDENDB("GOLDENDB", 11, "SQL", GoldenDBJDBCAdapter.class),
    GREENPLUM("GREENPLUM", 12, "SQL", GreenplumJDBCAdapter.class),
    HIVE("HIVE", 13, "SQL", HiveJDBCAdapter.class),
    KINGBASE8("KINGBASE8", 14, "SQL", KingBase8JDBCAdapter.class),
    KUNDB("KUNDB", 16, "NoSQL", KunDBJDBCAdapter.class),
    MONGODB("MONGODB", 17, "NoSQL", MongoDBJDBCAdapter.class),
    MYSQL("MYSQL", 18, "SQL", MySQLJDBCAdapter.class),
    OCEANBASE("OCEANBASE", 19, "SQL", OceanBaseJDBCAdapter.class),
    OPENGAUSS("OPENGAUSS", 20, "SQL", OpenGaussJDBCAdapter.class),
    ORACLE("ORACLE", 21, "SQL", OracleJDBCAdapter.class),
    OSCAR("OSCAR", 22, "SQL", OSCARJDBCAdapter.class),
    POSTGRESQL("POSTGRESQL", 23, "SQL", PostgreSQLJDBCAdapter.class),
    SELECTDB("SELECTDB", 24, "SQL", SelectDBJDBCAdapter.class),
    SQLITE3("SQLITE3", 25, "SQL", SQLite3JDBCAdapter.class),
    SQLSERVER("SQLSERVER", 26, "SQL", SQLServerJDBCAdapter.class),
    STARROCKS("STARROCKS", 27, "SQL", StarRocksJDBCAdapter.class),
    SYBASE("SYBASE", 28, "SQL", SybaseJDBCAdapter.class),
    TDSQL("TDSQL", 29, "SQL", TDSQLJDBCAdapter.class),
    TIDB("TIDB", 30, "SQL", TIDBJDBCAdapter.class);

    private final String name;
    private final int type;
    private final String databaseCategory;
    private final Class adapterClass;

    DatabaseEnum(String name, int type, String databaseCategory, Class adapterClass) {
        this.name = name;
        this.type = type;
        this.databaseCategory = databaseCategory;
        this.adapterClass = adapterClass;
    }

    public String getName() {
        return name;
    }

    public int getTypeId() {
        return type;
    }

    public Class getAdapterClass() {
        return adapterClass;
    }

    public String getDatabaseCategory() {
        return databaseCategory;
    }

    //传入Integer类型的typeId，返回对应的DatabaseType
    public static DatabaseEnum getDatabaseType(Integer typeId) {
        for (DatabaseEnum databaseEnum : DatabaseEnum.values()) {
            if (databaseEnum.getTypeId() == typeId) {
                return databaseEnum;
            }
        }
        return null;
    }
}