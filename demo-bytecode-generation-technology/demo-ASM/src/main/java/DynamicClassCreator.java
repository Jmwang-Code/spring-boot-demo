import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class DynamicClassCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public byte[] createClass(String tableName, String className) throws SQLException, ClassNotFoundException {
        // Connect to the database
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // Get metadata
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);

        // Create a ClassWriter object
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);

        // Add fields to the class based on the table columns
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");

            // Convert SQL type to Java type
            String javaType = sqlTypeToJavaType(columnType);

            classWriter.visitField(Opcodes.ACC_PUBLIC, columnName, Type.getDescriptor(Class.forName(javaType)), null, null).visitEnd();
        }

        classWriter.visitEnd();

        // Close the connection
        connection.close();

        return classWriter.toByteArray();
    }

    private String sqlTypeToJavaType(String sqlType) {
        switch (sqlType) {
            case "VARCHAR":
            case "CHAR":
            case "LONGVARCHAR":
                return String.class.getName();
            case "NUMERIC":
            case "DECIMAL":
                return java.math.BigDecimal.class.getName();
            case "BIT":
                return Boolean.class.getName();
            case "TINYINT":
                return Byte.class.getName();
            case "SMALLINT":
                return Short.class.getName();
            case "INTEGER":
                return Integer.class.getName();
            case "BIGINT":
                return Long.class.getName();
            case "REAL":
                return Float.class.getName();
            case "FLOAT":
            case "DOUBLE":
                return Double.class.getName();
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
                return "byte[]";
            case "DATE":
                return java.sql.Date.class.getName();
            case "TIME":
                return java.sql.Time.class.getName();
            case "TIMESTAMP":
                return java.sql.Timestamp.class.getName();
            default:
                return Object.class.getName();
        }
    }

    public static void main(String[] args) {
        DynamicClassCreator creator = new DynamicClassCreator();
        try {
            byte[] classBytes = creator.createClass("sys_config", "SysConfig");

            // 获取当前 Java 文件的目录
            String currentDir = new File(DynamicClassCreator.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getPath();

            // 将字节写入当前目录中的 .class 文件
            try (FileOutputStream fos = new FileOutputStream(currentDir + File.separator + "SysConfig.class")) {
                fos.write(classBytes);
            }

            System.out.println("类已成功创建并写入 " + currentDir + File.separator + "SysConfig.class");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}