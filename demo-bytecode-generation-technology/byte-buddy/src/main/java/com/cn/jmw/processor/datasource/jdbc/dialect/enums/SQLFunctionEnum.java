package com.cn.jmw.processor.datasource.jdbc.dialect.enums;


/**
 * QueryFunctionEnum 枚举表示 SQL 查询中可用的函数。
 * <p>
 * 此枚举包含各种聚合和日期函数，以及相应的描述和参数数量。
 * </p>
 */
public enum SQLFunctionEnum {
    /**
     * 返回 expr 表达式的最小值。
     */
    MIN("MIN", "最小值", "返回expr表达式的最小值", 1),

    /**
     * 返回 expr 表达式的最大值。
     */
    MAX("MAX", "最大值", "返回expr表达式的最大值", 1),

    /**
     * 返回选中字段所有值的和。
     */
    SUM("SUM", "求和", "用于返回选中字段所有值的和", 1),

    /**
     * 返回 expr 表达式的平均值。
     */
    AVG("AVG", "平均值", "返回expr表达式的平均值", 1),

    /**
     * 计算加权算术平均值。
     * 返回结果为所有对应数值和权重的乘积相累加，除以总的权重和。
     * 如果所有的权重和等于0，将返回NaN。
     */
    AVG_WEIGHTED("AVG_WEIGHTED", "平均加权值", "计算加权算术平均值, 即返回结果为: 所有对应数值和权重的乘积相累加，除总的权重和。 如果所有的权重和等于0, 将返回NaN", 1),

    /**
     * 返回满足要求的行的数目。
     */
    COUNT("COUNT", "记录条数", "用于返回满足要求的行的数目", 1),

    /**
     * 将列中数据视为枚举值，统计每个枚举值的个数。
     * 返回各个列枚举值的个数，以及非 null 值的个数与 null 值的个数。
     * 输入至少填写一个输入，值为字符串（STRING）类型的列。
     * 输出为一个 JSONArray 字符串。
     */
    COUNT_BY_ENUM("COUNT_BY_ENUM", "枚举计数", "将列中数据看作枚举值，统计每个枚举值的个数。返回各个列枚举值的个数，以及非 null 值的个数与 null 值的个数。输入至少填写一个输入。值为字符串（STRING）类型的列。输出一个 JSONArray 字符串", 1),

    /**
     * 入参 DATETIME 返回日期的秒数。
     */
    SECOND("SECOND", "获取日期秒数", "入参DATETIME返回日期的秒数", 1),

    /**
     * 入参 DATETIME 返回日期的分钟数。
     */
    MINUTE("MINUTE", "获取日期分钟", "入参DATETIME返回日期的分钟数", 1),

    /**
     * 入参 DATETIME 返回日期的小时。
     */
    HOUR("HOUR", "获取日期小时", "入参DATETIME返回日期的小时", 1),

    /**
     * 入参 DATETIME 返回日期的天数。
     */
    DAY("DAY", "获取日期天数", "入参DATETIME返回日期的天数", 1),

    /**
     * 入参 DATETIME 返回日期的季度。
     */
    QUARTER("QUARTER", "获取日期季度", "入参DATETIME返回日期的季度", 1),

    /**
     * 入参 DATETIME 返回日期的月份。
     */
    MONTH("MONTH", "获取日期月份", "入参DATETIME返回日期的月份", 1),

    /**
     * 入参 DATETIME 返回日期的年份。
     */
    YEAR("YEAR", "获取日期年份", "入参DATETIME返回日期的年份", 1),

    /**
     * 返回当前时间。
     */
    NOW("NOW", "获取当前时间", "返回当前时间", 0),

    /**
     * 返回当前日期。
     */
    CURDATE("CURDATE", "获取当前日期", "返回当前日期", 0),

    /**
     * 返回当前时间。
     */
    CURTIME("CURTIME", "获取当前时间", "返回当前时间", 0),

    /**
     * 入参 DATETIME 返回指定日期是一年中的第几天。
     */
    DAYOFYEAR("DAYOFYEAR", "获取日期一年第几天", "入参DATETIME返回指定日期是一年中的第几天", 1),

    /**
     * 入参 DATETIME 返回指定日期是一个月中的第几天。
     */
    DAYOFMONTH("DAYOFMONTH", "获取日期一月第几天", "入参DATETIME返回指定日期是一个月中的第几天", 1),

    /**
     * 入参 DATETIME 返回指定日期是一周中的第几天。
     */
    DAYOFWEEK("DAYOFWEEK", "获取日期一周第几天", "入参DATETIME返回指定日期是一周中的第几天", 1),

    /**
     * 入参 DATETIME 返回日期所在月份的最后一天。
     */
    LAST_DAY("LAST_DAY", "获取当前月最后一天", "入参DATETIME返回日期所在月份的最后一天", 1);

    private final String function;
    private final String functionName;
    private final String description;
    private final int numParams;

    /**
     * QueryFunctionEnum 构造函数。
     *
     * @param function    函数名称
     * @param description 函数描述
     * @param numParams   函数所需参数数量
     */
    SQLFunctionEnum(String function, String functionName, String description, int numParams) {
        this.function = function;
        this.functionName = functionName;
        this.description = description;
        this.numParams = numParams;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getFunction() {
        return function;
    }

    public String getDescription() {
        return description;
    }

    public int getNumParams() {
        return numParams;
    }


    /**
     * 通过functionName查询枚举
     *
     * @param functionName 函数名称
     * @return 对应的QueryFunctionEnum枚举，如果未找到，则返回null
     */
    public static SQLFunctionEnum getQueryFunctionEnum(String functionName) {
        for (SQLFunctionEnum functionEnum : SQLFunctionEnum.values()) {
            if (functionEnum.getFunctionName().equals(functionName)) {
                return functionEnum;
            }
        }
        return null;
    }

}

