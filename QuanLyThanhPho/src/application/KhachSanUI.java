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

public class KhachSanUI extends JFrame {

    private JTextField txtKhachSanId;
    private JTextField txtTen;
    private JTextField txtSoDiaChi;
    private JTextField txtDuongId;
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
        super("Quản lý Khách Sạn");
        initializeComponents();
        khachSanDAO = new KhachSanDAO();
        duongDAO = new DuongDAO();
        khachSanList = khachSanDAO.findAllKhachSan();
        duongList = duongDAO.getAllDuong();
        tableModel = new KhachSanTableModel(khachSanList);
        tableKhachSan.setModel(tableModel);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridBagLayout());
        JPanel panelCenter = new JPanel(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblKhachSanId = new JLabel("ID Khách Sạn:");
        txtKhachSanId = new JTextField(10);
        JLabel lblTen = new JLabel("Tên Khách Sạn:");
        txtTen = new JTextField(20);
        JLabel lblSoDiaChi = new JLabel("Địa chỉ:");
        txtSoDiaChi = new JTextField(20);
        JLabel lblDuongId = new JLabel("ID Đường:");
        txtDuongId = new JTextField(10);
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelTop.add(lblKhachSanId, gbc);
        gbc.gridx = 1;
        panelTop.add(txtKhachSanId, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelTop.add(lblTen, gbc);
        gbc.gridx = 1;
        panelTop.add(txtTen, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelTop.add(lblSoDiaChi, gbc);
        gbc.gridx = 1;
        panelTop.add(txtSoDiaChi, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelTop.add(lblDuongId, gbc);
        gbc.gridx = 1;
        panelTop.add(txtDuongId, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelTop.add(lblSoPhong, gbc);
        gbc.gridx = 1;
        panelTop.add(txtSoPhong, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelTop.add(lblSoPhongConTrong, gbc);
        gbc.gridx = 1;
        panelTop.add(txtSoPhongConTrong, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelTop.add(lblCheckIn, gbc);
        gbc.gridx = 1;
        panelTop.add(txtCheckIn, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelTop.add(lblCheckOut, gbc);
        gbc.gridx = 1;
        panelTop.add(txtCheckOut, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panelTop.add(lblQuanLy, gbc);
        gbc.gridx = 1;
        panelTop.add(txtQuanLy, gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        panelTop.add(btnThem, gbc);
        gbc.gridx = 1;
        panelTop.add(btnSua, gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        panelTop.add(btnXoa, gbc);

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

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addKhachSan() {
        try {
            int khachSanId = Integer.parseInt(txtKhachSanId.getText().trim());
            String ten = txtTen.getText().trim();
            String soDiaChi = txtSoDiaChi.getText().trim();
            int duongId = Integer.parseInt(txtDuongId.getText().trim());
            int soPhong = Integer.parseInt(txtSoPhong.getText().trim());
            int soPhongConTrong = Integer.parseInt(txtSoPhongConTrong.getText().trim());
            int checkIn = Integer.parseInt(txtCheckIn.getText().trim());
            int checkOut = Integer.parseInt(txtCheckOut.getText().trim());
            String quanLy = txtQuanLy.getText().trim();

            boolean duongExists = false;
            for (Duong duong : duongList) {
                if (duong.getDuongId() == duongId) {
                    duongExists = true;
                    break;
                }
            }

            if (!duongExists) {
                JOptionPane.showMessageDialog(this, "DuongId không tồn tại.");
                return;
            }

            KhachSan newKhachSan = new KhachSan();
            newKhachSan.setKhachSanId(khachSanId);
            newKhachSan.setTen(ten);
            newKhachSan.setSoDiaChi(soDiaChi);
            newKhachSan.setDuongId(duongId);
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ.");
        }
    }

    private void updateKhachSan() {
        int selectedRow = tableKhachSan.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                KhachSan selectedKhachSan = khachSanList.get(selectedRow);

                int khachSanId = Integer.parseInt(txtKhachSanId.getText().trim());
                String ten = txtTen.getText().trim();
                String soDiaChi = txtSoDiaChi.getText().trim();
                int duongId = Integer.parseInt(txtDuongId.getText().trim());
                int soPhong = Integer.parseInt(txtSoPhong.getText().trim());
                int soPhongConTrong = Integer.parseInt(txtSoPhongConTrong.getText().trim());
                int checkIn = Integer.parseInt(txtCheckIn.getText().trim());
                int checkOut = Integer.parseInt(txtCheckOut.getText().trim());
                String quanLy = txtQuanLy.getText().trim();

                boolean duongExists = false;
                for (Duong duong : duongList) {
                    if (duong.getDuongId() == duongId) {
                        duongExists = true;
                        break;
                    }
                }

                if (!duongExists) {
                    JOptionPane.showMessageDialog(this, "DuongId không tồn tại.");
                    return;
                }

                selectedKhachSan.setKhachSanId(khachSanId);
                selectedKhachSan.setTen(ten);
                selectedKhachSan.setSoDiaChi(soDiaChi);
                selectedKhachSan.setDuongId(duongId);
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
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ.");
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
        txtDuongId.setText(String.valueOf(khachSan.getDuongId()));
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
        txtDuongId.setText("");
        txtSoPhong.setText("");
        txtSoPhongConTrong.setText("");
        txtCheckIn.setText("");
        txtCheckOut.setText("");
        txtQuanLy.setText("");
    }

    class KhachSanTableModel extends AbstractTableModel {
        private final List<KhachSan> khachSanList;

        public KhachSanTableModel(List<KhachSan> khachSanList) {
            this.khachSanList = khachSanList;
        }

        @Override
        public int getRowCount() {
            return khachSanList.size();
        }

        @Override
        public int getColumnCount() {
            return 9;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            KhachSan khachSan = khachSanList.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return khachSan.getKhachSanId();
                case 1:
                    return khachSan.getTen();
                case 2:
                    return khachSan.getSoDiaChi();
                case 3:
                    return khachSan.getDuongId();
                case 4:
                    return khachSan.getSoPhong();
                case 5:
                    return khachSan.getSoPhongConTrong();
                case 6:
                    return khachSan.getCheckIn();
                case 7:
                    return khachSan.getCheckOut();
                case 8:
                    return khachSan.getQuanLy();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "ID Khách Sạn";
                case 1:
                    return "Tên";
                case 2:
                    return "Địa chỉ";
                case 3:
                    return "ID Đường";
                case 4:
                    return "Số phòng";
                case 5:
                    return "Số phòng còn trống";
                case 6:
                    return "Check In";
                case 7:
                    return "Check Out";
                case 8:
                    return "Quản lý";
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KhachSanUI(); // Khởi tạo UI khi chạy chương trình
        });
    }
}