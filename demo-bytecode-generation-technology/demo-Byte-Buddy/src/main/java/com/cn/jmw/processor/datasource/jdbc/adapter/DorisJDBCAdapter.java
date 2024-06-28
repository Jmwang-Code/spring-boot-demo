package com.cn.jmw.processor.datasource.jdbc.adapter;

import com.cn.jmw.processor.datasource.JDBCAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.pojo.StreamLoadResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DorisJDBCAdapter extends JDBCAdapter {

    // DORIS HTTP PORT
    private static final int DORIS_HTTP_PORT = 8030;

    public DorisJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "jdbc:mysql://" + super.hostname + ":" + super.port + "/" + super.databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
    }

    /**
     * Query data using batch
     */
    public List<Map<String, Object>> queryBatch(String sql, Object[] params) throws SQLException {
        return super.runner.query(pool.getConnection(hostname+port+databaseName), sql, new MapListHandler(), params);
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return Arrays.asList("information_schema", "__internal_schema");
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.DORIS;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
     * @param json 要加载的 JSON 数据
     * @param tableName 要将数据加载到其中的表的名称
     * @throws IOException 如果发生 IO 错误
     */
    private StreamLoadResult streamLoad(String json, String tableName) throws IOException {
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
            put.setHeader("format", "json");
            put.setHeader("label", label);
            put.setHeader("column_separator", ",");
            // strip_outer_array: 布尔类型，为true表示json数据以数组对象开始且将数组对象中进行展平，默认值是false
            put.setHeader("strip_outer_array", "true");

            // 设置导入文件。
            // 这里也可以使用 StringEntity 来传输任意数据。
            StringEntity entity = new StringEntity(json,StandardCharsets.UTF_8);
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
     * 将一个大列表分成size份进行streamLoad
     */
    public StreamLoadResult[] streamLoadBatch(List<?> list, String tableName, int size) throws IOException {
        int total = list.size();
        if (list==null || total==0){
            return new StreamLoadResult[0];
        }
        int batch = total / size;
        StreamLoadResult[] results = new StreamLoadResult[batch + 1];
        int remainder = total % size;
        int start = 0;
        for (int i = 0; i < batch; i++) {
            List<?> subList = list.subList(start, start + size);
            StreamLoadResult streamLoadResult = streamLoad(listToJson(subList), tableName);
            results[i] = streamLoadResult;
            start += size;
        }
        if (remainder > 0) {
            List<?> subList = list.subList(start, start + remainder);
            StreamLoadResult streamLoadResult = streamLoad(listToJson(subList), tableName);
            results[batch] = streamLoadResult;
        }

        return results;
    }

}