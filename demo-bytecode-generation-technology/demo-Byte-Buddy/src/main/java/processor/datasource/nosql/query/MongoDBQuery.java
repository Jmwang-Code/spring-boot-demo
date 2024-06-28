package processor.datasource.nosql.query;

import org.bson.conversions.Bson;

public class MongoDBQuery implements NoSQLQuery {
    private String collectionName;
    private Bson filter;
    // ... 其他参数和方法 ...
}