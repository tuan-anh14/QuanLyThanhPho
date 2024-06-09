package dao;

import entity.CongVien;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CongVienDAO {

    // Kiểm tra sự tồn tại của ID công viên
    public boolean isCongVienIdExists(int congVienId) {
        String query = "SELECT COUNT(*) FROM cong_vien WHERE cong_vien_id = ?";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, congVienId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra sự tồn tại của tên công viên
    public boolean isTenCongVienExists(String ten) {
        String query = "SELECT COUNT(*) FROM cong_vien WHERE ten = ?";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ten);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một công viên
    public boolean addCongVien(CongVien congVien) {
        if (isCongVienIdExists(congVien.getCongVienId())) {
            System.out.println("ID " + congVien.getCongVienId() + " đã tồn tại.");
            return false;
        }

        if (isTenCongVienExists(congVien.getTen())) {
            System.out.println("Tên công viên " + congVien.getTen() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO cong_vien (cong_vien_id, ten, so_dia_chi, duong_id, so_khach_mot_ngay, dien_tich, quan_ly, cong_trinh_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, congVien.getCongVienId());
            preparedStatement.setString(2, congVien.getTen());
            preparedStatement.setString(3, congVien.getSoDiaChi());
            preparedStatement.setInt(4, congVien.getDuongId());
            preparedStatement.setInt(5, congVien.getSoKhachMotNgay());
            preparedStatement.setDouble(6, congVien.getDienTich());
            preparedStatement.setString(7, congVien.getQuanLy());
            preparedStatement.setInt(8, congVien.getCongTrinhId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một công viên
    public boolean updateCongVien(CongVien congVien) {
        String query = "UPDATE cong_vien SET ten = ?, so_dia_chi = ?, duong_id = ?, so_khach_mot_ngay = ?, dien_tich = ?, quan_ly = ?, cong_trinh_id = ? WHERE cong_vien_id = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, congVien.getTen());
            preparedStatement.setString(2, congVien.getSoDiaChi());
            preparedStatement.setInt(3, congVien.getDuongId());
            preparedStatement.setInt(4, congVien.getSoKhachMotNgay());
            preparedStatement.setDouble(5, congVien.getDienTich());
            preparedStatement.setString(6, congVien.getQuanLy());
            preparedStatement.setInt(7, congVien.getCongTrinhId());
            preparedStatement.setInt(8, congVien.getCongVienId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một công viên
    public boolean deleteCongVien(int congVienId) {
        String query = "DELETE FROM cong_vien WHERE cong_vien_id = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, congVienId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một công viên theo ID
    public CongVien findCongVienById(int congVienId) {
        String query = "SELECT * FROM cong_vien WHERE cong_vien_id = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, congVienId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CongVien congVien = new CongVien();
                congVien.setCongVienId(resultSet.getInt("cong_vien_id"));
                congVien.setTen(resultSet.getString("ten"));
                congVien.setSoDiaChi(resultSet.getString("so_dia_chi"));
                congVien.setDuongId(resultSet.getInt("duong_id"));
                congVien.setSoKhachMotNgay(resultSet.getInt("so_khach_mot_ngay"));
                congVien.setDienTich(resultSet.getDouble("dien_tich"));
                congVien.setQuanLy(resultSet.getString("quan_ly"));
                congVien.setCongTrinhId(resultSet.getInt("cong_trinh_id"));
                return congVien;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả công viên
    public List<CongVien> findAllCongVien() {
        List<CongVien> congVienList = new ArrayList<>();
        String query = "SELECT * FROM cong_vien";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                CongVien congVien = new CongVien();
                congVien.setCongVienId(resultSet.getInt("cong_vien_id"));
                congVien.setTen(resultSet.getString("ten"));
                congVien.setSoDiaChi(resultSet.getString("so_dia_chi"));
                congVien.setDuongId(resultSet.getInt("duong_id"));
                congVien.setSoKhachMotNgay(resultSet.getInt("so_khach_mot_ngay"));
                congVien.setDienTich(resultSet.getDouble("dien_tich"));
                congVien.setQuanLy(resultSet.getString("quan_ly"));
                congVien.setCongTrinhId(resultSet.getInt("cong_trinh_id"));

                congVienList.add(congVien);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return congVienList;
    }

        // Phương thức main để kiểm tra chức năng
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            CongVienDAO congVienDAO = new CongVienDAO();
    
            while (true) {
                System.out.println("==== MENU ====");
                System.out.println("1. Thêm công viên");
                System.out.println("2. Tìm kiếm công viên theo ID");
                System.out.println("3. Hiển thị danh sách công viên");
                System.out.println("4. Sửa thông tin công viên");
                System.out.println("5. Xóa công viên");
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
                        System.out.println("== Thêm công viên ==");
                        CongVien newCongVien = enterCongVienInfo(scanner, congVienDAO);
                        boolean added = congVienDAO.addCongVien(newCongVien);
                        System.out.println(added ? "Thêm công viên thành công." : "Thêm công viên thất bại.");
                        break;
                    case 2:
                        System.out.println("== Tìm kiếm công viên theo ID ==");
                        System.out.print("Nhập ID công viên cần tìm: ");
                        int searchId = scanner.nextInt();
                        scanner.nextLine(); // Đọc kí tự newline
                        CongVien foundCongVien = congVienDAO.findCongVienById(searchId);
                        if (foundCongVien != null) {
                            displayCongVienInfo(foundCongVien);
                        } else {
                            System.out.println("Không tìm thấy công viên có ID " + searchId);
                        }
                        break;
                    case 3:
                        System.out.println("== Hiển thị danh sách công viên ==");
                        List<CongVien> congVienList = congVienDAO.findAllCongVien();
                        displayAllCongVien(congVienList);
                        break;
                    case 4:
                        System.out.println("== Sửa thông tin công viên ==");
                        System.out.print("Nhập ID công viên cần chỉnh sửa: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine(); // Đọc kí tự newline
                        CongVien editCongVien = congVienDAO.findCongVienById(editId);
                        if (editCongVien != null) {
                            System.out.println("Nhập thông tin mới cho công viên:");
                            CongVien updatedCongVien = enterCongVienInfo(scanner, congVienDAO);
                            updatedCongVien.setCongVienId(editId);
                            boolean updated = congVienDAO.updateCongVien(updatedCongVien);
                            System.out.println(updated ? "Sửa thông tin công viên thành công."
                                    : "Sửa thông tin công viên thất bại.");
                        } else {
                            System.out.println("Không tìm thấy công viên có ID " + editId);
                        }
                        break;
                    case 5:
                        System.out.println("== Xóa công viên ==");
                        System.out.print("Nhập ID công viên cần xóa: ");
                        int deleteId = scanner.nextInt();
                        boolean deleteResult = congVienDAO.deleteCongVien(deleteId);
                        System.out.println(deleteResult ? "Xóa công viên thành công." : "Xóa công viên thất bại.");
                        break;
                    default:
                        System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                        break;
                }
            }
        }
    
        // Phương thức để nhập thông tin công viên từ bàn phím
        private static CongVien enterCongVienInfo(Scanner scanner, CongVienDAO congVienDAO) {
            CongVien congVien = new CongVien();
    
            while (true) {
                System.out.print("ID công viên: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Đọc kí tự newline
    
                if (congVienDAO.isCongVienIdExists(id)) {
                    System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
                } else {
                    congVien.setCongVienId(id);
                    break;
                }
            }
    
            System.out.print("Tên công viên: ");
            String ten = scanner.nextLine();
            while (true) {
                if (congVienDAO.isTenCongVienExists(ten)) {
                    System.out.println("Tên công viên " + ten + " đã tồn tại. Vui lòng nhập lại tên khác.");
                    ten = scanner.nextLine();
                } else {
                    congVien.setTen(ten);
                    break;
                }
            }
    
            System.out.print("Số địa chỉ: ");
            congVien.setSoDiaChi(scanner.nextLine());
    
            System.out.print("ID đường: ");
            congVien.setDuongId(scanner.nextInt());
            scanner.nextLine(); // Đọc kí tự newline
    
            System.out.print("Số khách một ngày: ");
            congVien.setSoKhachMotNgay(scanner.nextInt());
            scanner.nextLine(); // Đọc kí tự newline
    
            System.out.print("Diện tích: ");
            congVien.setDienTich(scanner.nextDouble());
            scanner.nextLine(); // Đọc kí tự newline
    
            System.out.print("Quản lý: ");
            congVien.setQuanLy(scanner.nextLine());
    
            System.out.print("ID công trình: ");
            congVien.setCongTrinhId(scanner.nextInt());
            scanner.nextLine(); // Đọc kí tự newline
    
            return congVien;
        }
    
        // Phương thức để hiển thị thông tin công viên
        private static void displayCongVienInfo(CongVien congVien) {
            System.out.println("ID công viên: " + congVien.getCongVienId());
            System.out.println("Tên: " + congVien.getTen());
            System.out.println("Số địa chỉ: " + congVien.getSoDiaChi());
            System.out.println("ID đường: " + congVien.getDuongId());
            System.out.println("Số khách một ngày: " + congVien.getSoKhachMotNgay());
            System.out.println("Diện tích: " + congVien.getDienTich());
            System.out.println("Quản lý: " + congVien.getQuanLy());
            System.out.println("ID công trình: " + congVien.getCongTrinhId());
        }
    
        // Phương thức để hiển thị danh sách tất cả công viên
        private static void displayAllCongVien(List<CongVien> congVienList) {
            for (CongVien congVien : congVienList) {
                displayCongVienInfo(congVien);
                System.out.println("-------------");
            }
        }
    }
    
