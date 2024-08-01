package com.cn.jmw.processor.datasource.jdbc.dialect;

/**
 * Dialect接口用于定义SQL方言的行为。
 * <p>
 * 该接口规定了获取SQL方言SQL查询的标准方法。
 * 实现此接口的类必须提供特定数据库的SQL命令和语法。
 * </p>
 */
public interface Dialect {

    /**
     * 根据提供的SQLQueryBuilder构建和返回SQL方言的SQL查询。
     *
     * @param queryBuilder 要转换为SQL字符串的SQLQueryBuilder实例。
     * @return 返回构建的SQL字符串，符合特定数据库的语法要求。
     */
    public String getDialectSQL(SQLQueryBuilder queryBuilder);
}