package dao;

import entity.Duong;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DuongDAO {

    // Kiểm tra sự tồn tại của ID đường
    public boolean isDuongIdExists(int duongId) {
        String query = "SELECT COUNT(*) FROM duong WHERE duong_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, duongId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra sự tồn tại của tên đường
    public boolean isTenDuongExists(String tenDuong) {
        String query = "SELECT COUNT(*) FROM duong WHERE ten_duong = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tenDuong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một đường
    public boolean addDuong(Duong duong) {
        if (isDuongIdExists(duong.getDuongId())) {
            System.out.println("ID " + duong.getDuongId() + " đã tồn tại.");
            return false;
        }

        if (isTenDuongExists(duong.getTenDuong())) {
            System.out.println("Tên đường " + duong.getTenDuong() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO duong (duong_id, ten_duong, quan_id) VALUES (?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, duong.getDuongId());
            preparedStatement.setString(2, duong.getTenDuong());
            preparedStatement.setInt(3, duong.getQuanId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một đường
    public boolean updateDuong(Duong duong) {
        String query = "UPDATE duong SET ten_duong = ?, quan_id = ? WHERE duong_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, duong.getTenDuong());
            preparedStatement.setInt(2, duong.getQuanId());
            preparedStatement.setInt(3, duong.getDuongId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một đường
    public boolean deleteDuong(int duongId) {
        String query = "DELETE FROM duong WHERE duong_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, duongId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một đường theo ID
    public Duong findDuongById(int duongId) {
        String query = "SELECT * FROM duong WHERE duong_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, duongId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Duong duong = new Duong();
                duong.setDuongId(resultSet.getInt("duong_id"));
                duong.setTenDuong(resultSet.getString("ten_duong"));
                duong.setQuanId(resultSet.getInt("quan_id"));
                return duong;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả các đường
    public List<Duong> getAllDuong() {
        List<Duong> duongList = new ArrayList<>();
        String query = "SELECT * FROM duong";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Duong duong = new Duong();
                duong.setDuongId(resultSet.getInt("duong_id"));
                duong.setTenDuong(resultSet.getString("ten_duong"));
                duong.setQuanId(resultSet.getInt("quan_id"));

                duongList.add(duong);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return duongList;
    }

    // Phương thức main để kiểm tra chức năng
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DuongDAO duongDAO = new DuongDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm đường");
            System.out.println("2. Tìm kiếm đường theo ID");
            System.out.println("3. Hiển thị danh sách đường");
            System.out.println("4. Sửa thông tin đường");
            System.out.println("5. Xoá đường");
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
                    System.out.println("== Thêm đường ==");
                    Duong newDuong = enterDuongInfo(scanner, duongDAO);
                    boolean added = duongDAO.addDuong(newDuong);
                    System.out.println(added ? "Thêm đường thành công." : "Thêm đường thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm đường theo ID ==");
                    System.out.print("Nhập ID đường cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    Duong foundDuong = duongDAO.findDuongById(searchId);
                    if (foundDuong != null) {
                        displayDuongInfo(foundDuong);
                    } else {
                        System.out.println("Không tìm thấy đường có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách đường ==");
                    List<Duong> duongList = duongDAO.getAllDuong();
                    displayAllDuong(duongList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin đường ==");
                    System.out.print("Nhập ID đường cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    Duong editDuong = duongDAO.findDuongById(editId);
                    if (editDuong != null) {
                        System.out.println("Nhập thông tin mới cho đường:");
                        Duong updatedDuong = enterDuongInfo(scanner, duongDAO);
                        updatedDuong.setDuongId(editId);
                        boolean updated = duongDAO.updateDuong(updatedDuong);
                        System.out.println(updated ? "Sửa thông tin đường thành công."
                                : "Sửa thông tin đường thất bại.");
                    } else {
                        System.out.println("Không tìm thấy đường có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá đường ==");
                    System.out.print("Nhập ID đường cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = duongDAO.deleteDuong(deleteId);
                    System.out.println(deleteResult ? "Xoá đường thành công." : "Xoá đường thất bại. Bạn hãy xoá hết công trình thuộc đường này trước khi xoá đường !");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin đường từ bàn phím
    private static Duong enterDuongInfo(Scanner scanner, DuongDAO duongDAO) {
        Duong duong = new Duong();

        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            if (duongDAO.isDuongIdExists(id)) {
                System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
            } else {
                duong.setDuongId(id);
                break;
            }
        }

        System.out.print("Tên đường: ");
        String tenDuong = scanner.nextLine();
        while (true) {
            if (duongDAO.isTenDuongExists(tenDuong)) {
                System.out.println("Tên đường " + tenDuong + " đã tồn tại. Vui lòng nhập lại tên đường khác.");
                tenDuong = scanner.nextLine();
            } else {
                duong.setTenDuong(tenDuong);
                break;
            }
        }

        System.out.print("ID Quận: ");
        duong.setQuanId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return duong;
    }

    // Phương thức để hiển thị thông tin đường
    private static void displayDuongInfo(Duong duong) {
        System.out.println("ID: " + duong.getDuongId());
        System.out.println("Tên đường: " + duong.getTenDuong());
        System.out.println("ID Quận: " + duong.getQuanId());
    }

    // Phương thức để hiển thị danh sách tất cả các đường
    private static void displayAllDuong(List<Duong> duongList) {
        for (Duong duong : duongList) {
            displayDuongInfo(duong);
            System.out.println("-------------");
        }
    }
}
