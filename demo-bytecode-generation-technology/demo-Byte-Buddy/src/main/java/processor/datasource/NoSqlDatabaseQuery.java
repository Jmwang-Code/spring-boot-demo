package processor.datasource;

import processor.datasource.nosql.query.NoSQLQuery;

import java.util.List;

public interface NoSqlDatabaseQuery {

    // NO query
    List query(NoSQLQuery noSQLQuery);
}
