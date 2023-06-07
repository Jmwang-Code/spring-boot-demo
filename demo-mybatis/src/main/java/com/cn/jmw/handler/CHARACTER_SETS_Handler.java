package com.cn.jmw.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义 MyBatis TypeHandler，用于处理 CHARACTER SETS 类型的数据。
 *
 * 注意：该类并未实现具体的数据类型转换逻辑，需要根据实际情况进行实现。
 *
 * @author jmw
 * @date 2023年06月07日 16:44
 * @version 1.0
 */
public class CHARACTER_SETS_Handler extends BaseTypeHandler<Object> {

    /**
     *  方法：用于设置非空参数的值。在执行 SQL 语句时，如果参数的值不为空，则会调用该方法将参数的值设置到预编译的 SQL 语句对象中。
     *
     * @param preparedStatement 预编译的 SQL 语句对象。
     * @param i 参数的位置。
     * @param o 参数的值。
     * @param jdbcType JDBC 类型。
     * @throws SQLException 如果发生 SQL 异常。
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        // TODO: 实现具体的数据类型转换逻辑。
    }

    /**
     *  方法：用于获取可空结果。在执行 SQL 语句时，如果查询结果不为空，则会调用该方法从结果集中获取指定列索引的值。
     *
     * @param resultSet 结果集。
     * @param s 列名。
     * @return 可空结果。
     * @throws SQLException 如果发生 SQL 异常。
     */
    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        // TODO: 实现具体的数据类型转换逻辑。
        return null;
    }

    /**
     *  方法：用于获取可空结果。在执行 SQL 语句时，如果查询结果不为空，则会调用该方法从结果集中获取指定列索引的值。
     *
     * @param resultSet 结果集。
     * @param i 列的索引。
     * @return 可空结果。
     * @throws SQLException 如果发生 SQL 异常。
     */
    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        // TODO: 实现具体的数据类型转换逻辑。
        return null;
    }

    /**
     * 方法：用于获取可空结果。在执行存储过程时，如果查询结果不为空，则会调用该方法从可调用的 SQL 语句对象中获取指定参数位置的值。
     *
     * @param callableStatement 可调用的 SQL 语句对象。
     * @param i 参数的位置。
     * @return 可空结果。
     * @throws SQLException 如果发生 SQL 异常。
     */
    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        // TODO: 实现具体的数据类型转换逻辑。
        return null;
    }
}