package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.instantiation.Instantiation;
import com.cn.jmw.processor.datasource.jdbc.dialect.Dialect;
import com.cn.jmw.processor.datasource.jdbc.dialect.SQLQueryBuilder;
import com.cn.jmw.processor.datasource.jdbc.dialect.enums.SQLFunctionEnum;
import com.cn.jmw.processor.datasource.jdbc.dialect.enums.SQLOperatorEnum;
import com.cn.jmw.processor.datasource.jdbc.dialect.pojo.*;
import com.cn.jmw.processor.datasource.pojo.StreamLoadResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jooq.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class DorisJDBCAdapter extends JDBCAdapter implements Dialect, Instantiation {

    // DORIS HTTP PORT
    private static final int DORIS_HTTP_PORT = 8030;

    /**
     * 构造函数用于创建DorisJDBCAdapter实例。
     *
     * @param hostname     Doris服务器的主机名
     * @param port         Doris服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public DorisJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取Doris的连接字符串。
     *
     * @return 返回连接字符串，包含连接所需的参数
     */
    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    /**
     * 使用批量查询数据。
     *
     * @param sql    执行的SQL语句
     * @param params SQL语句的参数
     * @return 查询结果，返回一个Map列表
     * @throws SQLException 如果发生SQL错误
     */
    public List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException {
        return super.runner.query(pool.getConnection(hostname + port + databaseName), sql, new MapListHandler(), params);
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("information_schema", "__internal_schema");
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.DORIS;
    }

    //非全字段映射
    public static final ObjectMapper objectMapper = new ObjectMapper();

    //全字段映射
    private static final ObjectMapper fullFieldObjectMapper = new ObjectMapper();

    /**
     * 在将 LocalDateTime 对象转换为 JSON 时，ObjectMapper 默认会将其转换为一个包含年、月、日、小时、分钟、秒和纳秒的数组。
     * 这是因为 LocalDateTime 对象包含这些字段，而 ObjectMapper 默认会将对象的每个字段转换为 JSON 的一个元素。
     * 然而，Doris 的 DATETIME 类型需要的是一个格式为 'YYYY-MM-DD HH:MI:SS' 的字符串，而不是一个数组。
     * 因此，当你尝试将这个数组加载到 Doris 的 DATETIME 列时，会失败。
     * 为了解决这个问题，你需要告诉 ObjectMapper 使用一个特定的日期时间格式来序列化 LocalDateTime 对象。你可以使用 DateTimeFormatter 来定义这个格式，然后使用 JavaTimeModule 将这个格式器添加到 ObjectMapper。
     */
    static {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        fullFieldObjectMapper.registerModule(module);
        fullFieldObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    private final static HttpClientBuilder httpClientBuilder = HttpClients
            .custom()
            .setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                protected boolean isRedirectable(String method) {
                    // 如果连接目标是 FE，则需要处理 307 redirect。
                    return true;
                }
            });

    /**
     * 使用流加载将数据加载到 Doris 中。
     *
     * @param json      要加载的 JSON 数据
     * @param tableName 要将数据加载到其中的表的名称
     * @param columns   列的名称关系
     * @throws IOException 如果发生 IO 错误
     */
    private StreamLoadResult streamLoad(String json, String tableName, String columns) throws IOException {
        String url = "http://" + super.hostname + ":" + DORIS_HTTP_PORT + "/api/" + super.databaseName + "/" + tableName + "/_stream_load";

        String loadResult = "";

        try (CloseableHttpClient client = httpClientBuilder.build()) {
            HttpPut put = new HttpPut(url);
            put.setHeader(HttpHeaders.EXPECT, "100-continue");
            put.setHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(username, password));

            // 可以在 Header 中设置 stream load 相关属性，这里我们设置 label 和 column_separator。
            Calendar calendar = Calendar.getInstance();
            String label = String.format("audit_%s%02d%02d_%02d%02d%02d_%s",
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                    UUID.randomUUID().toString().replaceAll("-", ""));
            //指定导入数据格式 csv, json, arrow, csv_with_names
            put.setHeader("format", "json");
            // 用于指定 Doris 该次导入的标签，标签相同的数据无法多次导入
            put.setHeader("label", label);
            // 用于指定导入文件中的列分隔符
            put.setHeader("column_separator", ",");
            // strip_outer_array: 布尔类型，为true表示json数据以数组对象开始且将数组对象中进行展平，默认值是false
            put.setHeader("strip_outer_array", "true");
            if (StringUtils.isNotBlank(columns)) {
                /**
                 * 部分列更新
                 *
                 * Doris 在主键模型的导入更新，提供了可以直接插入或者更新部分列数据的功能，不需要先读取整行数据，这样更新效率就大幅提升了。
                 */
                put.setHeader("partial_columns", "true");
                /**
                 * 设置 columns
                 * 指定导入文件中的列和 table 中的列的对应关系。
                 */
                put.setHeader("columns", columns);
            }

            // 设置导入文件。
            // 这里也可以使用 StringEntity 来传输任意数据。
            StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
            put.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(put)) {
                if (response.getEntity() != null) {
                    loadResult = EntityUtils.toString(response.getEntity());
                }

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IOException(
                            String.format("流加载失败。状态： %s 加载结果: %s", statusCode, loadResult));
                }

                System.out.println("获取加载结果: " + loadResult);
            }
        }
        //loadResult字符串转换成StreamLoadResult
        StreamLoadResult streamLoadResult = objectMapper.readValue(loadResult, StreamLoadResult.class);

        return streamLoadResult;
    }

    /**
     * 生成基本认证的HTTP头部。
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回基本认证的HTTP头部字符串
     */
    private String basicAuthHeader(String username, String password) {
        final String tobeEncode = username + ":" + password;
        byte[] encoded = Base64.encodeBase64(tobeEncode.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encoded);
    }

    /**
     * 将对象列表转换为 JSON 字符串。
     *
     * @param list 要转换的对象列表
     * @return JSON字符串
     * @throws IOException 如果发生 IO 错误
     */
    private String listToJson(List<?> list) throws IOException {
        return objectMapper.writeValueAsString(list);
    }

    /**
     * 将一个大列表分成指定大小进行流加载。
     *
     * @param list     要加载的对象列表
     * @param tableName 要将数据加载到其中的表的名称
     * @param size     每批次的大小
     * @param columns  列的名称关系
     * @return StreamLoadResult数组
     * @throws IOException 如果发生 IO 错误
     */
    public StreamLoadResult[] streamLoadBatch(List<?> list, String tableName, int size, String columns) throws IOException {
        int total = list.size();
        if (list == null || total == 0) {
            return new StreamLoadResult[0];
        }

        int batch = total / size;
        StreamLoadResult[] results = new StreamLoadResult[batch + 1];
        int remainder = total % size;
        int start = 0;
        for (int i = 0; i < batch; i++) {
            List<?> subList = list.subList(start, start + size);
            StreamLoadResult streamLoadResult = streamLoad(listToJson(subList), tableName, columns);
            results[i] = streamLoadResult;
            start += size;
        }
        if (remainder > 0) {
            List<?> subList = list.subList(start, start + remainder);
            StreamLoadResult streamLoadResult = streamLoad(listToJson(subList), tableName, columns);
            results[batch] = streamLoadResult;
        }

        return results;
    }

    /**
     * 将驼峰命名转换为下划线命名。
     *
     * @param str 输入的字符串
     * @return 转换后的字符串
     */
    public static String camelCaseToUnderScore(String str) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        str = str.replaceAll(regex, replacement).toLowerCase();
        return str;
    }

    @Override
    public String getDialectSQL(SQLQueryBuilder queryBuilder) {
        long l = System.currentTimeMillis();
        String sql = queryBuilder.buildSQL();
        log.info("执行耗时：" + (System.currentTimeMillis() - l) + "ms");
        System.out.println();
        System.out.println(sql);
        return sql;
    }

    @Override
    public Object instantiate(String databaseName,String tableName)  {
        try {
            DatabaseMetaData metaData = pool.getConnection(hostname+port+databaseName).getMetaData();
            ResultSet resultSet = metaData.getColumns(databaseName, null, tableName, null);

            // 创建ByteBuddy对象
            ByteBuddy byteBuddy = new ByteBuddy();

            // 开始构建类
            tableName = toCamelCase(tableName);
            //默认没有构造器
            DynamicType.Builder<?> builder = byteBuddy.subclass(Object.class)
                    .name(tableName);

            //记录字段名 fieldNames
            List<String> fieldNames = new ArrayList<>();
            //记录字段类型 fieldTypes
            List<Class<?>> fieldTypes = new ArrayList<>();

            // 使用反射来设置字段的值
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String camelCaseColumnName = toCamelCase(columnName);
                fieldNames.add(camelCaseColumnName);
                String columnType = resultSet.getString("TYPE_NAME");
                fieldTypes.add(sqlTypeToJavaType(columnType));

                // 将SQL类型转换为Java类型
                Class<?> javaType = sqlTypeToJavaType(columnType);

                // 将字段添加到类中
                builder = builder.defineField(camelCaseColumnName, javaType, Modifier.PUBLIC)
                        .defineMethod("get" + capitalize(camelCaseColumnName), javaType, Modifier.PUBLIC)
                        .intercept(FieldAccessor.ofBeanProperty())
                        .defineMethod("set" + capitalize(camelCaseColumnName), void.class, Modifier.PUBLIC)
                        .withParameter(javaType)
                        .intercept(FieldAccessor.ofBeanProperty());

            }

            Class<?> loadedClass;
            try {
                loadedClass = Class.forName(tableName);
            } catch (ClassNotFoundException e) {
                DynamicType.Unloaded<?> unloadedType = builder.make();
                unloadedType.saveIn(new File("./demo-bytecode-generation-technology/byte-buddy/target/classes")); // Save the .class file in target/classes directory
                try {
                    loadedClass = Class.forName(unloadedType.getTypeDescription().getName());
                } catch (ClassNotFoundException ex) {
                    loadedClass = unloadedType.load(getClass().getClassLoader()).getLoaded();
                }
            }

            return loadedClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> sqlTypeToJavaType(String sqlType) {
        switch (sqlType) {
            case "VARCHAR":
            case "CHAR":
            case "LONGVARCHAR":
                return String.class;
            case "NUMERIC":
            case "DECIMAL":
                return java.math.BigDecimal.class;
            case "BIT":
                return Boolean.class;
            case "TINYINT":
                return Byte.class;
            case "SMALLINT":
                return Short.class;
            case "INTEGER":
                return Integer.class;
            case "BIGINT":
                return Long.class;
            case "REAL":
                return Float.class;
            case "FLOAT":
            case "DOUBLE":
                return Double.class;
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
                return byte[].class;
            case "DATE":
                return Date.class;
            case "TIME":
                return Time.class;
            case "TIMESTAMP":
                return Timestamp.class;
            default:
                return Object.class;
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String toCamelCase(String s) {
        String[] parts = s.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(toProperCase(parts[i]));
        }
        return camelCaseString.toString();
    }

    public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}