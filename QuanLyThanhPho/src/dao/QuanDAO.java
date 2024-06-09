package dao;

import entity.Quan;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanDAO {

    // Kiểm tra sự tồn tại của ID quận
    public boolean isQuanIdExists(int quanId) {
        String query = "SELECT COUNT(*) FROM quan WHERE Quan_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một quận
    public boolean addQuan(Quan quan) {
        if (isQuanIdExists(quan.getQuanId())) {
            System.out.println("ID " + quan.getQuanId() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO quan (Quan_id, Ten_quan, So_luong_nguoi, So_luong_duong, So_luong_Cong_trinh) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, quan.getQuanId());
            preparedStatement.setString(2, quan.getTenQuan());
            preparedStatement.setInt(3, quan.getSoLuongNguoi());
            preparedStatement.setInt(4, quan.getSoLuongDuong());
            preparedStatement.setInt(5, quan.getSoLuongCongTrinh());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một quận
    public boolean updateQuan(Quan quan) {
        String query = "UPDATE quan SET Ten_quan = ?, So_luong_nguoi = ?, So_luong_duong = ?, So_luong_Cong_trinh = ? WHERE Quan_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, quan.getTenQuan());
            preparedStatement.setInt(2, quan.getSoLuongNguoi());
            preparedStatement.setInt(3, quan.getSoLuongDuong());
            preparedStatement.setInt(4, quan.getSoLuongCongTrinh());
            preparedStatement.setInt(5, quan.getQuanId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một quận
    public boolean deleteQuan(int quanId) {
        String query = "DELETE FROM quan WHERE Quan_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, quanId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một quận theo ID
    public Quan findQuanById(int quanId) {
        String query = "SELECT * FROM quan WHERE Quan_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, quanId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Quan quan = new Quan();
                quan.setQuanId(resultSet.getInt("Quan_id"));
                quan.setTenQuan(resultSet.getString("Ten_quan"));
                quan.setSoLuongNguoi(resultSet.getInt("So_luong_nguoi"));
                quan.setSoLuongDuong(resultSet.getInt("So_luong_duong"));
                quan.setSoLuongCongTrinh(resultSet.getInt("So_luong_Cong_trinh"));
                return quan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hiển thị tất cả các quận
    public List<Quan> getAllQuan() {
        List<Quan> quanList = new ArrayList<>();
        String query = "SELECT * FROM quan";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Quan quan = new Quan();
                quan.setQuanId(resultSet.getInt("Quan_id"));
                quan.setTenQuan(resultSet.getString("Ten_quan"));
                quan.setSoLuongNguoi(resultSet.getInt("So_luong_nguoi"));
                quan.setSoLuongDuong(resultSet.getInt("So_luong_duong"));
                quan.setSoLuongCongTrinh(resultSet.getInt("So_luong_Cong_trinh"));

                quanList.add(quan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quanList;
    }

    // Phương thức main để kiểm tra chức năng
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuanDAO quanDAO = new QuanDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm quận");
            System.out.println("2. Tìm kiếm quận theo ID");
            System.out.println("3. Hiển thị danh sách quận");
            System.out.println("4. Sửa thông tin quận");
            System.out.println("5. Xoá quận");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng (0-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            switch (choice) {
                case 0:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                case 1:
                    System.out.println("== Thêm quận ==");
                    Quan newQuan = enterQuanInfo(scanner, quanDAO);
                    boolean added = quanDAO.addQuan(newQuan);
                    System.out.println(added ? "Thêm quận thành công." : "Thêm quận thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm quận theo ID ==");
                    System.out.print("Nhập ID quận cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    Quan foundQuan = quanDAO.findQuanById(searchId);
                    if (foundQuan != null) {
                        displayQuanInfo(foundQuan);
                    } else {
                        System.out.println("Không tìm thấy quận có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách quận ==");
                    List<Quan> quanList = quanDAO.getAllQuan();
                    displayAllQuan(quanList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin quận ==");
                    System.out.print("Nhập ID quận cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    Quan editQuan = quanDAO.findQuanById(editId);
                    if (editQuan != null) {
                        System.out.println("Nhập thông tin mới cho quận:");
                        Quan updatedQuan = enterQuanInfo(scanner, quanDAO);
                        updatedQuan.setQuanId(editId);
                        boolean updated = quanDAO.updateQuan(updatedQuan);
                        System.out.println(updated ? "Sửa thông tin quận thành công."
                                : "Sửa thông tin quận thất bại.");
                    } else {
                        System.out.println("Không tìm thấy quận có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá quận ==");
                    System.out.print("Nhập ID quận cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = quanDAO.deleteQuan(deleteId);
                    System.out.println(deleteResult ? "Xoá quận thành công." : "Xoá quận thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin quận từ bàn phím
    private static Quan enterQuanInfo(Scanner scanner, QuanDAO quanDAO) {
        Quan quan = new Quan();

        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            if (quanDAO.isQuanIdExists(id)) {
                System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
            } else {
                quan.setQuanId(id);
                break;
            }
        }

        System.out.print("Tên quận: ");
        quan.setTenQuan(scanner.nextLine());

        System.out.print("Số lượng người: ");
        quan.setSoLuongNguoi(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số lượng đường: ");
        quan.setSoLuongDuong(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số lượng công trình: ");
        quan.setSoLuongCongTrinh(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return quan;
    }

    // Phương thức để hiển thị thông tin quận
    private static void displayQuanInfo(Quan quan) {
        System.out.println("ID: " + quan.getQuanId());
        System.out.println("Tên quận: " + quan.getTenQuan());
        System.out.println("Số lượng người: " + quan.getSoLuongNguoi());
        System.out.println("Số lượng đường: " + quan.getSoLuongDuong());
        System.out.println("Số lượng công trình: " + quan.getSoLuongCongTrinh());
    }

    // Phương thức để hiển thị danh sách tất cả quận
    private static void displayAllQuan(List<Quan> quanList) {
        for (Quan quan : quanList) {
            displayQuanInfo(quan);
            System.out.println("-------------");
        }
    }
}
