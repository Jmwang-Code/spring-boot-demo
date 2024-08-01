package com.cn.jmw.processor.datasource.nosql.adapter;

import com.cn.jmw.processor.datasource.NoSqlAdapter;
import com.cn.jmw.processor.datasource.enums.DatabaseEnum;
import com.cn.jmw.processor.datasource.pojo.ColumnEntity;
import com.cn.jmw.processor.datasource.pojo.DatabaseEntity;
import com.cn.jmw.processor.datasource.pojo.TableEntity;
import com.cn.jmw.processor.datasource.nosql.query.NoSQLQuery;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * MongoDBJDBCAdapter类用于适配MongoDB数据库连接。
 * <p>
 * 该类扩展了NoSqlAdapter，提供了与MongoDB相关的数据库操作。
 * </p>
 */
public class MongoDBJDBCAdapter extends NoSqlAdapter {

    /**
     * 构造函数用于创建MongoDBJDBCAdapter实例。
     *
     * @param hostname     MongoDB服务器的主机名
     * @param port         MongoDB服务器的端口号
     * @param databaseName 数据库名称
     * @param username     用户名
     * @param password     密码
     */
    public MongoDBJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    /**
     * 获取MongoDB的连接字符串。
     *
     * @return 返回连接字符串
     */
    @Override
    public String getConnectionString() {
        return "mongodb://" + super.username + ":" + super.password + "@" + super.hostname + ":" + super.port + "/" + super.databaseName;
    }

    /**
     * 获取当前数据库的类型。
     *
     * @return 返回数据库枚举类型
     */
    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.MONGODB;
    }

    /**
     * 测试与MongoDB的连接是否有效。
     *
     * @return 如果连接有效返回true，否则返回false
     */
    @Override
    public boolean testConnection() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        try {
            // 尝试获取一个数据库并执行简单操作
            mongoClient.getDatabase(super.databaseName).getName();
            return true;
        } catch (Exception e) {
            // 如果抛出异常，则连接无效
            return false;
        } finally {
            mongoClient.close();
        }
    }

    /**
     * 获取MongoDB的数据库元数据。
     *
     * @return 返回包含数据库实体的列表
     */
    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        List<DatabaseEntity> databaseEntities = new ArrayList<>();
        // 获取所有数据库
        for (String dbName : mongoClient.listDatabaseNames()) {
            DatabaseEntity databaseEntity = new DatabaseEntity();
            databaseEntity.setDatabaseName(dbName);
            databaseEntity.setDatabaseEnum(getDatabaseType());
            MongoDatabase db = mongoClient.getDatabase(dbName);
            // 获取数据库中的所有集合
            List<TableEntity> tableEntities = new ArrayList<>();
            for (String collectionName : db.listCollectionNames()) {
                TableEntity tableEntity = new TableEntity();
                tableEntity.setTableName(collectionName);
                // 获取集合中文档的结构
                // 此处只获取第一个文档及其字段名
                MongoCollection<Document> collection = db.getCollection(collectionName);
                Document firstDoc = collection.find().first();
                if (firstDoc != null) {
                    List<ColumnEntity> columnEntities = new ArrayList<>();
                    for (String fieldName : firstDoc.keySet()) {
                        ColumnEntity columnEntity = new ColumnEntity();
                        columnEntity.setColumnName(fieldName);
                        columnEntity.setColumnType(firstDoc.get(fieldName).getClass().getSimpleName());
                        columnEntities.add(columnEntity);
                    }
                    tableEntity.setColumns(columnEntities);
                }
                tableEntities.add(tableEntity);
            }
            databaseEntity.setTables(tableEntities);
            databaseEntities.add(databaseEntity);
        }
        mongoClient.close();
        return databaseEntities;
    }

    /**
     * 获取MongoDB的版本信息。
     *
     * @return 返回数据库版本
     */
    @Override
    public String getDatabaseVersion() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        String version = mongoClient.getDatabase(super.databaseName).runCommand(new Document("buildInfo", 1)).get("version").toString();
        mongoClient.close();
        return version;
    }

    /**
     * 获取需要忽略的数据库列表。
     *
     * @return 返回忽略数据库的列表
     */
    @Override
    public List<String> getIgnoreDatabaseList() {
        return List.of();
    }

    /**
     * 执行NoSQL查询。
     *
     * @param noSQLQuery 要执行的NoSQL查询对象
     * @return 返回查询结果
     */
    @Override
    public List query(NoSQLQuery noSQLQuery) {
        return null;
    }
}