package com.cn.jmw.processor.datasource.pojo;

import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.nosql.query.NoSQLQuery;
import lombok.*;

/**
 * JDBCConnectionEntity类用于表示JDBC连接的基本信息。
 * <p>
 * 该类包含关于数据库连接的信息，如数据库类型、连接IP、端口号、数据库名称、用户名、密码、SQL语句及相关参数等。
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class JDBCConnectionEntity {
    /**
     * 数据库类型的枚举值，例如：1表示MySQL。
     */
    private DatabaseEnum dbType;

    /**
     * 数据库连接信息，比如：1表示127.0.0.1。
     */
    private String assetIp;

    /**
     * 数据库连接的端口号，例如：1表示3306。
     */
    private Integer port;

    /**
     * 数据库名称，例如：1表示bds-cloud。
     */
    private String dbName;

    /**
     * 数据库用户的账号，例如：1表示root。
     */
    private String username;

    /**
     * 数据库用户的密码，例如：1表示123456。
     */
    private String password;

    /**
     * 要执行的SQL语句，例如：1表示select * from table。
     */
    private String sql;

    /**
     * SQL查询的参数数组，例如：1表示?。
     */
    private Object[] params;

    /**
     * 资产相关的手机号，例如：1表示12345678901。
     */
    private String phone;

    /**
     * 资产所属的部门ID，例如：1表示1。
     */
    private Long deptId;

    /**
     * 资产负责人的名称，例如：1表示张三。
     */
    private String responsiblePerson;

    /**
     * 资产的描述信息，例如：1表示资产描述。
     */
    private String assetDesc;

    /**
     * 资产的用户信息。
     */
    private String assetUser;

    /**
     * 关联的NoSQL查询对象。
     */
    private NoSQLQuery noSQLQuery;

    /**
     * 资产状态，表示当前资产的状态。
     */
    private short AssetStatus;

    /**
     * 构造函数用于创建JDBCConnectionEntity实例。
     *
     * @param dbType     数据库类型的枚举值
     * @param assetIp    数据库连接IP
     * @param port       数据库连接端口
     * @param dbName     数据库名称
     * @param username    数据库用户名
     * @param password    数据库密码
     */
    public JDBCConnectionEntity(DatabaseEnum dbType, String assetIp, Integer port, String dbName, String username, String password) {
        this.dbType = dbType; // 初始化数据库类型
        this.assetIp = assetIp; // 初始化连接IP
        this.port = port; // 初始化端口
        this.dbName = dbName; // 初始化数据库名称
        this.username = username; // 初始化用户名
        this.password = password; // 初始化密码
    }

    // 数据库驱动类名
    private String driverClassName;

    // JDBC连接字符串
    private String jdbcString;
}