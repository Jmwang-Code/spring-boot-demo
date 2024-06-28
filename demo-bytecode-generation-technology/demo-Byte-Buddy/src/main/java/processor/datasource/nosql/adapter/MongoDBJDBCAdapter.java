package processor.datasource.nosql.adapter;

import processor.datasource.NoSqlAdapter;
import processor.datasource.enums.DatabaseEnum;
import processor.datasource.nosql.query.NoSQLQuery;
import processor.datasource.pojo.ColumnEntity;
import processor.datasource.pojo.DatabaseEntity;
import processor.datasource.pojo.TableEntity;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBJDBCAdapter extends NoSqlAdapter {

    public MongoDBJDBCAdapter(String hostname, Integer port, String databaseName, String username, String password) {
        super(hostname, port, databaseName, username, password);
    }

    @Override
    public String getConnectionString() {
        return "mongodb://" + super.username + ":" + super.password + "@" + super.hostname + ":" + super.port + "/" + super.databaseName;
    }

    @Override
    public DatabaseEnum getDatabaseType() {
        return DatabaseEnum.MONGODB;
    }

    @Override
    public boolean testConnection() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        try {
            // Try to get a database and perform a simple operation
            mongoClient.getDatabase(super.databaseName).getName();
            return true;
        } catch (Exception e) {
            // If an exception is thrown, the connection is not valid
            return false;
        } finally {
            mongoClient.close();
        }
    }

    @Override
    public List<DatabaseEntity> getDatabaseMetadata() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        List<DatabaseEntity> databaseEntities = new ArrayList<>();

        // Get all the databases
        for (String dbName : mongoClient.listDatabaseNames()) {
            DatabaseEntity databaseEntity = new DatabaseEntity();
            databaseEntity.setDatabaseName(dbName);
            databaseEntity.setDatabaseEnum(getDatabaseType());

            MongoDatabase db = mongoClient.getDatabase(dbName);

            // Get all the collections in the database
            List<TableEntity> tableEntities = new ArrayList<>();
            for (String collectionName : db.listCollectionNames()) {
                TableEntity tableEntity = new TableEntity();
                tableEntity.setTableName(collectionName);

                // Get the structure of the documents in the collection
                // Here we just get the first document and get its field names
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

    @Override
    public String getDatabaseVersion() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        String version = mongoClient.getDatabase(super.databaseName).runCommand(new Document("buildInfo", 1)).get("version").toString();
        mongoClient.close();
        return version;
    }

    @Override
    public List<String> getIgnoreDatabaseList() {
        return List.of();
    }

    @Override
    public List query(NoSQLQuery noSQLQuery) {
        return null;
    }
}