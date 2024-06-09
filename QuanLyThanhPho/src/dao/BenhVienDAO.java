package dao;

import entity.BenhVien;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BenhVienDAO {

    // Kiểm tra sự tồn tại của ID bệnh viện
    public boolean isBenhVienIdExists(int benhVienId) {
        String query = "SELECT COUNT(*) FROM benh_vien WHERE benh_vien_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, benhVienId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra sự tồn tại của số địa chỉ
    public boolean isSoDiaChiExists(String soDiaChi) {
        String query = "SELECT COUNT(*) FROM benh_vien WHERE so_dia_chi = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, soDiaChi);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một bệnh viện
    public boolean addBenhVien(BenhVien benhVien) {
        if (isBenhVienIdExists(benhVien.getBenhVienId())) {
            System.out.println("ID " + benhVien.getBenhVienId() + " đã tồn tại.");
            return false;
        }

        if (isSoDiaChiExists(benhVien.getSoDiaChi())) {
            System.out.println("Số địa chỉ " + benhVien.getSoDiaChi() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO benh_vien (benh_vien_id, so_dia_chi, duong_id, nam_thanh_lap, so_bac_si, so_benh_nhan, giam_doc, cong_trinh_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, benhVien.getBenhVienId());
            preparedStatement.setString(2, benhVien.getSoDiaChi());
            preparedStatement.setInt(3, benhVien.getDuongId());
            preparedStatement.setInt(4, benhVien.getNamThanhLap());
            preparedStatement.setInt(5, benhVien.getSoBacSi());
            preparedStatement.setInt(6, benhVien.getSoBenhNhan());
            preparedStatement.setString(7, benhVien.getGiamDoc());
            preparedStatement.setInt(8, benhVien.getCongTrinhId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một bệnh viện
    public boolean updateBenhVien(BenhVien benhVien) {
        String query = "UPDATE benh_vien SET so_dia_chi = ?, duong_id = ?, nam_thanh_lap = ?, so_bac_si = ?, so_benh_nhan = ?, giam_doc = ?, cong_trinh_id = ? WHERE benh_vien_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, benhVien.getSoDiaChi());
            preparedStatement.setInt(2, benhVien.getDuongId());
            preparedStatement.setInt(3, benhVien.getNamThanhLap());
            preparedStatement.setInt(4, benhVien.getSoBacSi());
            preparedStatement.setInt(5, benhVien.getSoBenhNhan());
            preparedStatement.setString(6, benhVien.getGiamDoc());
            preparedStatement.setInt(7, benhVien.getCongTrinhId());
            preparedStatement.setInt(8, benhVien.getBenhVienId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một bệnh viện
    public boolean deleteBenhVien(int benhVienId) {
        String query = "DELETE FROM benh_vien WHERE benh_vien_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, benhVienId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một bệnh viện theo ID
    public BenhVien findBenhVienById(int benhVienId) {
        String query = "SELECT * FROM benh_vien WHERE benh_vien_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, benhVienId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                BenhVien benhVien = new BenhVien();
                benhVien.setBenhVienId(resultSet.getInt("benh_vien_id"));
                benhVien.setSoDiaChi(resultSet.getString("so_dia_chi"));
                benhVien.setDuongId(resultSet.getInt("duong_id"));
                benhVien.setNamThanhLap(resultSet.getInt("nam_thanh_lap"));
                benhVien.setSoBacSi(resultSet.getInt("so_bac_si"));
                benhVien.setSoBenhNhan(resultSet.getInt("so_benh_nhan"));
                benhVien.setGiamDoc(resultSet.getString("giam_doc"));
                benhVien.setCongTrinhId(resultSet.getInt("cong_trinh_id"));
                return benhVien;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả bệnh viện
    public List<BenhVien> findAllBenhVien() {
        List<BenhVien> benhVienList = new ArrayList<>();
        String query = "SELECT * FROM benh_vien";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BenhVien benhVien = new BenhVien();
                benhVien.setBenhVienId(resultSet.getInt("benh_vien_id"));
                benhVien.setSoDiaChi(resultSet.getString("so_dia_chi"));
                benhVien.setDuongId(resultSet.getInt("duong_id"));
                benhVien.setNamThanhLap(resultSet.getInt("nam_thanh_lap"));
                benhVien.setSoBacSi(resultSet.getInt("so_bac_si"));
                benhVien.setSoBenhNhan(resultSet.getInt("so_benh_nhan"));
                benhVien.setGiamDoc(resultSet.getString("giam_doc"));
                benhVien.setCongTrinhId(resultSet.getInt("cong_trinh_id"));

                benhVienList.add(benhVien);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return benhVienList;
    }

    // Phương thức main để kiểm tra chức năng
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BenhVienDAO benhVienDAO = new BenhVienDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm bệnh viện");
            System.out.println("2. Tìm kiếm bệnh viện theo ID");
            System.out.println("3. Hiển thị danh sách bệnh viện");
            System.out.println("4. Sửa thông tin bệnh viện");
            System.out.println("5. Xoá bệnh viện");
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
                    System.out.println("== Thêm bệnh viện ==");
                    BenhVien newBenhVien = enterBenhVienInfo(scanner, benhVienDAO);
                    boolean added = benhVienDAO.addBenhVien(newBenhVien);
                    System.out.println(added ? "Thêm bệnh viện thành công." : "Thêm bệnh viện thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm bệnh viện theo ID ==");
                    System.out.print("Nhập ID bệnh viện cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    BenhVien foundBenhVien = benhVienDAO.findBenhVienById(searchId);
                    if (foundBenhVien != null) {
                        displayBenhVienInfo(foundBenhVien);
                    } else {
                        System.out.println("Không tìm thấy bệnh viện có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách bệnh viện ==");
                    List<BenhVien> benhVienList = benhVienDAO.findAllBenhVien();
                    displayAllBenhVien(benhVienList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin bệnh viện ==");
                    System.out.print("Nhập ID bệnh viện cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    BenhVien editBenhVien = benhVienDAO.findBenhVienById(editId);
                    if (editBenhVien != null) {
                        System.out.println("Nhập thông tin mới cho bệnh viện:");
                        BenhVien updatedBenhVien = enterBenhVienInfo(scanner, benhVienDAO);
                        updatedBenhVien.setBenhVienId(editId);
                        boolean updated = benhVienDAO.updateBenhVien(updatedBenhVien);
                        System.out.println(
                                updated ? "Sửa thông tin bệnh viện thành công." : "Sửa thông tin bệnh viện thất bại.");
                    } else {
                        System.out.println("Không tìm thấy bệnh viện có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá bệnh viện ==");
                    System.out.print("Nhập ID bệnh viện cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = benhVienDAO.deleteBenhVien(deleteId);
                    System.out.println(deleteResult ? "Xoá bệnh viện thành công." : "Xoá bệnh viện thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin bệnh viện từ bàn phím
    private static BenhVien enterBenhVienInfo(Scanner scanner, BenhVienDAO benhVienDAO) {
        BenhVien benhVien = new BenhVien();

        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            if (benhVienDAO.isBenhVienIdExists(id)) {
                System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
            } else {
                benhVien.setBenhVienId(id);
                break;
            }
        }

        System.out.print("Số địa chỉ: ");
        String soDiaChi = scanner.nextLine();
        while (true) {
            if (benhVienDAO.isSoDiaChiExists(soDiaChi)) {
                System.out.println("Số địa chỉ " + soDiaChi + " đã tồn tại. Vui lòng nhập lại số địa chỉ khác.");
                soDiaChi = scanner.nextLine();
            } else {
                benhVien.setSoDiaChi(soDiaChi);
                break;
            }
        }

        System.out.print("ID Đường: ");
        benhVien.setDuongId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Năm thành lập: ");
        benhVien.setNamThanhLap(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số bác sĩ: ");
        benhVien.setSoBacSi(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Số bệnh nhân: ");
        benhVien.setSoBenhNhan(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Giám đốc: ");
        benhVien.setGiamDoc(scanner.nextLine());

        System.out.print("ID Công trình: ");
        benhVien.setCongTrinhId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return benhVien;
    }

    // Phương thức để hiển thị thông tin bệnh viện
    private static void displayBenhVienInfo(BenhVien benhVien) {
        System.out.println("ID: " + benhVien.getBenhVienId());
        System.out.println("Số địa chỉ: " + benhVien.getSoDiaChi());
        System.out.println("ID Đường: " + benhVien.getDuongId());
        System.out.println("Năm thành lập: " + benhVien.getNamThanhLap());
        System.out.println("Số bác sĩ: " + benhVien.getSoBacSi());
        System.out.println("Số bệnh nhân: " + benhVien.getSoBenhNhan());
        System.out.println("Giám đốc: " + benhVien.getGiamDoc());
        System.out.println("ID Công trình: " + benhVien.getCongTrinhId());
    }

    // Phương thức để hiển thị danh sách tất cả bệnh viện
    private static void displayAllBenhVien(List<BenhVien> benhVienList) {
        for (BenhVien benhVien : benhVienList) {
            displayBenhVienInfo(benhVien);
            System.out.println("-------------");
        }
    }
}
