package com.cn.jmw.processor.datasource.jdbc.dialect.pojo;

import com.cn.jmw.processor.datasource.jdbc.dialect.SQLQueryBuilder;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.impl.DSL;

/**
 * QueryJoin类用于表示SQL查询中的连接操作。
 * <p>
 * 此类支持不同类型的表连接，例如INNER、LEFT、RIGHT和FULL，允许通过构造函数配置连接条件。
 * </p>
 */
public class QueryJoin {
    // 连接类型（例如：INNER、LEFT、RIGHT、FULL）
    private String type;
    // 子查询，如果存在则用于子查询连接
    private SQLQueryBuilder subQuery;
    // 用于直接表连接的表名
    private String table;
    // 表别名
    private String alias;
    // ON条件，即连接条件
    private String onCondition;

    /**
     * 表连接构造函数
     *
     * @param type 连接类型（例如：INNER, LEFT, RIGHT, FULL）
     * @param table 目标表名
     * @param alias 表的别名
     * @param onCondition 连接条件
     */
    public QueryJoin(String type, String table, String alias, String onCondition) {
        this.type = type;
        this.table = table;
        this.alias = alias;
        this.onCondition = onCondition;
    }

    /**
     * 子查询连接的构造函数
     *
     * @param type 连接类型（例如：INNER, LEFT, RIGHT, FULL）
     * @param subQuery 关联的子查询构建器
     * @param alias 表的别名
     * @param onCondition 连接条件
     */
    public QueryJoin(String type, SQLQueryBuilder subQuery, String alias, String onCondition) {
        this.type = type;
        this.subQuery = subQuery;
        this.alias = alias;
        this.onCondition = onCondition;
    }

    /**
     * 将连接转换为jOOQ表对象。
     *
     * @param create DSLContext对象，用于构建jOOQ表
     * @return jOOQ的Table对象，表示此连接的目标表
     */
    public Table<?> toTable(DSLContext create) {
        if (subQuery != null) {
            // 如果存在子查询，则创建子查询的表对象
            return DSL.table("(" + subQuery.buildSQL() + ")").as(alias);
        } else {
            // 否则，创建普通表的表对象
            return DSL.table(table).as(alias);
        }
    }

    /**
     * 获取连接类型。
     *
     * @return 连接类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取ON条件。
     *
     * @return 连接的ON条件
     */
    public String getOnCondition() {
        return onCondition;
    }
}