package ui;

import java.awt.EventQueue;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.QuanDAO;
import entity.Quan;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuanUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtQuanId;
    private JTextField txtTenQuan;
    private JTextField txtSoLuongNguoi;
    private JTextField txtSoLuongDuong;
    private JTextField txtSoLuongCongTrinh;
    private JTable table;

    private QuanDAO quanDAO;
    private DefaultTableModel model;

    /**
     * Create the frame.
     */
    public QuanUI() {
        setTitle("Quản lý thông tin quận");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblQuanId = new JLabel("ID Quận:");
        lblQuanId.setBounds(10, 20, 80, 14);
        contentPane.add(lblQuanId);

        txtQuanId = new JTextField();
        txtQuanId.setBounds(100, 20, 120, 20);
        contentPane.add(txtQuanId);
        txtQuanId.setColumns(10);

        JLabel lblTenQuan = new JLabel("Tên Quận:");
        lblTenQuan.setBounds(10, 50, 80, 14);
        contentPane.add(lblTenQuan);

        txtTenQuan = new JTextField();
        txtTenQuan.setBounds(100, 50, 120, 20);
        contentPane.add(txtTenQuan);
        txtTenQuan.setColumns(10);

        JLabel lblSoLuongNguoi = new JLabel("Số lượng Người:");
        lblSoLuongNguoi.setBounds(10, 80, 100, 14);
        contentPane.add(lblSoLuongNguoi);

        txtSoLuongNguoi = new JTextField();
        txtSoLuongNguoi.setBounds(100, 80, 120, 20);
        contentPane.add(txtSoLuongNguoi);
        txtSoLuongNguoi.setColumns(10);

        JLabel lblSoLuongDuong = new JLabel("Số lượng Đường:");
        lblSoLuongDuong.setBounds(10, 110, 100, 14);
        contentPane.add(lblSoLuongDuong);

        txtSoLuongDuong = new JTextField();
        txtSoLuongDuong.setBounds(100, 110, 120, 20);
        contentPane.add(txtSoLuongDuong);
        txtSoLuongDuong.setColumns(10);

        JLabel lblSoLuongCongTrinh = new JLabel("Số lượng Công trình:");
        lblSoLuongCongTrinh.setBounds(10, 140, 120, 14);
        contentPane.add(lblSoLuongCongTrinh);

        txtSoLuongCongTrinh = new JTextField();
        txtSoLuongCongTrinh.setBounds(130, 140, 120, 20);
        contentPane.add(txtSoLuongCongTrinh);
        txtSoLuongCongTrinh.setColumns(10);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themQuan();
            }
        });
        btnThem.setBounds(10, 170, 89, 23);
        contentPane.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaQuan();
            }
        });
        btnSua.setBounds(109, 170, 89, 23);
        contentPane.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaQuan();
            }
        });
        btnXoa.setBounds(208, 170, 89, 23);
        contentPane.add(btnXoa);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 204, 614, 146);
        contentPane.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID Quận", "Tên Quận", "Số lượng Người", "Số lượng Đường", "Số lượng Công trình" }
        );
        table.setModel(model);
        scrollPane.setViewportView(table);

        // Initialize DAO
        quanDAO = new QuanDAO();

        // Load table data
        loadDanhSachQuan();

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    // Method to load danh sach quan from database
    private void loadDanhSachQuan() {
        // Clear table
        model.setRowCount(0);

        // Get danh sach quan from DAO
        List<Quan> danhSachQuan = quanDAO.getAllQuan();

        // Add danh sach quan to table
        for (Quan quan : danhSachQuan) {
            Object[] row = {
                quan.getQuanId(),
                quan.getTenQuan(),
                quan.getSoLuongNguoi(),
                quan.getSoLuongDuong(),
                quan.getSoLuongCongTrinh()
            };
            model.addRow(row);
        }
    }

    // Method to add new quan
    private void themQuan() {
        // Create new Quan object
        Quan quan = new Quan();

        // Set values from input fields
        quan.setQuanId(Integer.parseInt(txtQuanId.getText()));
        quan.setTenQuan(txtTenQuan.getText());
        quan.setSoLuongNguoi(Integer.parseInt(txtSoLuongNguoi.getText()));
        quan.setSoLuongDuong(Integer.parseInt(txtSoLuongDuong.getText()));
        quan.setSoLuongCongTrinh(Integer.parseInt(txtSoLuongCongTrinh.getText()));

        // Add Quan object to database via DAO
        quanDAO.addQuan(quan);

        // Reload table
        loadDanhSachQuan();

        // Clear input fields
        clearFields();
    }

    // Method to update existing quan
    private void suaQuan() {
        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return; // No row selected, do nothing
        }

        // Get Quan object from selected row
        int quanId = (int) table.getValueAt(selectedRow, 0);
        Quan quan = quanDAO.findQuanById(quanId);

        // Update Quan object with new values
        quan.setTenQuan(txtTenQuan.getText());
        quan.setSoLuongNguoi(Integer.parseInt(txtSoLuongNguoi.getText()));
        quan.setSoLuongDuong(Integer.parseInt(txtSoLuongDuong.getText()));
        quan.setSoLuongCongTrinh(Integer.parseInt(txtSoLuongCongTrinh.getText()));

        // Update Quan object in database via DAO
        quanDAO.updateQuan(quan);

        // Reload table
        loadDanhSachQuan();

        // Clear input fields
        clearFields();
    }

    // Method to delete existing quan
    private void xoaQuan() {
        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return; // No row selected, do nothing
        }

        // Get Quan object from selected row
        int quanId = (int) table.getValueAt(selectedRow, 0);

        // Delete Quan object from database via DAO
        quanDAO.deleteQuan(quanId);

        // Reload table
        loadDanhSachQuan();

        // Clear input fields
        clearFields();
    }

    // Method to clear input fields
    private void clearFields() {
        txtQuanId.setText("");
        txtTenQuan.setText("");
        txtSoLuongNguoi.setText("");
        txtSoLuongDuong.setText("");
        txtSoLuongCongTrinh.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QuanUI frame = new QuanUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}