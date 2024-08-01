package com.cn.jmw.processor.datasource.jdbc.dialect.pojo;

import com.cn.jmw.processor.datasource.jdbc.dialect.enums.SQLFunctionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jooq.Field;
import org.jooq.impl.DSL;
/**
 * QueryField类用于表示SQL查询中的字段信息，包括字段名、别名和函数类型。
 * <p>
 * 此类支持为字段指定别名和函数类型，用于在查询构建过程中提供更多信息。
 * </p>
 */
@Data
@AllArgsConstructor
public class QueryField {
    // 字段名
    String field;
    // 字段别名
    String fieldAlias;
    // 函数类型，可以是Doris支持的任何函数
    SQLFunctionEnum function;

    /**
     * 构造一个QueryField实例，仅使用字段名。
     *
     * @param field 字段名
     */
    public QueryField(String field) {
        this.field = field;
    }

    /**
     * 构造一个QueryField实例，使用字段名和字段别名。
     *
     * @param field 字段名
     * @param fieldAlias 字段别名
     */
    public QueryField(String field, String fieldAlias) {
        this.field = field;
        this.fieldAlias = fieldAlias;
    }

    /**
     * 将QueryField转换为jOOQ的Field对象，并根据表别名生成合格字段。
     *
     * @param tableAlias 表别名
     * @return jOOQ的Field对象，表示此字段
     */
    public Field<?> toField(String tableAlias) {
        String qualifiedField;
        if (tableAlias != null && !tableAlias.isEmpty()) {
            qualifiedField = "`" + tableAlias + "`.`" + field + "`"; // 使用表别名构建合格字段
        } else {
            qualifiedField = "`" + field + "`"; // 仅使用字段名
        }
        if (fieldAlias != null && !fieldAlias.isEmpty()) {
            return DSL.field(qualifiedField).as(fieldAlias); // 返回带别名的字段
        } else {
            return DSL.field(qualifiedField); // 返回无别名的字段
        }
    }
}