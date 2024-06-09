package dao;

import entity.TruongHoc;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TruongHocDAO {

    // Kiểm tra sự tồn tại của ID trường học
    public boolean isTruongHocIdExists(int truongHocId) {
        String query = "SELECT COUNT(*) FROM truong_hoc WHERE truong_hoc_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, truongHocId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một trường học
    public boolean addTruongHoc(TruongHoc truongHoc) {
        if (isTruongHocIdExists(truongHoc.getTruongHocId())) {
            System.out.println("ID " + truongHoc.getTruongHocId() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO truong_hoc (truong_hoc_id, ten, so_dia_chi, duong_id, cap_bac, so_hoc_sinh, so_giang_vien, hieu_truong, cong_trinh_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, truongHoc.getTruongHocId());
            preparedStatement.setString(2, truongHoc.getTen());
            preparedStatement.setString(3, truongHoc.getSoDiaChi());
            preparedStatement.setInt(4, truongHoc.getDuongId());
            preparedStatement.setString(5, truongHoc.getCapBac());
            preparedStatement.setInt(6, truongHoc.getSoHocSinh());
            preparedStatement.setInt(7, truongHoc.getSoGiangVien());
            preparedStatement.setString(8, truongHoc.getHieuTruong());
            preparedStatement.setInt(9, truongHoc.getCongTrinhId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một trường học
    public boolean updateTruongHoc(TruongHoc truongHoc) {
        String query = "UPDATE truong_hoc SET ten = ?, so_dia_chi = ?, duong_id = ?, cap_bac = ?, so_hoc_sinh = ?, so_giang_vien = ?, hieu_truong = ?, cong_trinh_id = ? WHERE truong_hoc_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, truongHoc.getTen());
            preparedStatement.setString(2, truongHoc.getSoDiaChi());
            preparedStatement.setInt(3, truongHoc.getDuongId());
            preparedStatement.setString(4, truongHoc.getCapBac());
            preparedStatement.setInt(5, truongHoc.getSoHocSinh());
            preparedStatement.setInt(6, truongHoc.getSoGiangVien());
            preparedStatement.setString(7, truongHoc.getHieuTruong());
            preparedStatement.setInt(8, truongHoc.getCongTrinhId());
            preparedStatement.setInt(9, truongHoc.getTruongHocId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một trường học
    public boolean deleteTruongHoc(int truongHocId) {
        String query = "DELETE FROM truong_hoc WHERE truong_hoc_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, truongHocId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một trường học theo ID
    public TruongHoc findTruongHocById(int truongHocId) {
        String query = "SELECT * FROM truong_hoc WHERE truong_hoc_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, truongHocId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TruongHoc truongHoc = new TruongHoc();
                truongHoc.setTruongHocId(resultSet.getInt("truong_hoc_id"));
                truongHoc.setTen(resultSet.getString("ten"));
                truongHoc.setSoDiaChi(resultSet.getString("so_dia_chi"));
                truongHoc.setDuongId(resultSet.getInt("duong_id"));
                truongHoc.setCapBac(resultSet.getString("cap_bac"));
                truongHoc.setSoHocSinh(resultSet.getInt("so_hoc_sinh"));
                truongHoc.setSoGiangVien(resultSet.getInt("so_giang_vien"));
                truongHoc.setHieuTruong(resultSet.getString("hieu_truong"));
                truongHoc.setCongTrinhId(resultSet.getInt("cong_trinh_id"));
                return truongHoc;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả trường học
    public List<TruongHoc> findAllTruongHoc() {
        List<TruongHoc> truongHocList = new ArrayList<>();
        String query = "SELECT * FROM truong_hoc";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TruongHoc truongHoc = new TruongHoc();
                truongHoc.setTruongHocId(resultSet.getInt("truong_hoc_id"));
                truongHoc.setTen(resultSet.getString("ten"));
                truongHoc.setSoDiaChi(resultSet.getString("so_dia_chi"));
                truongHoc.setDuongId(resultSet.getInt("duong_id"));
                truongHoc.setCapBac(resultSet.getString("cap_bac"));
                truongHoc.setSoHocSinh(resultSet.getInt("so_hoc_sinh"));
                truongHoc.setSoGiangVien(resultSet.getInt("so_giang_vien"));
                truongHoc.setHieuTruong(resultSet.getString("hieu_truong"));
                truongHoc.setCongTrinhId(resultSet.getInt("cong_trinh_id"));
                truongHocList.add(truongHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return truongHocList;
    }

    // Phương thức main để kiểm tra chức năng
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TruongHocDAO truongHocDAO = new TruongHocDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm trường học");
            System.out.println("2. Tìm kiếm trường học theo ID");
            System.out.println("3. Hiển thị danh sách trường học");
            System.out.println("4. Sửa thông tin trường học");
            System.out.println("5. Xoá trường học");
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
                    System.out.println("== Thêm trường học ==");
                    TruongHoc newTruongHoc = enterTruongHocInfo(scanner, truongHocDAO);
                    boolean added = truongHocDAO.addTruongHoc(newTruongHoc);
                    System.out.println(added ? "Thêm trường học thành công." : "Thêm trường học thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm trường học theo ID ==");
                    System.out.print("Nhập ID trường học cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    TruongHoc foundTruongHoc = truongHocDAO.findTruongHocById(searchId);
                    if (foundTruongHoc != null) {
                        displayTruongHocInfo(foundTruongHoc);
                    } else {
                        System.out.println("Không tìm thấy trường học có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách trường học ==");
                    List<TruongHoc> truongHocList = truongHocDAO.findAllTruongHoc();
                    displayAllTruongHoc(truongHocList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin trường học ==");
                    System.out.print("Nhập ID trường học cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    TruongHoc editTruongHoc = truongHocDAO.findTruongHocById(editId);
                    if (editTruongHoc != null) {
                        System.out.println("Nhập thông tin mới cho trường học:");
                        TruongHoc updatedTruongHoc = enterTruongHocInfo(scanner, truongHocDAO);
                        updatedTruongHoc.setTruongHocId(editId);
                        boolean updated = truongHocDAO.updateTruongHoc(updatedTruongHoc);
                        System.out.println(updated ? "Sửa thông tin trường học thành công."
                                : "Sửa thông tin trường học thất bại.");
                    } else {
                        System.out.println("Không tìm thấy trường học có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá trường học ==");
                    System.out.print("Nhập ID trường học cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = truongHocDAO.deleteTruongHoc(deleteId);
                    System.out.println(deleteResult ? "Xoá trường học thành công." : "Xoá trường học thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin trường học từ bàn phím
    private static TruongHoc enterTruongHocInfo(Scanner scanner, TruongHocDAO truongHocDAO) {
        TruongHoc truongHoc = new TruongHoc();

        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            if (truongHocDAO.isTruongHocIdExists(id)) {
                System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
            } else {
                truongHoc.setTruongHocId(id);
                break;
            }
        }

        System.out.print("Tên trường: ");
        truongHoc.setTen(scanner.nextLine());

        System.out.print("Số địa chỉ: ");
        truongHoc.setSoDiaChi(scanner.nextLine());

        System.out.print("ID Đường: ");
        truongHoc.setDuongId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Cấp bậc: ");
        truongHoc.setCapBac(scanner.nextLine());

        System.out.print("Số học sinh: ");
        truongHoc.setSoHocSinh(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số giáo viên: ");
        truongHoc.setSoGiangVien(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Hiệu trưởng: ");
        truongHoc.setHieuTruong(scanner.nextLine());

        System.out.print("ID Công trình: ");
        truongHoc.setCongTrinhId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return truongHoc;
    }

    // Phương thức để hiển thị thông tin trường học
    private static void displayTruongHocInfo(TruongHoc truongHoc) {
        System.out.println("ID: " + truongHoc.getTruongHocId());
        System.out.println("Tên trường: " + truongHoc.getTen());
        System.out.println("Số địa chỉ: " + truongHoc.getSoDiaChi());
        System.out.println("ID Đường: " + truongHoc.getDuongId());
        System.out.println("Cấp bậc: " + truongHoc.getCapBac());
        System.out.println("Số học sinh: " + truongHoc.getSoHocSinh());
        System.out.println("Số giáo viên: " + truongHoc.getSoGiangVien());
        System.out.println("Hiệu trưởng: " + truongHoc.getHieuTruong());
        System.out.println("ID Công trình: " + truongHoc.getCongTrinhId());
    }

    // Phương thức để hiển thị danh sách tất cả trường học
private static void displayAllTruongHoc(List<TruongHoc> truongHocList) {
    for (TruongHoc truongHoc : truongHocList) {
        displayTruongHocInfo(truongHoc);
        System.out.println("-------------");
    }
}

}