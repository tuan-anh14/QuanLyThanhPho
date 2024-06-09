package dao;

import entity.KhachSan;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KhachSanDAO {

    // Kiểm tra sự tồn tại của ID khách sạn
    public boolean isKhachSanIdExists(int khachSanId) {
        String query = "SELECT COUNT(*) FROM khach_san WHERE khach_san_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, khachSanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một khách sạn
    public boolean addKhachSan(KhachSan khachSan) {
        if (isKhachSanIdExists(khachSan.getKhachSanId())) {
            System.out.println("ID " + khachSan.getKhachSanId() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO khach_san (khach_san_id, Ten, So_dia_chi, Duong_id, So_phong, So_phong_con_trong, CheckIn, CheckOut, Quan_ly, Cong_trinh_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, khachSan.getKhachSanId());
            preparedStatement.setString(2, khachSan.getTen());
            preparedStatement.setString(3, khachSan.getSoDiaChi());
            preparedStatement.setInt(4, khachSan.getDuongId());
            preparedStatement.setInt(5, khachSan.getSoPhong());
            preparedStatement.setInt(6, khachSan.getSoPhongConTrong());
            preparedStatement.setInt(7, khachSan.getCheckIn());
            preparedStatement.setInt(8, khachSan.getCheckOut());
            preparedStatement.setString(9, khachSan.getQuanLy());
            preparedStatement.setInt(10, khachSan.getCongTrinhId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một khách sạn
    public boolean updateKhachSan(KhachSan khachSan) {
        String query = "UPDATE khach_san SET Ten = ?, So_dia_chi = ?, Duong_id = ?, So_phong = ?, So_phong_con_trong = ?, CheckIn = ?, CheckOut = ?, Quan_ly = ?, Cong_trinh_id = ? WHERE khach_san_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, khachSan.getTen());
            preparedStatement.setString(2, khachSan.getSoDiaChi());
            preparedStatement.setInt(3, khachSan.getDuongId());
            preparedStatement.setInt(4, khachSan.getSoPhong());
            preparedStatement.setInt(5, khachSan.getSoPhongConTrong());
            preparedStatement.setInt(6, khachSan.getCheckIn());
            preparedStatement.setInt(7, khachSan.getCheckOut());
            preparedStatement.setString(8, khachSan.getQuanLy());
            preparedStatement.setInt(9, khachSan.getCongTrinhId());
            preparedStatement.setInt(10, khachSan.getKhachSanId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một khách sạn
    public boolean deleteKhachSan(int khachSanId) {
        String query = "DELETE FROM khach_san WHERE khach_san_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, khachSanId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một khách sạn theo ID
    public KhachSan findKhachSanById(int khachSanId) {
        String query = "SELECT * FROM khach_san WHERE khach_san_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, khachSanId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                KhachSan khachSan = new KhachSan();
                khachSan.setKhachSanId(resultSet.getInt("khach_san_id"));
                khachSan.setTen(resultSet.getString("Ten"));
                khachSan.setSoDiaChi(resultSet.getString("So_dia_chi"));
                khachSan.setDuongId(resultSet.getInt("Duong_id"));
                khachSan.setSoPhong(resultSet.getInt("So_phong"));
                khachSan.setSoPhongConTrong(resultSet.getInt("So_phong_con_trong"));
                khachSan.setCheckIn(resultSet.getInt("CheckIn"));
                khachSan.setCheckOut(resultSet.getInt("CheckOut"));
                khachSan.setQuanLy(resultSet.getString("Quan_ly"));
                khachSan.setCongTrinhId(resultSet.getInt("Cong_trinh_id"));
                return khachSan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả khách sạn
    public List<KhachSan> findAllKhachSan() {
        List<KhachSan> khachSanList = new ArrayList<>();
        String query = "SELECT * FROM khach_san";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                KhachSan khachSan = new KhachSan();
                khachSan.setKhachSanId(resultSet.getInt("khach_san_id"));
                khachSan.setTen(resultSet.getString("Ten"));
                khachSan.setSoDiaChi(resultSet.getString("So_dia_chi"));
                khachSan.setDuongId(resultSet.getInt("Duong_id"));
                khachSan.setSoPhong(resultSet.getInt("So_phong"));
                khachSan.setSoPhongConTrong(resultSet.getInt("So_phong_con_trong"));
                khachSan.setCheckIn(resultSet.getInt("CheckIn"));
                khachSan.setCheckOut(resultSet.getInt("CheckOut"));
                khachSan.setQuanLy(resultSet.getString("Quan_ly"));
                khachSan.setCongTrinhId(resultSet.getInt("Cong_trinh_id"));
                khachSanList.add(khachSan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachSanList;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KhachSanDAO khachSanDAO = new KhachSanDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm khách sạn");
            System.out.println("2. Tìm kiếm khách sạn theo ID");
            System.out.println("3. Hiển thị danh sách khách sạn");
            System.out.println("4. Sửa thông tin khách sạn");
            System.out.println("5. Xoá khách sạn");
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
                    System.out.println("== Thêm khách sạn ==");
                    KhachSan newKhachSan = enterKhachSanInfo(scanner);
                    boolean added = khachSanDAO.addKhachSan(newKhachSan);
                    System.out.println(added ? "Thêm khách sạn thành công." : "Thêm khách sạn thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm khách sạn theo ID ==");
                    System.out.print("Nhập ID khách sạn cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    KhachSan foundKhachSan = khachSanDAO.findKhachSanById(searchId);
                    if (foundKhachSan != null) {
                        displayKhachSanInfo(foundKhachSan);
                    } else {
                        System.out.println("Không tìm thấy khách sạn có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách khách sạn ==");
                    List<KhachSan> khachSanList = khachSanDAO.findAllKhachSan();
                    displayAllKhachSan(khachSanList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin khách sạn ==");
                    System.out.print("Nhập ID khách sạn cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    KhachSan editKhachSan = khachSanDAO.findKhachSanById(editId);
                    if (editKhachSan != null) {
                        System.out.println("Nhập thông tin mới cho khách sạn:");
                        KhachSan updatedKhachSan = enterKhachSanInfo(scanner);
                        updatedKhachSan.setKhachSanId(editId);
                        boolean updated = khachSanDAO.updateKhachSan(updatedKhachSan);
                        System.out.println(updated ? "Sửa thông tin khách sạn thành công."
                                : "Sửa thông tin khách sạn thất bại.");
                    } else {
                        System.out.println("Không tìm thấy khách sạn có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá khách sạn ==");
                    System.out.print("Nhập ID khách sạn cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = khachSanDAO.deleteKhachSan(deleteId);
                    System.out.println(deleteResult ? "Xoá khách sạn thành công." : "Xoá khách sạn thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin khách sạn từ bàn phím
    private static KhachSan enterKhachSanInfo(Scanner scanner) {
        KhachSan khachSan = new KhachSan();

        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Đọc kí tự newline
        khachSan.setKhachSanId(id);

        System.out.print("Tên: ");
        khachSan.setTen(scanner.nextLine());

        System.out.print("Số địa chỉ: ");
        khachSan.setSoDiaChi(scanner.nextLine());

        System.out.print("ID Đường: ");
        khachSan.setDuongId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số phòng: ");
        khachSan.setSoPhong(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số phòng còn trống: ");
        khachSan.setSoPhongConTrong(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("CheckIn: ");
        khachSan.setCheckIn(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("CheckOut: ");
        khachSan.setCheckOut(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Quản lý: ");
        khachSan.setQuanLy(scanner.nextLine());

        System.out.print("ID Công trình: ");
        khachSan.setCongTrinhId(scanner.nextInt());
        scanner.nextLine(); //
        return khachSan;
    }

    // Phương thức để hiển thị thông tin khách sạn
    private static void displayKhachSanInfo(KhachSan khachSan) {
        System.out.println("ID: " + khachSan.getKhachSanId());
        System.out.println("Tên: " + khachSan.getTen());
        System.out.println("Số địa chỉ: " + khachSan.getSoDiaChi());
        System.out.println("ID Đường: " + khachSan.getDuongId());
        System.out.println("Số phòng: " + khachSan.getSoPhong());
        System.out.println("Số phòng còn trống: " + khachSan.getSoPhongConTrong());
        System.out.println("CheckIn: " + khachSan.getCheckIn());
        System.out.println("CheckOut: " + khachSan.getCheckOut());
        System.out.println("Quản lý: " + khachSan.getQuanLy());
        System.out.println("ID Công trình: " + khachSan.getCongTrinhId());
    }

    // Phương thức để hiển thị danh sách tất cả khách sạn
    private static void displayAllKhachSan(List<KhachSan> khachSanList) {
        for (KhachSan khachSan : khachSanList) {
            displayKhachSanInfo(khachSan);
            System.out.println("-------------");
        }
    }
}
