package com.cn.jmw.processor.datasource.pojo;

import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.nosql.query.NoSQLQuery;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class JDBCConnectionEntity {

    //数据库类型 1:MYSQL
    private DatabaseEnum dbType;

    //数据库连接信息 1:127.0.0.1
    private String assetIp;

    //数据库连接信息 1:3306
    private Integer port;

    //数据库连接信息 1:bds-cloud
    private String dbName;

    //账号 1:root
    private String username;

    //密码 1:123456
    private String password;

    //SQL 1:select * from table
    private String sql;

    //参数 1: ?
    private Object[] params;

    //手机号 1: 12345678901
    private String phone;

    //部门id 1:1
    private Long deptId;

    //负责人 1:张三
    private String responsiblePerson;

    //资产描述 1:资产描述
    private String assetDesc;

    //资产用户
    private String assetUser;

    //NoSQLQuery
    private NoSQLQuery noSQLQuery;

    public JDBCConnectionEntity(DatabaseEnum dbType, String assetIp, Integer port, String dbName, String username, String password) {
        this.dbType = dbType;
        this.assetIp = assetIp;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    private String driverClassName;
    private String jdbcString;

}
