package com.cn.jmw.processor.datasource.jdbc.dialect.pojo;

import com.cn.jmw.processor.datasource.jdbc.dialect.SQLQueryBuilder;
import com.cn.jmw.processor.datasource.jdbc.dialect.enums.SQLFunctionEnum;
import com.cn.jmw.processor.datasource.jdbc.dialect.enums.SQLOperatorEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryCondition类用于表示SQL查询中的条件信息，包括字段名、操作符、值和嵌套条件等。
 * <p>
 * 此类支持构建复杂的SQL查询条件，允许通过添加嵌套条件来实现更灵活的查询构建。
 * </p>
 */
@Data
@NoArgsConstructor
public class QueryCondition {

    @JsonProperty("Field")
    // 参与查询的字段名
    private String field;

    @JsonProperty("Operator")
    // 查询操作符，例如：EQUAL, NOT_EQUAL等
    private SQLOperatorEnum operator;

    @JsonProperty("Value")
    // 查询条件的值
    private Object value;

    @JsonProperty("NestedConditions")
    // 嵌套的查询条件列表
    private List<QueryCondition> nestedConditions;

    @JsonProperty("JoinOperator")
    // 连接操作符，可以是"AND"或"OR"
    private SQLOperatorEnum joinOperator;

    @JsonProperty("Function")
    // 函数，可以是支持的任何查询函数
    private SQLFunctionEnum function;

    @JsonProperty("FunctionParams")
    // 函数参数列表
    private List<Object> functionParams;

