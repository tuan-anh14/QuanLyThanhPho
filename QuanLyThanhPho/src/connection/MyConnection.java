package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Chọn cơ sở dữ liệu thanh_pho
            String useDatabaseQuery = "USE thanh_pho";
            stmt.executeUpdate(useDatabaseQuery);

            // Thực thi câu lệnh SELECT để lấy dữ liệu từ bảng Quan
            ResultSet rs = stmt.executeQuery("SELECT * FROM Quan");

            // Duyệt qua các bản ghi trả về từ câu lệnh SELECT
            while (rs.next()) {
                int quanId = rs.getInt("quanId");
                String tenQuan = rs.getString("tenQuan");
                int soLuongNguoi = rs.getInt("soLuongNguoi");
                int soLuongDuong = rs.getInt("soLuongDuong");
                int soLuongCongTrinh = rs.getInt("soLuongCongTrinh");

                // Hiển thị thông tin của mỗi bản ghi
                System.out.println("Quan ID: " + quanId);
                System.out.println("Ten Quan: " + tenQuan);
                System.out.println("So Luong Nguoi: " + soLuongNguoi);
                System.out.println("So Luong Duong: " + soLuongDuong);
                System.out.println("So Luong Cong Trinh: " + soLuongCongTrinh);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
