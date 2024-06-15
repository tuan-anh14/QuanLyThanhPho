package ui;

import dao.CongVienDAO;
import entity.CongVien;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class CongVienUI extends JPanel {
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

    public CongVienUI() {
        congVienDAO = new CongVienDAO();

        initializeComponents();
        loadData();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel pnlInput = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Label và TextField cho ID
        pnlInput.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        txtId = new JTextField(10);
        pnlInput.add(txtId, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Tên
        pnlInput.add(new JLabel("Tên:"), gbc);
        gbc.gridx++;
        txtTen = new JTextField(20);
        pnlInput.add(txtTen, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Số địa chỉ
        pnlInput.add(new JLabel("Số địa chỉ:"), gbc);
        gbc.gridx++;
        txtSoDiaChi = new JTextField(20);
        pnlInput.add(txtSoDiaChi, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho ID Đường
        pnlInput.add(new JLabel("ID Đường:"), gbc);
        gbc.gridx++;
        txtDuongId = new JTextField(10);
        pnlInput.add(txtDuongId, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Số khách một ngày
        pnlInput.add(new JLabel("Số khách một ngày:"), gbc);
        gbc.gridx++;
        txtSoKhachMotNgay = new JTextField(10);
        pnlInput.add(txtSoKhachMotNgay, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Diện tích
        pnlInput.add(new JLabel("Diện tích:"), gbc);
        gbc.gridx++;
        txtDienTich = new JTextField(10);
        pnlInput.add(txtDienTich, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Label và TextField cho Quản lý
        pnlInput.add(new JLabel("Quản lý:"), gbc);
        gbc.gridx++;
        txtQuanLy = new JTextField(20);
        pnlInput.add(txtQuanLy, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Button panel
        JPanel pnlButtons = new JPanel();
        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(e -> saveCongVien());
        pnlButtons.add(btnLuu);

        btnXoa = new JButton("Xoá");
        btnXoa.addActionListener(e -> deleteCongVien());
        pnlButtons.add(btnXoa);

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.addActionListener(e -> updateCongVien());
        pnlButtons.add(btnCapNhat);

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.addActionListener(e -> findCongVienById());
        pnlButtons.add(btnTimKiem);

        // Table panel
        tableModel = new CongVienTableModel(congVienDAO.findAllCongVien());
        tblCongVien = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblCongVien);

        // Add components to the main panel
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
			// TODO Auto-generated method stub
			return 0;
		}

		
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
    }
}
