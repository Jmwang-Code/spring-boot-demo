import net.sf.cglib.beans.BeanGenerator;
import java.sql.*;

public class CGLIBClassCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    //JDK9开始 会禁用CGlib底层API需要加,--add-opens java.base/java.lang=ALL-UNNAMED
    public Object createClass(String tableName, String className) throws SQLException {
        // Connect to the database
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // Get metadata
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);

        // Create a BeanGenerator object
        BeanGenerator generator = new BeanGenerator();

        // Set the superclass of the generated class
        generator.setSuperclass(Object.class);

        // Add properties to the bean based on the table columns
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");

            // Convert SQL type to Java type
            Class<?> javaType = sqlTypeToJavaType(columnType);

            // Add the property to the bean
            generator.addProperty(columnName, javaType);
        }

        // Close the connection
        connection.close();

        // Generate the bean
        return generator.create();
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
                return java.sql.Date.class;
            case "TIME":
                return java.sql.Time.class;
            case "TIMESTAMP":
                return java.sql.Timestamp.class;
            default:
                return Object.class;
        }
    }

    public static void main(String[] args) {
        CGLIBClassCreator creator = new CGLIBClassCreator();
        try {
            Object bean = creator.createClass("sys_config", "SysConfig");
            System.out.println("Class created successfully: " + bean.getClass());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}