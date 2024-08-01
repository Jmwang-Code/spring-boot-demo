package com.cn.jmw.processor.datasource.jdbc.dialect.pojo;

import com.cn.jmw.processor.datasource.jdbc.dialect.enums.SQLOperatorEnum;
import lombok.Data;

/**
 * QueryOrderBy 类用于表示 SQL 查询中的排序字段及其排序方式。
 * <p>
 * 该类包含字段名和排序类型（升序或降序），用于在查询构建过程中指定排序条件。
 * </p>
 */
@Data
public class QueryOrderBy {
    // 排序字段的名称
    private String field;
    // 排序的方式（如升序、降序）
    private SQLOperatorEnum order;

    /**
     * 构造一个 QueryOrderBy 实例
     *
     * @param field 排序字段的名称
     * @param order 排序的方式（升序或降序）
     */
    public QueryOrderBy(String field, SQLOperatorEnum order) {
        this.field = field;
        this.order = order;
    }
}