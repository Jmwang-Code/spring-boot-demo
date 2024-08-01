package com.cn.jmw.processor.datasource.jdbc.dialect;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;


/**
 * 拼SQL
 * 主要是看防止value注入的问题
 *
 *
 * 解析SQL字符串
 * 1.
 * 防止系统函数 系统表 系统命令 全部禁用
 *
 * 2.
 * 黑名单关键表名 字段名 无法使用
 */
public class SQLValidator {

    // 定义白名单（允许的表名和字段名）
    private static final Set<String> ALLOWED_TABLES = new HashSet<>(Arrays.asList("users", "orders", "products"));
    private static final Map<String, Set<String>> ALLOWED_FIELDS = new HashMap<>();

    static {
        ALLOWED_FIELDS.put("users", new HashSet<>(Arrays.asList("id", "name", "email")));
        ALLOWED_FIELDS.put("orders", new HashSet<>(Arrays.asList("id", "user_id", "product_id", "quantity")));
        ALLOWED_FIELDS.put("products", new HashSet<>(Arrays.asList("id", "name", "price")));
    }

    // 定义黑名单（禁止的表名和字段名）
    private static final Set<String> BLACKLISTED_TABLES = new HashSet<>(Arrays.asList("system_users"));
    private static final Set<String> BLACKLISTED_FIELDS = new HashSet<>(Arrays.asList("password", "username"));

    // 验证 SQL 语句
    public static boolean validateSQL(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);

            // 检查是否包含危险的 SQL 操作
            if (statement instanceof Drop || statement instanceof Truncate) {
                decorateAndCalculateLength("禁止的 SQL 操作：DROP 或 TRUNCATE");
                return false;
            }

            // 根据具体需求，验证 SQL 语句的结构和内容
            if (statement instanceof Select) {
                return validateSelect((Select) statement);
            }

            if (statement instanceof Insert) {
                return validateInsert((Insert) statement);
            }

            if (statement instanceof Update) {
                return validateUpdate((Update) statement);
            }

            if (statement instanceof Delete) {
                return validateDelete((Delete) statement);
            }

