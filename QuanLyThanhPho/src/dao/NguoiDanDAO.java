package dao;

import entity.NguoiDan;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NguoiDanDAO {

    // Kiểm tra sự tồn tại của ID
    public boolean isNguoiDanIdExists(int nguoiDanId) {
        String query = "SELECT COUNT(*) FROM Nguoi_dan WHERE Nguoi_dan_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, nguoiDanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một người dân
    public boolean addNguoiDan(NguoiDan nguoiDan) {
        if (isNguoiDanIdExists(nguoiDan.getNguoiDanId())) {
            System.out.println("ID " + nguoiDan.getNguoiDanId() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO Nguoi_dan (Nguoi_dan_id, Ho, Ten, Ngay_sinh, Gioi_tinh, CCCD, So_nha, Nghe_nghiep, Duong_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nguoiDan.getNguoiDanId());
            preparedStatement.setString(2, nguoiDan.getHo());
            preparedStatement.setString(3, nguoiDan.getTen());
            preparedStatement.setString(4, nguoiDan.getNgaySinh());
            preparedStatement.setString(5, nguoiDan.getGioiTinh());
            preparedStatement.setString(6, nguoiDan.getCccd());
            preparedStatement.setString(7, nguoiDan.getSoNha());
            preparedStatement.setString(8, nguoiDan.getNgheNghiep());
            preparedStatement.setInt(9, nguoiDan.getDuongId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một người dân
    public boolean updateNguoiDan(NguoiDan nguoiDan) {
        String query = "UPDATE Nguoi_dan SET Ho = ?, Ten = ?, Ngay_sinh = ?, Gioi_tinh = ?, CCCD = ?, So_nha = ?, Nghe_nghiep = ?, Duong_id = ? WHERE Nguoi_dan_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nguoiDan.getHo());
            preparedStatement.setString(2, nguoiDan.getTen());
            preparedStatement.setString(3, nguoiDan.getNgaySinh());
            preparedStatement.setString(4, nguoiDan.getGioiTinh());
            preparedStatement.setString(5, nguoiDan.getCccd());
            preparedStatement.setString(6, nguoiDan.getSoNha());
            preparedStatement.setString(7, nguoiDan.getNgheNghiep());
            preparedStatement.setInt(8, nguoiDan.getDuongId());
            preparedStatement.setInt(9, nguoiDan.getNguoiDanId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một người dân
    public boolean deleteNguoiDan(int nguoiDanId) {
        String query = "DELETE FROM Nguoi_dan WHERE Nguoi_dan_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nguoiDanId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một người dân theo ID
    public NguoiDan findNguoiDanById(int nguoiDanId) {
        String query = "SELECT * FROM Nguoi_dan WHERE Nguoi_dan_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nguoiDanId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                NguoiDan nguoiDan = new NguoiDan();
                nguoiDan.setNguoiDanId(resultSet.getInt("Nguoi_dan_id"));
                nguoiDan.setHo(resultSet.getString("Ho"));
                nguoiDan.setTen(resultSet.getString("Ten"));
                nguoiDan.setNgaySinh(resultSet.getString("Ngay_sinh"));
                nguoiDan.setGioiTinh(resultSet.getString("Gioi_tinh"));
                nguoiDan.setCccd(resultSet.getString("CCCD"));
                nguoiDan.setSoNha(resultSet.getString("So_nha"));
                nguoiDan.setNgheNghiep(resultSet.getString("Nghe_nghiep"));
                nguoiDan.setDuongId(resultSet.getInt("Duong_id"));
                return nguoiDan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả người dân
    public List<NguoiDan> findAllNguoiDan() {
        List<NguoiDan> nguoiDanList = new ArrayList<>();
        String query = "SELECT * FROM Nguoi_dan";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                NguoiDan nguoiDan = new NguoiDan();
                nguoiDan.setNguoiDanId(resultSet.getInt("Nguoi_dan_id"));
                nguoiDan.setHo(resultSet.getString("Ho"));
                nguoiDan.setTen(resultSet.getString("Ten"));
                nguoiDan.setNgaySinh(resultSet.getString("Ngay_sinh"));
                nguoiDan.setGioiTinh(resultSet.getString("Gioi_tinh"));
                nguoiDan.setCccd(resultSet.getString("CCCD"));
                nguoiDan.setSoNha(resultSet.getString("So_nha"));
                nguoiDan.setNgheNghiep(resultSet.getString("Nghe_nghiep"));
                nguoiDan.setDuongId(resultSet.getInt("Duong_id"));

                nguoiDanList.add(nguoiDan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nguoiDanList;
    }

    // Phương thức main để kiểm tra chức năng
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NguoiDanDAO nguoiDanDAO = new NguoiDanDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm người dân");
            System.out.println("2. Tìm kiếm người dân theo ID");
            System.out.println("3. Hiển thị danh sách người dân");
            System.out.println("4. Sửa thông tin người dân");
            System.out.println("5. Xoá người dân");
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
                    System.out.println("== Thêm người dân ==");
                    NguoiDan newNguoiDan = enterNguoiDanInfo(scanner, nguoiDanDAO);
                    boolean added = nguoiDanDAO.addNguoiDan(newNguoiDan);
                    System.out.println(added ? "Thêm người dân thành công." : "Thêm người dân thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm người dân theo ID ==");
                    System.out.print("Nhập ID người dân cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    NguoiDan foundNguoiDan = nguoiDanDAO.findNguoiDanById(searchId);
                    if (foundNguoiDan != null) {
                        displayNguoiDanInfo(foundNguoiDan);
                    } else {
                        System.out.println("Không tìm thấy người dân có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách người dân ==");
                    List<NguoiDan> nguoiDanList = nguoiDanDAO.findAllNguoiDan();
                    displayAllNguoiDan(nguoiDanList);
                    break;
                case 4:
                    System.out.println("== Chỉnh sửa thông tin người dân ==");
                    System.out.print("Nhập ID người dân cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    NguoiDan editNguoiDan = nguoiDanDAO.findNguoiDanById(editId);
                    if (editNguoiDan != null) {
                        System.out.println("Nhập thông tin mới cho người dân:");
                        NguoiDan updatedNguoiDan = enterNguoiDanInfo(scanner, nguoiDanDAO);
                        updatedNguoiDan.setNguoiDanId(editId);
                        boolean updated = nguoiDanDAO.updateNguoiDan(updatedNguoiDan);
                        System.out.println(updated ? "Chỉnh sửa thông tin người dân thành công."
                                : "Chỉnh sửa thông tin người dân thất bại.");
                    } else {
                        System.out.println("Không tìm thấy người dân có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá người dân ==");
                    System.out.print("Nhập ID người dân cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = nguoiDanDAO.deleteNguoiDan(deleteId);
                    System.out.println(deleteResult ? "Xoá người dân thành công." : "Xoá người dân thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin người dân
    private static NguoiDan enterNguoiDanInfo(Scanner scanner, NguoiDanDAO nguoiDanDAO) {
        NguoiDan nguoiDan = new NguoiDan();

        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            if (nguoiDanDAO.isNguoiDanIdExists(id)) {
                System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
            } else {
                nguoiDan.setNguoiDanId(id);
                break;
            }
        }

        System.out.print("Họ: ");
        nguoiDan.setHo(scanner.nextLine());

        System.out.print("Tên: ");
        nguoiDan.setTen(scanner.nextLine());

        while (true) {
            System.out.print("Ngày sinh (yyyy-MM-dd): ");
            String ngaySinh = scanner.nextLine();
            if (isValidDateFormat(ngaySinh)) {
                nguoiDan.setNgaySinh(ngaySinh);
                break;
            } else {
                System.out.println("Định dạng ngày sinh không hợp lệ. Vui lòng nhập lại.");
            }
        }

        System.out.print("Giới tính: ");
        nguoiDan.setGioiTinh(scanner.nextLine());

        System.out.print("CCCD: ");
        nguoiDan.setCccd(scanner.nextLine());

        System.out.print("Số nhà: ");
        nguoiDan.setSoNha(scanner.nextLine());

        System.out.print("Nghề nghiệp: ");
        nguoiDan.setNgheNghiep(scanner.nextLine());

        System.out.print("ID Đường: ");
        nguoiDan.setDuongId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return nguoiDan;
    }

    // Phương thức để kiểm tra định dạng ngày sinh
    private static boolean isValidDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Không cho phép các ngày không hợp lệ như 30-02-2020
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Phương thức để hiển thị thông tin người dân
    private static void displayNguoiDanInfo(NguoiDan nguoiDan) {
        System.out.println("ID: " + nguoiDan.getNguoiDanId());
        System.out.println("Họ: " + nguoiDan.getHo());
        System.out.println("Tên: " + nguoiDan.getTen());
        System.out.println("Ngày sinh: " + nguoiDan.getNgaySinh());
        System.out.println("Giới tính: " + nguoiDan.getGioiTinh());
        System.out.println("CCCD: " + nguoiDan.getCccd());
        System.out.println("Số nhà: " + nguoiDan.getSoNha());
        System.out.println("Nghề nghiệp: " + nguoiDan.getNgheNghiep());
        System.out.println("ID Đường: " + nguoiDan.getDuongId());
    }

    // Phương thức để hiển thị danh sách tất cả người dân
    private static void displayAllNguoiDan(List<NguoiDan> nguoiDanList) {
        for (NguoiDan nguoiDan : nguoiDanList) {
            displayNguoiDanInfo(nguoiDan);
            System.out.println("-------------");
        }
    }
}
