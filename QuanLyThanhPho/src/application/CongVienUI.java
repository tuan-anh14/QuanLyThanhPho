package ui;

import dao.CongVienDAO;
import entity.CongVien;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class CongVienUI extends JFrame {

    private CongVienDAO congVienDAO;

    private JTextField txtId;
    private JTextField txtTen;
    private JTextField txtSoDiaChi;
    private JTextField txtDuongId;
    private JTextField txtSoKhachMotNgay;
    private JTextField txtDienTich;
    private JTextField txtQuanLy;

    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnCapNhat;
    private JButton btnTimKiem;

    private JTable tblCongVien;
    private CongVienTableModel tableModel;

    private JScrollPane scrollPane; // Declare scrollPane as a class-level field

    public CongVienUI() {
        congVienDAO = new CongVienDAO();

        setTitle("Quản lý Công viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        initializeComponents();
        setupLayout();
        loadData();

        setLocationRelativeTo(null); // Center the JFrame on screen
    }

    private void initializeComponents() {
        txtId = new JTextField(10);
        txtTen = new JTextField(20);
        txtSoDiaChi = new JTextField(20);
        txtDuongId = new JTextField(10);
        txtSoKhachMotNgay = new JTextField(10);
        txtDienTich = new JTextField(10);
        txtQuanLy = new JTextField(20);

        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(e -> saveCongVien());

        btnXoa = new JButton("Xoá");
        btnXoa.addActionListener(e -> deleteCongVien());

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.addActionListener(e -> updateCongVien());

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.addActionListener(e -> findCongVienById());

        tableModel = new CongVienTableModel(congVienDAO.findAllCongVien());
        tblCongVien = new JTable(tableModel);

        // Initialize scrollPane here
        scrollPane = new JScrollPane(tblCongVien);
        scrollPane.setPreferredSize(new Dimension(700, 400));
    }

    private void setupLayout() {
        JPanel pnlInput = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Label và TextField cho ID
        pnlInput.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtId, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Tên
        pnlInput.add(new JLabel("Tên:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtTen, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Số địa chỉ
        pnlInput.add(new JLabel("Số địa chỉ:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtSoDiaChi, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho ID Đường
        pnlInput.add(new JLabel("ID Đường:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtDuongId, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Số khách một ngày
        pnlInput.add(new JLabel("Số khách một ngày:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtSoKhachMotNgay, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Diện tích
        pnlInput.add(new JLabel("Diện tích:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtDienTich, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Quản lý
        pnlInput.add(new JLabel("Quản lý:"), gbc);
        gbc.gridx++;
        pnlInput.add(txtQuanLy, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Button panel
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnCapNhat);
        pnlButtons.add(btnTimKiem);

        // Main panel layout
        setLayout(new BorderLayout());
        add(pnlInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void saveCongVien() {
        int id = Integer.parseInt(txtId.getText());
        String ten = txtTen.getText();
        String soDiaChi = txtSoDiaChi.getText();
        int duongId = Integer.parseInt(txtDuongId.getText());
        int soKhachMotNgay = Integer.parseInt(txtSoKhachMotNgay.getText());
        double dienTich = Double.parseDouble(txtDienTich.getText());
        String quanLy = txtQuanLy.getText();

        CongVien congVien = new CongVien();
        congVien.setCongVienId(id);
        congVien.setTen(ten);
        congVien.setSoDiaChi(soDiaChi);
        congVien.setDuongId(duongId);
        congVien.setSoKhachMotNgay(soKhachMotNgay);
        congVien.setDienTich(dienTich);
        congVien.setQuanLy(quanLy);

        boolean result = congVienDAO.addCongVien(congVien);
        if (result) {
            JOptionPane.showMessageDialog(this, "Thêm công viên thành công.");
            tableModel.setData(congVienDAO.findAllCongVien());
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm công viên thất bại.");
        }
    }

    private void deleteCongVien() {
        int id = Integer.parseInt(txtId.getText());
        boolean result = congVienDAO.deleteCongVien(id);
        if (result) {
            JOptionPane.showMessageDialog(this, "Xoá công viên thành công.");
            tableModel.setData(congVienDAO.findAllCongVien());
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Xoá công viên thất bại.");
        }
    }

    private void updateCongVien() {
        int id = Integer.parseInt(txtId.getText());
        String ten = txtTen.getText();
        String soDiaChi = txtSoDiaChi.getText();
        int duongId = Integer.parseInt(txtDuongId.getText());
        int soKhachMotNgay = Integer.parseInt(txtSoKhachMotNgay.getText());
        double dienTich = Double.parseDouble(txtDienTich.getText());
        String quanLy = txtQuanLy.getText();

        CongVien congVien = new CongVien();
        congVien.setCongVienId(id);
        congVien.setTen(ten);
        congVien.setSoDiaChi(soDiaChi);
        congVien.setDuongId(duongId);
        congVien.setSoKhachMotNgay(soKhachMotNgay);
        congVien.setDienTich(dienTich);
        congVien.setQuanLy(quanLy);

        boolean result = congVienDAO.updateCongVien(congVien);
        if (result) {
            JOptionPane.showMessageDialog(this, "Cập nhật công viên thành công.");
            tableModel.setData(congVienDAO.findAllCongVien());
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật công viên thất bại.");
        }
    }

    private void findCongVienById() {
        int id = Integer.parseInt(txtId.getText());
        CongVien congVien = congVienDAO.findCongVienById(id);
        if (congVien != null) {
            txtTen.setText(congVien.getTen());
            txtSoDiaChi.setText(congVien.getSoDiaChi());
            txtDuongId.setText(String.valueOf(congVien.getDuongId()));
            txtSoKhachMotNgay.setText(String.valueOf(congVien.getSoKhachMotNgay()));
            txtDienTich.setText(String.valueOf(congVien.getDienTich()));
            txtQuanLy.setText(congVien.getQuanLy());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy công viên có ID " + id);
        }
    }

    private void loadData() {
        List<CongVien> congVienList = congVienDAO.findAllCongVien();
        tableModel.setData(congVienList);
    }

    private void clearFields() {
        txtId.setText("");
        txtTen.setText("");
        txtSoDiaChi.setText("");
        txtDuongId.setText("");
        txtSoKhachMotNgay.setText("");
        txtDienTich.setText("");
        txtQuanLy.setText("");
    }

    // Lớp CongVienTableModel để hiển thị dữ liệu trong JTable
    private class CongVienTableModel extends AbstractTableModel {
        private List<CongVien> congVienList;
        private final String[] columnNames = {"ID", "Tên", "Số địa chỉ", "ID Đường", "Số khách một ngày", "Diện tích", "Quản lý"};

        public CongVienTableModel(List<CongVien> congVienList) {
            this.congVienList = congVienList;
        }

        public void setData(List<CongVien> congVienList) {
            this.congVienList = congVienList;
            fireTableDataChanged();
        }

        public int getRowCount() {
            return congVienList.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            CongVien congVien = congVienList.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return congVien.getCongVienId();
                case 1:
                    return congVien.getTen();
                case 2:
                    return congVien.getSoDiaChi();
                case 3:
                    return congVien.getDuongId();
                case 4:
                    return congVien.getSoKhachMotNgay();
                case 5:
                    return congVien.getDienTich();
                case 6:
                    return congVien.getQuanLy();
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CongVienUI congVienUI = new CongVienUI();
                congVienUI.setVisible(true);
            }
        });
    }
}