            // 如果所有验证通过，返回 true
            return true;
        } catch (JSQLParserException e) {
            // SQL 解析失败，说明语句不合法
            decorateAndCalculateLength("SQL 解析失败：" + e.getMessage());
            return false;
        }
    }

    private static boolean validateSelect(Select select) {
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            // 对单个 PlainSelect 进行验证
            return validatePlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) selectBody;
            // 对每个 PlainSelect 进行递归验证
            for (SelectBody body : setOperationList.getSelects()) {
                if (!validateSelectBody(body)) {
                    return false;
                }
            }
            // 对 UNION 的其他验证逻辑
            // ...
            return true;
        }
        return false; // 处理其他类型的 SelectBody
    }

    private static boolean validatePlainSelect(PlainSelect plainSelect) {
        if (!validateFromItem(plainSelect.getFromItem())) {
            return false;
        }

        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                if (!validateFromItem(join.getRightItem())) {
                    return false;
                }
            }
        }

        for (SelectItem selectItem : plainSelect.getSelectItems()) {
            if (!validateSelectItem(selectItem)) {
                return false;
            }
        }

        return validateWhere(plainSelect.getWhere());
    }

    // 添加辅助方法，用于递归验证 SelectBody
    private static boolean validateSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            return validatePlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof SetOperationList) {
            return validateSelect((Select) selectBody);
        }
        return false; // 处理其他类型的 SelectBody
    }

    private static boolean validateInsert(Insert insert) {
        if (!validateTable(insert.getTable())) {
            return false;
        }

        for (Column column : insert.getColumns()) {
            if (!validateColumn(column)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validateUpdate(Update update) {
        if (!validateTable(update.getTable())) {
            return false;
        }

        for (Column column : update.getColumns()) {
            if (!validateColumn(column)) {
                return false;
            }
        }

        return validateWhere(update.getWhere());
    }

    private static boolean validateDelete(Delete delete) {
        return validateTable(delete.getTable()) &&
                validateWhere(delete.getWhere());
    }

    private static boolean validateFromItem(FromItem fromItem) {
        if (fromItem instanceof Table) {
            return validateTable((Table) fromItem);
        }
        // 添加更多的 FromItem 类型验证（如子查询等）...
        return true;
    }

    private static boolean validateTable(Table table) {
        String tableName = table.getName();
        if (BLACKLISTED_TABLES.contains(tableName)) {
            decorateAndCalculateLength("禁止的表名：" + tableName);
            return false;
        }

//        if (!ALLOWED_TABLES.contains(tableName)) {
//            decorateAndCalculateLength("不允许的表名：" + tableName);
//            return false;
//        }

        return true;
    }

    private static boolean validateColumn(Column column) {
        String tableName = column.getTable() != null ? column.getTable().getName() : null;
        String columnName = column.getColumnName();

        if (BLACKLISTED_FIELDS.contains(columnName)) {
            decorateAndCalculateLength("禁止的字段名：" + columnName);
            return false;
        }

//        if (tableName != null && ALLOWED_FIELDS.containsKey(tableName)) {
//            if (!ALLOWED_FIELDS.get(tableName).contains(columnName)) {
//                decorateAndCalculateLength("不允许的字段名："+columnName);
//                return false;
//            }
//        }

        return true;
    }

    private static boolean validateExpression(Expression expression) {
        if (expression instanceof Column) {
            return validateColumn((Column) expression);
        }
        // 添加更多的 Expression 类型验证
        return true;
    }

    private static boolean validateWhere(Expression where) {
        if (where == null) {
            return true;
        }

        // 验证 WHERE 子句中的表达式
        return validateExpression(where);
    }

    private static boolean validateSelectItem(SelectItem selectItem) {
        if (selectItem instanceof SelectExpressionItem) {
            return validateExpression(((SelectExpressionItem) selectItem).getExpression());
        }
        return true;
    }

    @Test
    public void testTextInjection() {
        System.out.println("————————————————————— 文本注入.单引号注入： ———————————————————————");

        String sqlInjectionSingleQuote = "admin' OR '1'='1";
        String vulnerableSQLSingleQuote = "SELECT * FROM users WHERE name = '" + sqlInjectionSingleQuote + "'";
        boolean isValidSingleQuote = SQLValidator.validateSQL(vulnerableSQLSingleQuote);
        assertFalse(isValidSingleQuote);


        System.out.println("————————————————————— 文本注入.注释符注入： ———————————————————————");

        String sqlInjectionComment = "admin'; --";
        String vulnerableSQLComment = "SELECT * FROM users WHERE name = '" + sqlInjectionComment + "'";
        boolean isValidComment = SQLValidator.validateSQL(vulnerableSQLComment);
        assertFalse(isValidComment);


        System.out.println("————————————————————— 文本注入.分号注入： ———————————————————————");

        String sqlInjectionSemicolon = "admin'; TRUNCATE TABLE users; --";
        String vulnerableSQLSemicolon = "SELECT * FROM users WHERE name = '" + sqlInjectionSemicolon + "'";
        boolean isValidSemicolon = SQLValidator.validateSQL(vulnerableSQLSemicolon);
        assertFalse(isValidSemicolon);


        System.out.println("————————————————————— 文本注入.UNION联合查询注入： ———————————————————————");

        String sqlInjectionUnion = "' UNION SELECT null, null, username, password FROM admin --";
        String vulnerableSQLUnion = "SELECT creator, user_id, email FROM users WHERE name = '" + sqlInjectionUnion + "'";
        boolean isValidUnion = SQLValidator.validateSQL(vulnerableSQLUnion);
        assertFalse(isValidUnion);


        System.out.println("————————————————————— 文本注入.时间延迟注入： ———————————————————————");

        String sqlInjectionTime = "'; WAITFOR DELAY '00:00:05'; --";
        String vulnerableSQLTime = "SELECT * FROM users WHERE name = '" + sqlInjectionTime + "'";
        boolean isValidTime = SQLValidator.validateSQL(vulnerableSQLTime);
        assertFalse(isValidTime);


        System.out.println("————————————————————— 文本注入.错误消息注入： ———————————————————————");

        String sqlInjectionError = "'; SELECT 1/0; --";
        String vulnerableSQLError = "SELECT * FROM users WHERE name = '" + sqlInjectionError + "'";
        boolean isValidError = SQLValidator.validateSQL(vulnerableSQLError);
        assertFalse(isValidError);

    }


    @Test
    public void testBooleanInjection() {
        String sqlInjectionBoolean = "' OR '1'='1";
        String vulnerableSQLBoolean = "SELECT * FROM users WHERE name = '" + sqlInjectionBoolean + "'";
        boolean isValidBoolean = SQLValidator.validateSQL(vulnerableSQLBoolean);
        assertFalse(isValidBoolean);

        // 基于注释的注入
        System.out.println("————————————————————— 布尔注入.基于注释的注入： ———————————————————————");
        String sqlInjectionComment = "admin'; --";
        String vulnerableSQLComment = "SELECT * FROM users WHERE name = '" + sqlInjectionComment + "'";
        boolean isValidBoolean2 = SQLValidator.validateSQL(vulnerableSQLComment);
        assertFalse(isValidBoolean2);

        // 基于井号的注入
        System.out.println("————————————————————— 布尔注入.基于井号的注入： ———————————————————————");
        String sqlInjectionHash = "admin' OR '1'='1'#";
        String vulnerableSQLHash = "SELECT * FROM users WHERE name = '" + sqlInjectionHash + "'";
        boolean isValidBoolean4 = SQLValidator.validateSQL(vulnerableSQLHash);
        assertFalse(isValidBoolean4);


        // 基于单引号的注入
        System.out.println("————————————————————— 布尔注入.基于单引号的注入： ———————————————————————");
        String sqlInjectionQuote = "admin' OR '1'='1'";
        String vulnerableSQLQuote = "SELECT * FROM users WHERE name = '" + sqlInjectionQuote + "'";
        boolean isValidBoolean5 = SQLValidator.validateSQL(vulnerableSQLQuote);
        assertFalse(isValidBoolean5);


        // 基于通配符的注入
        System.out.println("————————————————————— 布尔注入.基于通配符的注入： ———————————————————————");
        String sqlInjectionWildcard = "admin' OR name LIKE '%a%'";
        String vulnerableSQLWildcard = "SELECT * FROM users WHERE name = '" + sqlInjectionWildcard + "'";
        boolean isValidBoolean6 = SQLValidator.validateSQL(vulnerableSQLWildcard);
        assertFalse(isValidBoolean6);


        // 基于等号的注入
        System.out.println("————————————————————— 布尔注入.基于等号的注入： ———————————————————————");
        String sqlInjectionEqual = "admin' OR 1=1";
        String vulnerableSQLEqual = "SELECT * FROM users WHERE name = '" + sqlInjectionEqual + "'";
        boolean isValidBoolean7 = SQLValidator.validateSQL(vulnerableSQLEqual);
        assertFalse(isValidBoolean7);


        // 基于分号的注入
        System.out.println("————————————————————— 布尔注入.基于分号的注入： ———————————————————————");
        String sqlInjectionSemicolon = "admin'; TRUNCATE TABLE users; --";
        String vulnerableSQLSemicolon = "SELECT * FROM users WHERE name = '" + sqlInjectionSemicolon + "'";
        boolean isValidBoolean8 = SQLValidator.validateSQL(vulnerableSQLSemicolon);
        assertFalse(isValidBoolean8);


        // 基于恶意关键字的注入
        System.out.println("————————————————————— 布尔注入.基于恶意关键字的注入： ———————————————————————");
        String sqlInjectionKeyword = "admin'; DROP TABLE users; --";
        String vulnerableSQLKeyword = "SELECT * FROM users WHERE name = '" + sqlInjectionKeyword + "'";
        boolean isValidBoolean9 = SQLValidator.validateSQL(vulnerableSQLKeyword);
        assertFalse(isValidBoolean9);
    }

    @Test
    public void testErrorInjection() {
        //基于除零错误的注入
        String sqlInjectionError = "'; SELECT 1/0; --";
        String vulnerableSQLError = "SELECT * FROM users WHERE name = '" + sqlInjectionError + "'";
        boolean isValidError = SQLValidator.validateSQL(vulnerableSQLError);
        assertFalse(isValidError);

        //基于索引错误的注入
        String sqlInjectionIndexOutOfBounds = "'; SELECT column999 FROM users; --";
        String vulnerableSQLIndexOutOfBounds = "SELECT * FROM users WHERE username = '" + sqlInjectionIndexOutOfBounds + "'";
        boolean isValidIndexOutOfBounds = SQLValidator.validateSQL(vulnerableSQLIndexOutOfBounds);
        assertFalse(isValidIndexOutOfBounds);

        //基于类型转换错误的注入
        String sqlInjectionTypeConversion = "'; SELECT CAST(username AS INT) FROM users; --";
        String vulnerableSQLTypeConversion = "SELECT * FROM users WHERE username = '" + sqlInjectionTypeConversion + "'";
        boolean isValidTypeConversion = SQLValidator.validateSQL(vulnerableSQLTypeConversion);
        assertFalse(isValidTypeConversion);

        //基于执行存储过程的注入
        String sqlInjectionExecuteProcedure = "'; EXEC sp_dropuser 'admin'; --";
        String vulnerableSQLExecuteProcedure = "SELECT * FROM users WHERE username = '" + sqlInjectionExecuteProcedure + "'";
        boolean isValidExecuteProcedure = SQLValidator.validateSQL(vulnerableSQLExecuteProcedure);
        assertFalse(isValidExecuteProcedure);
    }

    public static void decorateAndCalculateLength(String input) {
        String[] lines = input.split("\n");
        int maxLength = 0;

        // 计算最长行的长度
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        // 计算装饰框线的宽度
        int width = maxLength + 4;

        // 构建装饰后的字符串
        StringBuilder builder = new StringBuilder();
        builder.append("┌" + repeatCharacter('—', width) + "—┐" + "\n");
        for (int i = 0;i<lines.length;i++) {
                builder.append("│" + padString(lines[i], width) + "\n");
        }
        builder.append("└" + repeatCharacter('—', width) + "—┘" + "\n");

        // 打印装饰后的字符串和计算出的长度
        System.out.println(builder.toString());
    }

    public static String repeatCharacter(char ch, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(ch);
        }
        return builder.toString();
    }

    public static String padString(String input, int width) {
        int inputLength = input.length();
        int paddingSize = width - inputLength - 2; // 减去两侧的'│'
        if (paddingSize < 0) {
            // 如果字符串太长，无法完全居中，可以选择截断或抛出异常
            throw new IllegalArgumentException("Input string is too long to fit in the specified width.");
        }
        int leftPadding = paddingSize / 2;
        int rightPadding = paddingSize - leftPadding;
        // 如果总宽度是奇数，并且剩余的一个空格应该放在右侧（为了保持视觉上的平衡）
        if (paddingSize % 2 != 0) {
            rightPadding++;
            leftPadding--;
        }
        return repeatCharacter(' ', leftPadding) + input + repeatCharacter(' ', rightPadding);
    }
}
