import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test"; // 数据库 URL
        String user = "root"; // 数据库用户名
        String password = "123456"; // 数据库密码

        try {
            // 加载 MySQL 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 建立连接
            Connection conn = DriverManager.getConnection(url, user, password);

            // 操作数据库...

            // 关闭连接
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }
}