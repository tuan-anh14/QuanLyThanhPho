package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    static final String DB_URL = "jdbc:mysql://localhost:3306/thanh_pho"; // Đặt trực tiếp URL tới cơ sở dữ liệu thanh_pho
    static final String USER = "root";
    static final String PASS = "123456";

    // Phương thức để lấy kết nối đến cơ sở dữ liệu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
