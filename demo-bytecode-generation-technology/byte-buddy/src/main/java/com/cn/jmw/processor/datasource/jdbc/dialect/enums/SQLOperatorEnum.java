package com.cn.jmw.processor.datasource.jdbc.dialect.enums;

/**
 * SQLOperatorEnum 枚举表示 SQL 查询中可用的操作符。
 * <p>
 * 此枚举提供多种 SQL 操作符，如逻辑操作符、比较操作符及排序操作符，以便在构建 SQL 查询时使用。
 * </p>
 */
public enum SQLOperatorEnum {
    /**
     * 减法操作符
     */
    SUBTRACTION("SUBTRACTION", "-"),

    /**
     * 加法操作符
     */
    ADDITION("ADDITION", "+"),

    /**
     * 逻辑或操作符。
     */
    OR("OR", "逻辑或"),

    /**
     * 逻辑与操作符。
     */
    AND("AND", "逻辑与"),

    /**
     * 等于操作符。
     */
    EQUAL("=", "等于"),

    /**
     * 不等于操作符。
     */
    NOT_EQUAL("!=", "不等于"),

    /**
     * 大于操作符。
     */
    GT(">", "大于"),

    /**
     * 小于操作符。
     */
    LT("<", "小于"),

    /**
     * 大于或等于操作符。
     */
    GE(">=", "大于等于"),

    /**
     * 小于或等于操作符。
     */
    LE("<=", "小于等于"),

    /**
     * LIKE 操作符，用于模糊匹配。
     */
    LIKE("LIKE", "包含"),

    /**
     * NOT LIKE 操作符，用于否定模糊匹配。
     */
    NOT_LIKE("NOT LIKE", "不包含"),

    /**
     * IN 操作符，用于在一组值中匹配。
     */
    IN("IN", "在列表中"),

    /**
     * IN 操作符，用于在一组值中匹配。
     */
    NOT_IN("NOT_IN", "不在列表中"),

    /**
     * IN 操作符，用于在一组值中匹配。
     */
    IN_FILE(" IN ", "在文件中"),

    /**
     * IN 操作符，用于在一组值中匹配。
     */
    NOT_IN_FILE(" NOT IN ", "不在文件中"),

    /**
     * 升序排序操作符。
     */
    ASC("ASC", "升序"),

    /**
     * 降序排序操作符。
     */
    DESC("DESC", "降序"),
    ;

    private String operator;

    private String symbol;

    /**
     * SQLOperatorEnum 构造函数。
     *
     * @param operator 操作符的字符串表示
     */
    SQLOperatorEnum(String operator, String symbol) {
        this.operator = operator;
        this.symbol = symbol;
    }

    /**
     * 获取操作符的字符串表示。
     *
     * @return 操作符的字符串表示
     */
    public String getOperator() {
        return operator;
    }

    public String getSymbol() {
        return symbol;
    }

    public static SQLOperatorEnum getSymbol(String symbol) {
        for (SQLOperatorEnum operatorEnum : SQLOperatorEnum.values()) {
            if (operatorEnum.getSymbol().equals(symbol)) {
                return operatorEnum;
            }
        }
        return null;
    }
}