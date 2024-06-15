package ui;

import dao.DuongDAO;
import dao.KhachSanDAO;
import entity.Duong;
import entity.KhachSan;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KhachSanUI extends JPanel {

    private JTextField txtKhachSanId;
    private JTextField txtTen;
    private JTextField txtSoDiaChi;
    private JComboBox<Duong> cboDuong;
    private JTextField txtSoPhong;
    private JTextField txtSoPhongConTrong;
    private JTextField txtCheckIn;
    private JTextField txtCheckOut;
    private JTextField txtQuanLy;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JTable tableKhachSan;

    private KhachSanTableModel tableModel;
    private KhachSanDAO khachSanDAO;
    private DuongDAO duongDAO;

    private List<KhachSan> khachSanList;
    private List<Duong> duongList;

    public KhachSanUI() {
        initializeComponents();
        khachSanDAO = new KhachSanDAO();
        duongDAO = new DuongDAO();
        khachSanList = khachSanDAO.findAllKhachSan();
        duongList = duongDAO.findAllDuong();
        tableModel = new KhachSanTableModel(khachSanList);
        tableKhachSan.setModel(tableModel);
        loadDuongComboBox();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelCenter = new JPanel(new BorderLayout());

        JLabel lblKhachSanId = new JLabel("ID Khách Sạn:");
        txtKhachSanId = new JTextField(10);
        JLabel lblTen = new JLabel("Tên Khách Sạn:");
        txtTen = new JTextField(20);
        JLabel lblSoDiaChi = new JLabel("Địa chỉ:");
        txtSoDiaChi = new JTextField(20);
        JLabel lblDuong = new JLabel("Đường:");
        cboDuong = new JComboBox<>();
        JLabel lblSoPhong = new JLabel("Số phòng:");
        txtSoPhong = new JTextField(10);
        JLabel lblSoPhongConTrong = new JLabel("Số phòng còn trống:");
        txtSoPhongConTrong = new JTextField(10);
        JLabel lblCheckIn = new JLabel("Check In:");
        txtCheckIn = new JTextField(10);
        JLabel lblCheckOut = new JLabel("Check Out:");
        txtCheckOut = new JTextField(10);
        JLabel lblQuanLy = new JLabel("Quản lý:");
        txtQuanLy = new JTextField(20);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        panelTop.add(lblKhachSanId);
        panelTop.add(txtKhachSanId);
        panelTop.add(lblTen);
        panelTop.add(txtTen);
        panelTop.add(lblSoDiaChi);
        panelTop.add(txtSoDiaChi);
        panelTop.add(lblDuong);
        panelTop.add(cboDuong);
        panelTop.add(lblSoPhong);
        panelTop.add(txtSoPhong);
        panelTop.add(lblSoPhongConTrong);
        panelTop.add(txtSoPhongConTrong);
        panelTop.add(lblCheckIn);
        panelTop.add(txtCheckIn);
        panelTop.add(lblCheckOut);
        panelTop.add(txtCheckOut);
        panelTop.add(lblQuanLy);
        panelTop.add(txtQuanLy);
        panelTop.add(btnThem);
        panelTop.add(btnSua);
        panelTop.add(btnXoa);

        tableKhachSan = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableKhachSan);

        panelCenter.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addKhachSan();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateKhachSan();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteKhachSan();
            }
        });

        tableKhachSan.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableKhachSan.getSelectedRow();
            if (selectedRow >= 0) {
                KhachSan khachSan = khachSanList.get(selectedRow);
                displayKhachSan(khachSan);
            }
        });
    }

    private void loadDuongComboBox() {
        DefaultComboBoxModel<Duong> model = new DefaultComboBoxModel<>();
        for (Duong duong : duongList) {
            model.addElement(duong);
        }
        cboDuong.setModel(model);
    }

    private void addKhachSan() {
        int khachSanId = Integer.parseInt(txtKhachSanId.getText().trim());
        String ten = txtTen.getText().trim();
        String soDiaChi = txtSoDiaChi.getText().trim();
        Duong duong = (Duong) cboDuong.getSelectedItem();
        int soPhong = Integer.parseInt(txtSoPhong.getText().trim());
        int soPhongConTrong = Integer.parseInt(txtSoPhongConTrong.getText().trim());
        int checkIn = Integer.parseInt(txtCheckIn.getText().trim());
        int checkOut = Integer.parseInt(txtCheckOut.getText().trim());
        String quanLy = txtQuanLy.getText().trim();

        KhachSan newKhachSan = new KhachSan();
        newKhachSan.setKhachSanId(khachSanId);
        newKhachSan.setTen(ten);
        newKhachSan.setSoDiaChi(soDiaChi);
        newKhachSan.setDuongId(duong.getDuongId()); // Assuming Duong class has getDuongId method
        newKhachSan.setSoPhong(soPhong);
        newKhachSan.setSoPhongConTrong(soPhongConTrong);
        newKhachSan.setCheckIn(checkIn);
        newKhachSan.setCheckOut(checkOut);
        newKhachSan.setQuanLy(quanLy);

        boolean added = khachSanDAO.addKhachSan(newKhachSan);
        if (added) {
            khachSanList.add(newKhachSan);
            tableModel.fireTableDataChanged();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khách sạn thất bại.");
        }
    }

    private void updateKhachSan() {
        int selectedRow = tableKhachSan.getSelectedRow();
        if (selectedRow >= 0) {
            KhachSan selectedKhachSan = khachSanList.get(selectedRow);

            int khachSanId = Integer.parseInt(txtKhachSanId.getText().trim());
            String ten = txtTen.getText().trim();
            String soDiaChi = txtSoDiaChi.getText().trim();
            Duong duong = (Duong) cboDuong.getSelectedItem();
            int soPhong = Integer.parseInt(txtSoPhong.getText().trim());
            int soPhongConTrong = Integer.parseInt(txtSoPhongConTrong.getText().trim());
            int checkIn = Integer.parseInt(txtCheckIn.getText().trim());
            int checkOut = Integer.parseInt(txtCheckOut.getText().trim());
            String quanLy = txtQuanLy.getText().trim();

            selectedKhachSan.setKhachSanId(khachSanId);
            selectedKhachSan.setTen(ten);
            selectedKhachSan.setSoDiaChi(soDiaChi);
            selectedKhachSan.setDuongId(duong.getDuongId()); // Assuming Duong class has getDuongId method
            selectedKhachSan.setSoPhong(soPhong);
            selectedKhachSan.setSoPhongConTrong(soPhongConTrong);
            selectedKhachSan.setCheckIn(checkIn);
            selectedKhachSan.setCheckOut(checkOut);
            selectedKhachSan.setQuanLy(quanLy);

            boolean updated = khachSanDAO.updateKhachSan(selectedKhachSan);
            if (updated) {
                tableModel.fireTableDataChanged();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa khách sạn thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách sạn để sửa.");
        }
    }

    private void deleteKhachSan() {
        int selectedRow = tableKhachSan.getSelectedRow();
        if (selectedRow >= 0) {
            KhachSan selectedKhachSan = khachSanList.get(selectedRow);

            int khachSanId = selectedKhachSan.getKhachSanId();

            boolean deleted = khachSanDAO.deleteKhachSan(khachSanId);
            if (deleted) {
                khachSanList.remove(selectedKhachSan);
                tableModel.fireTableDataChanged();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách sạn thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách sạn để xóa.");
        }
    }

    private void displayKhachSan(KhachSan khachSan) {
        txtKhachSanId.setText(String.valueOf(khachSan.getKhachSanId()));
        txtTen.setText(khachSan.getTen());
        txtSoDiaChi.setText(khachSan.getSoDiaChi());

        // Set selected duong in combo box
        for (int i = 0; i < cboDuong.getItemCount(); i++) {
            Duong duong = cboDuong.getItemAt(i);
            if (duong.getDuongId() == khachSan.getDuongId()) {
                cboDuong.setSelectedIndex(i);
                break;
            }
        }

        txtSoPhong.setText(String.valueOf(khachSan.getSoPhong()));
        txtSoPhongConTrong.setText(String.valueOf(khachSan.getSoPhongConTrong()));
        txtCheckIn.setText(String.valueOf(khachSan.getCheckIn()));
        txtCheckOut.setText(String.valueOf(khachSan.getCheckOut()));
        txtQuanLy.setText(khachSan.getQuanLy());
    }

    private void clearFields() {
        txtKhachSanId.setText("");
        txtTen.setText("");
        txtSoDiaChi.setText("");
        cboDuong.setSelectedIndex(0); // Assuming index 0 is a default selection
        txtSoPhong.setText("");
        txtSoPhongConTrong.setText("");
        txtCheckIn.setText("");
        txtCheckOut.setText("");
        txtQuanLy.setText("");
    }
}