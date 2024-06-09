package dao;

import entity.TTTM;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TTTMDAO {

    // Kiểm tra sự tồn tại của ID TTTM
    public boolean isTTTMIdExists(int tttmId) {
        String query = "SELECT COUNT(*) FROM tttm WHERE TTTM_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tttmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một TTTM
    public boolean addTTTM(TTTM tttm) {
        if (isTTTMIdExists(tttm.getTTTMId())) {
            System.out.println("ID " + tttm.getTTTMId() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO tttm (TTTM_id, Ten, So_dia_chi, Duong_id, So_luong_nhan_vien, So_luong_khach_mot_ngay, Quan_ly, Cong_trinh_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, tttm.getTTTMId());
            preparedStatement.setString(2, tttm.getTen());
            preparedStatement.setString(3, tttm.getSoDiaChi());
            preparedStatement.setInt(4, tttm.getDuongId());
            preparedStatement.setInt(5, tttm.getSoLuongNhanVien());
            preparedStatement.setInt(6, tttm.getSoLuongKhachMotNgay());
            preparedStatement.setString(7, tttm.getQuanLy());
            preparedStatement.setInt(8, tttm.getCongTrinhId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một TTTM
    public boolean updateTTTM(TTTM tttm) {
        String query = "UPDATE tttm SET Ten = ?, So_dia_chi = ?, Duong_id = ?, So_luong_nhan_vien = ?, So_luong_khach_mot_ngay = ?, Quan_ly = ?, Cong_trinh_id = ? WHERE TTTM_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tttm.getTen());
            preparedStatement.setString(2, tttm.getSoDiaChi());
            preparedStatement.setInt(3, tttm.getDuongId());
            preparedStatement.setInt(4, tttm.getSoLuongNhanVien());
            preparedStatement.setInt(5, tttm.getSoLuongKhachMotNgay());
            preparedStatement.setString(6, tttm.getQuanLy());
            preparedStatement.setInt(7, tttm.getCongTrinhId());
            preparedStatement.setInt(8, tttm.getTTTMId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một TTTM
    public boolean deleteTTTM(int tttmId) {
        String query = "DELETE FROM tttm WHERE TTTM_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, tttmId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một TTTM theo ID
    public TTTM findTTTMById(int tttmId) {
        String query = "SELECT * FROM tttm WHERE TTTM_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, tttmId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TTTM tttm = new TTTM();
                tttm.setTTTMId(resultSet.getInt("TTTM_id"));
                tttm.setTen(resultSet.getString("Ten"));
                tttm.setSoDiaChi(resultSet.getString("So_dia_chi"));
                tttm.setDuongId(resultSet.getInt("Duong_id"));
                tttm.setSoLuongNhanVien(resultSet.getInt("So_luong_nhan_vien"));
                tttm.setSoLuongKhachMotNgay(resultSet.getInt("So_luong_khach_mot_ngay"));
                tttm.setQuanLy(resultSet.getString("Quan_ly"));
                tttm.setCongTrinhId(resultSet.getInt("Cong_trinh_id"));
                return tttm;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả TTTM
    public List<TTTM> findAllTTTM() {
        List<TTTM> tttmList = new ArrayList<>();
        String query = "SELECT * FROM tttm";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TTTM tttm = new TTTM();
                tttm.setTTTMId(resultSet.getInt("TTTM_id"));
                tttm.setTen(resultSet.getString("Ten"));
                tttm.setSoDiaChi(resultSet.getString("So_dia_chi"));
                tttm.setDuongId(resultSet.getInt("Duong_id"));
                tttm.setSoLuongNhanVien(resultSet.getInt("So_luong_nhan_vien"));
                tttm.setSoLuongKhachMotNgay(resultSet.getInt("So_luong_khach_mot_ngay"));
                tttm.setQuanLy(resultSet.getString("Quan_ly"));
                tttm.setCongTrinhId(resultSet.getInt("Cong_trinh_id"));
                tttmList.add(tttm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tttmList;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TTTMDAO tttmDAO = new TTTMDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm TTTM");
            System.out.println("2. Tìm kiếm TTTM theo ID");
            System.out.println("3. Hiển thị danh sách TTTM");
            System.out.println("4. Sửa thông tin TTTM");
            System.out.println("5. Xoá TTTM");
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
                    System.out.println("== Thêm TTTM ==");
                    TTTM newTTTM = enterTTTMInfo(scanner);
                    boolean added = tttmDAO.addTTTM(newTTTM);
                    System.out.println(added ? "Thêm TTTM thành công." : "Thêm TTTM thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm TTTM theo ID ==");
                    System.out.print("Nhập ID TTTM cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    TTTM foundTTTM = tttmDAO.findTTTMById(searchId);
                    if (foundTTTM != null) {
                        displayTTTMInfo(foundTTTM);
                    } else {
                        System.out.println("Không tìm thấy TTTM có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách TTTM ==");
                    List<TTTM> tttmList = tttmDAO.findAllTTTM();
                    displayAllTTTM(tttmList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin TTTM ==");
                    System.out.print("Nhập ID TTTM cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    TTTM editTTTM = tttmDAO.findTTTMById(editId);
                    if (editTTTM != null) {
                        System.out.println("Nhập thông tin mới cho TTTM:");
                        TTTM updatedTTTM = enterTTTMInfo(scanner);
                        updatedTTTM.setTTTMId(editId);
                        boolean updated = tttmDAO.updateTTTM(updatedTTTM);
                        System.out.println(updated ? "Sửa thông tin TTTM thành công."
                                : "Sửa thông tin TTTM thất bại.");
                    } else {
                        System.out.println("Không tìm thấy TTTM có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá TTTM ==");
                    System.out.print("Nhập ID TTTM cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = tttmDAO.deleteTTTM(deleteId);
                    System.out.println(deleteResult ? "Xoá TTTM thành công." : "Xoá TTTM thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin TTTM từ bàn phím
    private static TTTM enterTTTMInfo(Scanner scanner) {
        TTTM tttm = new TTTM();

        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Đọc kí tự newline
        tttm.setTTTMId(id);

        System.out.print("Tên: ");
        tttm.setTen(scanner.nextLine());

        System.out.print("Số địa chỉ: ");
        tttm.setSoDiaChi(scanner.nextLine());

        System.out.print("ID Đường: ");
        tttm.setDuongId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số lượng nhân viên: ");
        tttm.setSoLuongNhanVien(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số lượng khách một ngày: ");
        tttm.setSoLuongKhachMotNgay(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Quản lý: ");
        tttm.setQuanLy(scanner.nextLine());

        System.out.print("ID Công trình: ");
        tttm.setCongTrinhId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return tttm;
    }

    // Phương thức để hiển thị thông tin TTTM
    private static void displayTTTMInfo(TTTM tttm) {
        System.out.println("ID: " + tttm.getTTTMId());
        System.out.println("Tên: " + tttm.getTen());
        System.out.println("Số địa chỉ: " + tttm.getSoDiaChi());
        System.out.println("ID Đường: " + tttm.getDuongId());
        System.out.println("Số lượng nhân viên: " + tttm.getSoLuongNhanVien());
        System.out.println("Số lượng khách một ngày: " + tttm.getSoLuongKhachMotNgay());
        System.out.println("Quản lý: " + tttm.getQuanLy());
        System.out.println("ID Công trình: " + tttm.getCongTrinhId());
    }

    // Phương thức để hiển thị danh sách tất cả TTTM
    private static void displayAllTTTM(List<TTTM> tttmList) {
        for (TTTM tttm : tttmList) {
            displayTTTMInfo(tttm);
            System.out.println("-------------");
        }
    }
}
