import javassist.*;

import java.io.File;
import java.sql.*;

public class JavassistClassCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public void createClass(String tableName, String className) throws Exception {
        // Connect to the database
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // Get metadata
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);

        // Create a new class
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        // Add fields to the class based on the table columns
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");

            // Convert SQL type to Java type
            String javaType = sqlTypeToJavaType(columnType);

            // Add the field to the class
            CtField field = new CtField(pool.get(javaType), columnName, cc);
            field.setModifiers(Modifier.PUBLIC);
            cc.addField(field);
        }
        String currentDir = new File(JavassistClassCreator.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getPath();

        // Write the class
        cc.writeFile(currentDir);

        // Close the connection
        connection.close();
    }

    private String sqlTypeToJavaType(String sqlType) {
        switch (sqlType) {
            case "VARCHAR":
            case "CHAR":
            case "LONGVARCHAR":
                return "java.lang.String";
            case "NUMERIC":
            case "DECIMAL":
                return "java.math.BigDecimal";
            case "BIT":
                return "java.lang.Boolean";
            case "TINYINT":
                return "java.lang.Byte";
            case "SMALLINT":
                return "java.lang.Short";
            case "INTEGER":
                return "java.lang.Integer";
            case "BIGINT":
                return "java.lang.Long";
            case "REAL":
                return "java.lang.Float";
            case "FLOAT":
            case "DOUBLE":
                return "java.lang.Double";
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
                return "[B";
            case "DATE":
                return "java.sql.Date";
            case "TIME":
                return "java.sql.Time";
            case "TIMESTAMP":
                return "java.sql.Timestamp";
            default:
                return "java.lang.Object";
        }
    }

    public static void main(String[] args) {
        JavassistClassCreator creator = new JavassistClassCreator();
        try {
            creator.createClass("sys_config", "SysConfig");
            System.out.println("Class created successfully and written to SysConfig.class");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}