    /**
     * 带参数的构造函数，用于创建QueryCondition实例。
     *
     * @param field            查询字段名
     * @param operator         查询操作符
     * @param value            查询条件的值
     * @param nestedConditions 嵌套条件列表
     * @param joinOperator     连接操作符（AND/OR）
     * @param function         函数类型
     * @param functionParams   函数参数列表
     */
    public QueryCondition(
            String field,
            SQLOperatorEnum operator,
            Object value,
            List<QueryCondition> nestedConditions,
            SQLOperatorEnum joinOperator,
            SQLFunctionEnum function,
            List<Object> functionParams
    ) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.nestedConditions = nestedConditions;
        this.joinOperator = joinOperator;
        this.function = function;
        this.functionParams = functionParams;
    }

    /**
     * 构造函数，创建一个AND条件的QueryCondition实例。
     *
     * @param field    查询字段名
     * @param operator 查询操作符
     * @param value    查询条件的值
     */
    public QueryCondition(String field, SQLOperatorEnum operator, Object value) {
        this(field, operator, value, null, SQLOperatorEnum.AND, null, null);
    }

    /**
     * 构造函数，创建一个（AND/OR）条件的QueryCondition实例。
     *
     * @param field        查询字段名
     * @param operator     查询操作符
     * @param value        查询条件的值
     * @param joinOperator 连接操作符（AND/OR）
     */
    public QueryCondition(String field, SQLOperatorEnum operator, Object value, SQLOperatorEnum joinOperator) {
        this(field, operator, value, null, joinOperator, null, null);
    }

    /**
     * 构造函数，创建嵌套条件的QueryCondition实例。
     *
     * @param nestedConditions 嵌套查询条件列表
     * @param joinOperator     连接操作符（AND/OR）
     */
    public QueryCondition(List<QueryCondition> nestedConditions, SQLOperatorEnum joinOperator) {
        this(null, null, null, nestedConditions, joinOperator, null, null);
    }

    /**
     * 构造函数，创建HAVING条件的QueryCondition实例。
     *
     * @param function       函数类型
     * @param field          查询字段名
     * @param operator       查询操作符
     * @param value          查询条件的值
     * @param joinOperator   连接操作符（AND/OR）
     * @param functionParams 函数参数列表
     */
    public QueryCondition(SQLFunctionEnum function, String field, SQLOperatorEnum operator, Object value, SQLOperatorEnum joinOperator, List<Object> functionParams) {
        this(field, operator, value, null, joinOperator, function, functionParams);
    }

    /**
     * 向当前条件添加嵌套条件。
     *
     * @param condition 要添加的嵌套条件
     */
    public void addNestedCondition(QueryCondition condition) {
        if (this.nestedConditions == null) {
            this.nestedConditions = new ArrayList<>();
        }
        this.nestedConditions.add(condition);
    }

    /**
     * 向当前条件添加AND嵌套条件。
     *
     * @param condition 要添加的AND嵌套条件
     */
    public void addAndCondition(QueryCondition condition) {
        condition.setJoinOperator(SQLOperatorEnum.AND);
        addNestedCondition(condition);
    }

    /**
     * 向当前条件添加OR嵌套条件。
     *
     * @param condition 要添加的OR嵌套条件
     */
    public void addOrCondition(QueryCondition condition) {
        condition.setJoinOperator(SQLOperatorEnum.OR);
        addNestedCondition(condition);
    }

    /**
     * 构建函数字段表达式。
     *
     * @return 包含函数表达式的Field对象
     */
    @SuppressWarnings("unchecked")
    private Field<Object> buildFunctionField() {
        // 确保参数数量符合函数要求
        if (functionParams.size() != function.getNumParams()) {
            throw new IllegalArgumentException("Function " + function.getFunction() + " expects " + function.getNumParams() + " parameters");
        }

        // 创建一个空列表用于存储函数参数
        List<Object> params = new ArrayList<>();
        for (Object param : functionParams) {
            // 如果参数是 QueryCondition 对象
            if (param instanceof QueryCondition) {
                // 调用 QueryCondition 对象的 buildConditionAsField 方法构建字段表达式并添加到参数列表中
                params.add(((QueryCondition) param).buildConditionAsField());
            } else {
                // 否则直接添加参数到列表中
                params.add(param);
            }
        }

        // 构建函数表达式，如 'functionName(param1, param2, ...)'
        StringBuilder functionExpression = new StringBuilder(function.getFunction() + "(");
        for (int i = 0; i < params.size(); i++) {
            // 添加参数占位符 '{i}'
            functionExpression.append("{").append(i).append("}");
            if (i < params.size() - 1) {
                // 如果不是最后一个参数，添加逗号分隔符
                functionExpression.append(", ");
            }
        }
        // 添加表达式结尾括号
        functionExpression.append(")");

        // 构建字段表达式并返回
        return DSL.field(functionExpression.toString(), params.toArray());
    }

    /**
     * 根据条件构建字段表达式。
     *
     * @return 执行SQL查询条件的Field对象
     */
    private Field<Object> buildConditionAsField() {
        if (function != null) {
            // 若存在函数，则调用 buildFunctionField 方法构建字段表达式
            return buildFunctionField();
        } else {
            // 若不存在函数，则直接构建字段表达式
            return DSL.field("`" + field + "`");
        }
    }

    /**
     * 构建SQL查询的条件。
     *
     * @param query 当前的SelectJoinStep对象
     * @param table 表名
     * @return 构建的Condition对象
     */
    public Condition buildCondition(SelectJoinStep<Record> query, String table) {
        // 处理嵌套条件
        if (nestedConditions != null && !nestedConditions.isEmpty()) {
            Condition nestedCondition = null;
            for (QueryCondition condition : nestedConditions) {
                Condition builtCondition = condition.buildCondition(query, table);
                if (nestedCondition == null) {
                    nestedCondition = builtCondition;
                } else {
                    if (SQLOperatorEnum.OR == joinOperator) {
                        nestedCondition = nestedCondition.or(builtCondition);
                    } else {
                        nestedCondition = nestedCondition.and(builtCondition);
                    }
                }
            }
            // 检查当前层条件是否有效，如果无效则返回嵌套条件
            if (field != null && operator != null && value != null) {
                Field<Object> fieldExpression = buildConditionAsField();
                Condition currentCondition = null;
                switch (operator) {
                    case EQUAL:
                        currentCondition = fieldExpression.eq(handleValue(value, query));
                        break;
                    case NOT_EQUAL:
                        currentCondition = fieldExpression.ne(handleValue(value, query));
                        break;
                    case GT:
                        currentCondition = fieldExpression.gt(handleValue(value, query));
                        break;
                    case LT:
                        currentCondition = fieldExpression.lt(handleValue(value, query));
                        break;
                    case GE:
                        currentCondition = fieldExpression.ge(handleValue(value, query));
                        break;
                    case LE:
                        currentCondition = fieldExpression.le(handleValue(value, query));
                        break;
                    case LIKE:
                        currentCondition = fieldExpression.like(DSL.val(value.toString()));
                        break;
                    case NOT_LIKE:
                        currentCondition = fieldExpression.notLike(DSL.val(value.toString()));
                        break;
                    case IN_FILE:
                        System.out.println(1);
                    case IN:
                        System.out.println(2);
                        if (value instanceof List<?>) {
                            currentCondition = fieldExpression.in(((List<?>) value).toArray());
                        } else if (value instanceof Object[]) {
                            currentCondition = fieldExpression.in((Object[]) value);
                        } else if (value instanceof SQLQueryBuilder) {
                            SQLQueryBuilder subQuery = (SQLQueryBuilder) value;
                            currentCondition = fieldExpression.in(DSL.field(subQuery.buildSubQuerySQL(DSL.using(query.configuration()))));
                        } else {
                            throw new IllegalArgumentException("“in”运算符的值必须是列表或数组");
                        }
                        break;
                    case NOT_IN_FILE:
                    case NOT_IN:
                        if (value instanceof List<?>) {
                            currentCondition = fieldExpression.notIn(((List<?>) value).toArray());
                        } else if (value instanceof Object[]) {
                            currentCondition = fieldExpression.notIn((Object[]) value);
                        } else if (value instanceof SQLQueryBuilder) {
                            SQLQueryBuilder subQuery = (SQLQueryBuilder) value;
                            currentCondition = fieldExpression.notIn(DSL.field(subQuery.buildSubQuerySQL(DSL.using(query.configuration()))));
                        } else {
                            throw new IllegalArgumentException("“in”运算符的值必须是列表或数组");
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("不支持的操作: " + operator);
                }
                if (nestedCondition != null) {
                    if (joinOperator == SQLOperatorEnum.AND) {
                        return DSL.condition(currentCondition).and(nestedCondition);
                    } else if (joinOperator == SQLOperatorEnum.OR) {
                        return DSL.condition(currentCondition).or(nestedCondition);
                    }
                }
                return DSL.condition(currentCondition);
            } else {
                return nestedCondition;
            }
        } else {
            Condition currentCondition = DSL.trueCondition();
            // 处理当前层条件
            Field<Object> fieldExpression = buildConditionAsField();
            if (field != null && operator != null && value != null) {
                switch (operator) {
                    case EQUAL:
                        currentCondition = fieldExpression.eq(handleValue(value, query));
                        break;
                    case NOT_EQUAL:
                        currentCondition = fieldExpression.ne(handleValue(value, query));
                        break;
                    case GT:
                        currentCondition = fieldExpression.gt(handleValue(value, query));
                        break;
                    case LT:
                        currentCondition = fieldExpression.lt(handleValue(value, query));
                        break;
                    case GE:
                        currentCondition = fieldExpression.ge(handleValue(value, query));
                        break;
                    case LE:
                        currentCondition = fieldExpression.le(handleValue(value, query));
                        break;
                    case LIKE:
                        currentCondition = fieldExpression.like(DSL.val(value.toString()));
                        break;
                    case NOT_LIKE:
                        currentCondition = fieldExpression.notLike(DSL.val(value.toString()));
                        break;
                    case IN:
                        if (value instanceof List<?>) {
                            currentCondition = fieldExpression.in(((List<?>) value).toArray());
                        } else if (value instanceof Object[]) {
                            currentCondition = fieldExpression.in((Object[]) value);
                        } else if (value instanceof SQLQueryBuilder) {
                            SQLQueryBuilder subQuery = (SQLQueryBuilder) value;
                            currentCondition = fieldExpression.in(DSL.field(subQuery.buildSubQuerySQL(DSL.using(query.configuration()))));
                        } else {
                            throw new IllegalArgumentException("“in”运算符的值必须是列表或数组");
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("不支持的操作: " + operator);
                }
            }
            return currentCondition; // 如果没有有效条件，返回一个始终为真的条件
        }
    }

    /**
     * 处理条件值，根据不同类型转换为适合的Field对象。
     *
     * @param value 要处理的值
     * @param query 当前的SelectJoinStep对象
     * @return 返回适合的Field对象
     */
    private Field<Object> handleValue(Object value, SelectJoinStep<Record> query) {
        if (value instanceof SQLQueryBuilder) {
            SQLQueryBuilder subQuery = (SQLQueryBuilder) value;
            DSLContext create = DSL.using(query.configuration());
            return DSL.field("(" + subQuery.buildSubQuerySQL(create) + ")");
        } else if (value instanceof QueryCondition) {
            return ((QueryCondition) value).buildConditionAsField();
        } else {
            return DSL.val(value);
        }
    }
}
