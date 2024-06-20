import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.sql.*;

public class ByteBuddyClassCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public Class<?> createClass(String tableName, String className) throws SQLException, IOException {
        // Connect to the database
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // Get metadata
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);

        // Create a ByteBuddy object
        ByteBuddy byteBuddy = new ByteBuddy();

        // Start building the class
        DynamicType.Builder<?> builder = byteBuddy.subclass(Object.class).name(className);

        // Add fields to the class based on the table columns
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");

            // Convert SQL type to Java type
            Class<?> javaType = sqlTypeToJavaType(columnType);

            // Add the field to the class
            builder = builder.defineField(columnName, javaType, Modifier.PUBLIC)
                    .defineMethod("get" + capitalize(columnName), javaType, Modifier.PUBLIC)
                    .intercept(FieldAccessor.ofBeanProperty())
                    .defineMethod("set" + capitalize(columnName), void.class, Modifier.PUBLIC)
                    .withParameter(javaType)
                    .intercept(FieldAccessor.ofBeanProperty());
        }

        // Close the connection
        connection.close();

        // Generate the class
        DynamicType.Unloaded<?> unloadedType = builder.make();
        unloadedType.saveIn(new File("target/classes")); // Save the .class file in target/classes directory

        return unloadedType.load(getClass().getClassLoader()).getLoaded();
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

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static void main(String[] args) {
        ByteBuddyClassCreator creator = new ByteBuddyClassCreator();
        try {
            Class<?> dynamicClass = creator.createClass("sys_config", "SysConfig");
            System.out.println("Class created successfully: " + dynamicClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}