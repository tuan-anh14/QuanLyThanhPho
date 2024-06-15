package ui;

import dao.TTTMDAO;
import entity.TTTM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TTTMUI extends JFrame {
    private TTTMDAO tttmDAO;
    private JTextField tfId, tfTen, tfSoDiaChi, tfDuongId, tfSoLuongNhanVien, tfSoLuongKhach, tfQuanLy, tfCongTrinhId;
    private JButton btnThem, btnTim, btnHienThi, btnSua, btnXoa;
    private JTable tableTTTM;
    private DefaultTableModel model;

    public TTTMUI() {
        super("Quản lý TTTM");
        tttmDAO = new TTTMDAO();

        // Khởi tạo UI components
        JPanel panelInput = createInputPanel();
        JPanel panelButtons = createButtonPanel();
        JScrollPane scrollPane = createTablePanel();

        // Đặt layout cho JFrame
        setLayout(new BorderLayout());
        add(panelInput, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Thiết lập các thuộc tính của JFrame
        setSize(800, 600);
        setLocationRelativeTo(null); // Hiển thị cửa sổ giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("ID:"));
        tfId = new JTextField();
        panel.add(tfId);

        panel.add(new JLabel("Tên:"));
        tfTen = new JTextField();
        panel.add(tfTen);

        panel.add(new JLabel("Số địa chỉ:"));
        tfSoDiaChi = new JTextField();
        panel.add(tfSoDiaChi);

        panel.add(new JLabel("ID Đường:"));
        tfDuongId = new JTextField();
        panel.add(tfDuongId);

        panel.add(new JLabel("Số lượng nhân viên:"));
        tfSoLuongNhanVien = new JTextField();
        panel.add(tfSoLuongNhanVien);

        panel.add(new JLabel("Số lượng khách một ngày:"));
        tfSoLuongKhach = new JTextField();
        panel.add(tfSoLuongKhach);

        panel.add(new JLabel("Quản lý:"));
        tfQuanLy = new JTextField();
        panel.add(tfQuanLy);

        panel.add(new JLabel("ID Công trình:"));
        tfCongTrinhId = new JTextField();
        panel.add(tfCongTrinhId);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        btnThem = new JButton("Thêm");
        btnThem.addActionListener(e -> themTTTM());

        btnTim = new JButton("Tìm");
        btnTim.addActionListener(e -> timTTTM());

        btnHienThi = new JButton("Hiển thị danh sách");
        btnHienThi.addActionListener(e -> hienThiDanhSach());

        btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> suaTTTM());

        btnXoa = new JButton("Xoá");
        btnXoa.addActionListener(e -> xoaTTTM());

        panel.add(btnThem);
        panel.add(btnTim);
        panel.add(btnHienThi);
        panel.add(btnSua);
        panel.add(btnXoa);

        return panel;
    }

    private JScrollPane createTablePanel() {
        tableTTTM = new JTable();
        model = new DefaultTableModel();
        tableTTTM.setModel(model);

        model.addColumn("ID");
        model.addColumn("Tên");
        model.addColumn("Số địa chỉ");
        model.addColumn("ID Đường");
        model.addColumn("Số lượng nhân viên");
        model.addColumn("Số lượng khách một ngày");
        model.addColumn("Quản lý");
        model.addColumn("ID Công trình");

        JScrollPane scrollPane = new JScrollPane(tableTTTM);
        return scrollPane;
    }

    private void themTTTM() {
        TTTM tttm = createTTTMFromInput();
        boolean added = tttmDAO.addTTTM(tttm);
        if (added) {
            JOptionPane.showMessageDialog(this, "Thêm TTTM thành công");
            clearInputFields();
            hienThiDanhSach();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm TTTM thất bại");
        }
    }

    private void timTTTM() {
        int id = Integer.parseInt(tfId.getText().trim());
        TTTM tttm = tttmDAO.findTTTMById(id);
        if (tttm != null) {
            displayTTTMInfo(tttm);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy TTTM có ID " + id);
        }
    }

    private void hienThiDanhSach() {
        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        List<TTTM> tttmList = tttmDAO.findAllTTTM();
        for (TTTM tttm : tttmList) {
            Object[] row = {
                    tttm.getTTTMId(),
                    tttm.getTen(),
                    tttm.getSoDiaChi(),
                    tttm.getDuongId(),
                    tttm.getSoLuongNhanVien(),
                    tttm.getSoLuongKhachMotNgay(),
                    tttm.getQuanLy(),
                    tttm.getCongTrinhId()
            };
            model.addRow(row);
        }
    }

    private void suaTTTM() {
        TTTM tttm = createTTTMFromInput();
        boolean updated = tttmDAO.updateTTTM(tttm);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Sửa thông tin TTTM thành công");
            clearInputFields();
            hienThiDanhSach();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thông tin TTTM thất bại");
        }
    }

    private void xoaTTTM() {
        int id = Integer.parseInt(tfId.getText().trim());
        boolean deleted = tttmDAO.deleteTTTM(id);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Xoá TTTM thành công");
            clearInputFields();
            hienThiDanhSach();
        } else {
            JOptionPane.showMessageDialog(this, "Xoá TTTM thất bại");
        }
    }

    private TTTM createTTTMFromInput() {
        TTTM tttm = new TTTM();

        tttm.setTTTMId(Integer.parseInt(tfId.getText().trim()));
        tttm.setTen(tfTen.getText().trim());
        tttm.setSoDiaChi(tfSoDiaChi.getText().trim());
        tttm.setDuongId(Integer.parseInt(tfDuongId.getText().trim()));
        tttm.setSoLuongNhanVien(Integer.parseInt(tfSoLuongNhanVien.getText().trim()));
        tttm.setSoLuongKhachMotNgay(Integer.parseInt(tfSoLuongKhach.getText().trim()));
        tttm.setQuanLy(tfQuanLy.getText().trim());
        tttm.setCongTrinhId(Integer.parseInt(tfCongTrinhId.getText().trim()));

        return tttm;
    }

    private void displayTTTMInfo(TTTM tttm) {
        tfId.setText(String.valueOf(tttm.getTTTMId()));
        tfTen.setText(tttm.getTen());
        tfSoDiaChi.setText(tttm.getSoDiaChi());
        tfDuongId.setText(String.valueOf(tttm.getDuongId()));
        tfSoLuongNhanVien.setText(String.valueOf(tttm.getSoLuongNhanVien()));
        tfSoLuongKhach.setText(String.valueOf(tttm.getSoLuongKhachMotNgay()));
        tfQuanLy.setText(tttm.getQuanLy());
        tfCongTrinhId.setText(String.valueOf(tttm.getCongTrinhId()));
    }

    private void clearInputFields() {
        tfId.setText("");
        tfTen.setText("");
        tfSoDiaChi.setText("");
        tfDuongId.setText("");
        tfSoLuongNhanVien.setText("");
        tfSoLuongKhach.setText("");
        tfQuanLy.setText("");
        tfCongTrinhId.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                TTTMUI frame = new TTTMUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}