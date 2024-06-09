package dao;

import entity.Nha;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NhaDAO {

    // Kiểm tra sự tồn tại của ID ngôi nhà
    public boolean isNhaIdExists(int nhaId) {
        String query = "SELECT COUNT(*) FROM nha WHERE nha_id = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, nhaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra sự tồn tại của số nhà
    public boolean isSoNhaExists(String soNha) {
        String query = "SELECT COUNT(*) FROM nha WHERE so_nha = ?";
        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, soNha);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm mới một ngôi nhà
    public boolean addNha(Nha nha) {
        if (isNhaIdExists(nha.getNhaId())) {
            System.out.println("ID " + nha.getNhaId() + " đã tồn tại.");
            return false;
        }

        if (isSoNhaExists(nha.getSoNha())) {
            System.out.println("Số nhà " + nha.getSoNha() + " đã tồn tại.");
            return false;
        }

        String query = "INSERT INTO nha (nha_id, so_nha, duong_id, loai_nha, cong_trinh_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nha.getNhaId());
            preparedStatement.setString(2, nha.getSoNha());
            preparedStatement.setInt(3, nha.getDuongId());
            preparedStatement.setString(4, nha.getLoaiNha());
            preparedStatement.setInt(5, nha.getCongTrinhId());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin một ngôi nhà
    public boolean updateNha(Nha nha) {
        String query = "UPDATE nha SET so_nha = ?, duong_id = ?, loai_nha = ?, cong_trinh_id = ? WHERE nha_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nha.getSoNha());
            preparedStatement.setInt(2, nha.getDuongId());
            preparedStatement.setString(3, nha.getLoaiNha());
            preparedStatement.setInt(4, nha.getCongTrinhId());
            preparedStatement.setInt(5, nha.getNhaId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một ngôi nhà
    public boolean deleteNha(int nhaId) {
        String query = "DELETE FROM nha WHERE nha_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nhaId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm một ngôi nhà theo ID
    public Nha findNhaById(int nhaId) {
        String query = "SELECT * FROM nha WHERE nha_id = ?";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nhaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Nha nha = new Nha();
                nha.setNhaId(resultSet.getInt("nha_id"));
                nha.setSoNha(resultSet.getString("so_nha"));
                nha.setDuongId(resultSet.getInt("duong_id"));
                nha.setLoaiNha(resultSet.getString("loai_nha"));
                nha.setCongTrinhId(resultSet.getInt("cong_trinh_id"));
                return nha;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tất cả ngôi nhà
    public List<Nha> findAllNha() {
        List<Nha> nhaList = new ArrayList<>();
        String query = "SELECT * FROM nha";

        try (Connection connection = MyConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Nha nha = new Nha();
                nha.setNhaId(resultSet.getInt("nha_id"));
                nha.setSoNha(resultSet.getString("so_nha"));
                nha.setDuongId(resultSet.getInt("duong_id"));
                nha.setLoaiNha(resultSet.getString("loai_nha"));
                nha.setCongTrinhId(resultSet.getInt("cong_trinh_id"));

                nhaList.add(nha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhaList;
    }

    // Phương thức main để kiểm tra chức năng
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NhaDAO nhaDAO = new NhaDAO();

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("1. Thêm ngôi nhà");
            System.out.println("2. Tìm kiếm ngôi nhà theo ID");
            System.out.println("3. Hiển thị danh sách ngôi nhà");
            System.out.println("4. Sửa thông tin ngôi nhà");
            System.out.println("5. Xoá ngôi nhà");
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
                    System.out.println("== Thêm ngôi nhà ==");
                    Nha newNha = enterNhaInfo(scanner, nhaDAO);
                    boolean added = nhaDAO.addNha(newNha);
                    System.out.println(added ? "Thêm ngôi nhà thành công." : "Thêm ngôi nhà thất bại.");
                    break;
                case 2:
                    System.out.println("== Tìm kiếm ngôi nhà theo ID ==");
                    System.out.print("Nhập ID ngôi nhà cần tìm: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    Nha foundNha = nhaDAO.findNhaById(searchId);
                    if (foundNha != null) {
                        displayNhaInfo(foundNha);
                    } else {
                        System.out.println("Không tìm thấy ngôi nhà có ID " + searchId);
                    }
                    break;
                case 3:
                    System.out.println("== Hiển thị danh sách ngôi nhà ==");
                    List<Nha> nhaList = nhaDAO.findAllNha();
                    displayAllNha(nhaList);
                    break;
                case 4:
                    System.out.println("== Sửa thông tin ngôi nhà ==");
                    System.out.print("Nhập ID ngôi nhà cần chỉnh sửa: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Đọc kí tự newline
                    Nha editNha = nhaDAO.findNhaById(editId);
                    if (editNha != null) {
                        System.out.println("Nhập thông tin mới cho ngôi nhà:");
                        Nha updatedNha = enterNhaInfo(scanner, nhaDAO);
                        updatedNha.setNhaId(editId);
                        boolean updated = nhaDAO.updateNha(updatedNha);
                        System.out.println(updated ? "Sửa thông tin ngôi nhà thành công."
                                : "Sửa thông tin ngôi nhà thất bại.");
                    } else {
                        System.out.println("Không tìm thấy ngôi nhà có ID " + editId);
                    }
                    break;
                case 5:
                    System.out.println("== Xoá ngôi nhà ==");
                    System.out.print("Nhập ID ngôi nhà cần xoá: ");
                    int deleteId = scanner.nextInt();
                    boolean deleteResult = nhaDAO.deleteNha(deleteId);
                    System.out.println(deleteResult ? "Xoá ngôi nhà thành công." : "Xoá ngôi nhà thất bại.");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    // Phương thức để nhập thông tin ngôi nhà từ bàn phím
    private static Nha enterNhaInfo(Scanner scanner, NhaDAO nhaDAO) {
        Nha nha = new Nha();

        while (true) {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Đọc kí tự newline

            if (nhaDAO.isNhaIdExists(id)) {
                System.out.println("ID " + id + " đã tồn tại. Vui lòng nhập lại ID khác.");
            } else {
                nha.setNhaId(id);
                break;
            }
        }

        System.out.print("Số nhà: ");
        String soNha = scanner.nextLine();
        while (true) {
            if (nhaDAO.isSoNhaExists(soNha)) {
                System.out.println("Số nhà " + soNha + " đã tồn tại. Vui lòng nhập lại số nhà khác.");
                soNha = scanner.nextLine();
            } else {
                nha.setSoNha(soNha);
                break;
            }
        }

        System.out.print("ID Đường: ");
        nha.setDuongId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        System.out.print("Loại nhà: ");
        nha.setLoaiNha(scanner.nextLine());

        System.out.print("ID Công trình: ");
        nha.setCongTrinhId(scanner.nextInt());
        scanner.nextLine(); // Đọc kí tự newline

        return nha;
    }

    // Phương thức để hiển thị thông tin ngôi nhà
    private static void displayNhaInfo(Nha nha) {
        System.out.println("ID: " + nha.getNhaId());
        System.out.println("Số nhà: " + nha.getSoNha());

        System.out.println("ID Đường: " + nha.getDuongId());
        System.out.println("Loại nhà: " + nha.getLoaiNha());
        System.out.println("ID Công trình: " + nha.getCongTrinhId());
    }

    // Phương thức để hiển thị danh sách tất cả ngôi nhà
    private static void displayAllNha(List<Nha> nhaList) {
        for (Nha nha : nhaList) {
            displayNhaInfo(nha);
            System.out.println("-------------");
        }
    }
}
