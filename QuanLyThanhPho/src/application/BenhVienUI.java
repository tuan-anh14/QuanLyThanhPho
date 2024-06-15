package ui;

import entity.BenhVien;
import dao.BenhVienDAO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BenhVienUI extends JFrame {

    private JTextField txtBenhVienId;
    private JTextField txtBenhVienTen;
    private JTextField txtSoDiaChi;
    private JTextField txtDuongId;
    private JTextField txtNamThanhLap;
    private JTextField txtSoBacSi;
    private JTextField txtSoBenhNhan;
    private JTextField txtGiamDoc;

    private JButton btnLuu;
    private JButton btnXoa;
    private JButton btnCapNhat;
    private JButton btnTimKiem;

    private JTable tableBenhVien;
    private JScrollPane scrollPane;

    private BenhVienDAO benhVienDAO;

    public BenhVienUI() {
        benhVienDAO = new BenhVienDAO();

        setTitle("Quản lý Bệnh viện");
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
        txtBenhVienId = new JTextField(10);
        txtBenhVienTen = new JTextField(20);
        txtSoDiaChi = new JTextField(30);
        txtDuongId = new JTextField(10);
        txtNamThanhLap = new JTextField(10);
        txtSoBacSi = new JTextField(10);
        txtSoBenhNhan = new JTextField(10);
        txtGiamDoc = new JTextField(20);

        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBenhVien();
            }
        });

        btnXoa = new JButton("Xoá");
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBenhVien();
            }
        });

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBenhVien();
            }
        });

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findBenhVienById();
            }
        });

        tableBenhVien = new JTable();
        scrollPane = new JScrollPane(tableBenhVien);
    }

    private void setupLayout() {
        JPanel panelInput = new JPanel(new GridLayout(8, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("ID:"));
        panelInput.add(txtBenhVienId);
        panelInput.add(new JLabel("Tên:"));
        panelInput.add(txtBenhVienTen);
        panelInput.add(new JLabel("Số địa chỉ:"));
        panelInput.add(txtSoDiaChi);
        panelInput.add(new JLabel("ID Đường:"));
        panelInput.add(txtDuongId);
        panelInput.add(new JLabel("Năm thành lập:"));
        panelInput.add(txtNamThanhLap);
        panelInput.add(new JLabel("Số bác sĩ:"));
        panelInput.add(txtSoBacSi);
        panelInput.add(new JLabel("Số bệnh nhân:"));
        panelInput.add(txtSoBenhNhan);
        panelInput.add(new JLabel("Giám đốc:"));
        panelInput.add(txtGiamDoc);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(btnLuu);
        panelButtons.add(btnXoa);
        panelButtons.add(btnCapNhat);
        panelButtons.add(btnTimKiem);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelInput, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        List<BenhVien> benhVienList = benhVienDAO.findAllBenhVien();
        BenhVienTableModel model = new BenhVienTableModel(benhVienList);
        tableBenhVien.setModel(model);
    }

    private void saveBenhVien() {
        int id = Integer.parseInt(txtBenhVienId.getText().trim());
        String ten = txtBenhVienTen.getText().trim();
        String soDiaChi = txtSoDiaChi.getText().trim();
        int duongId = Integer.parseInt(txtDuongId.getText().trim());
        int namThanhLap = Integer.parseInt(txtNamThanhLap.getText().trim());
        int soBacSi = Integer.parseInt(txtSoBacSi.getText().trim());
        int soBenhNhan = Integer.parseInt(txtSoBenhNhan.getText().trim());
        String giamDoc = txtGiamDoc.getText().trim();

        BenhVien benhVien = new BenhVien();
        benhVien.setBenhVienId(id);
        benhVien.setBenhVienTen(ten);
        benhVien.setSoDiaChi(soDiaChi);
        benhVien.setDuongId(duongId);
        benhVien.setNamThanhLap(namThanhLap);
        benhVien.setSoBacSi(soBacSi);
        benhVien.setSoBenhNhan(soBenhNhan);
        benhVien.setGiamDoc(giamDoc);

        boolean result = benhVienDAO.addBenhVien(benhVien);
        if (result) {
            JOptionPane.showMessageDialog(this, "Thêm bệnh viện thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm bệnh viện thất bại");
        }
    }

    private void deleteBenhVien() {
        int id = Integer.parseInt(txtBenhVienId.getText().trim());
        boolean result = benhVienDAO.deleteBenhVien(id);
        if (result) {
            JOptionPane.showMessageDialog(this, "Xoá bệnh viện thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Xoá bệnh viện thất bại");
        }
    }

    private void updateBenhVien() {
        int id = Integer.parseInt(txtBenhVienId.getText().trim());
        String ten = txtBenhVienTen.getText().trim();
        String soDiaChi = txtSoDiaChi.getText().trim();
        int duongId = Integer.parseInt(txtDuongId.getText().trim());
        int namThanhLap = Integer.parseInt(txtNamThanhLap.getText().trim());
        int soBacSi = Integer.parseInt(txtSoBacSi.getText().trim());
        int soBenhNhan = Integer.parseInt(txtSoBenhNhan.getText().trim());
        String giamDoc = txtGiamDoc.getText().trim();

        BenhVien benhVien = new BenhVien();
        benhVien.setBenhVienId(id);
        benhVien.setBenhVienTen(ten);
        benhVien.setSoDiaChi(soDiaChi);
        benhVien.setDuongId(duongId);
        benhVien.setNamThanhLap(namThanhLap);
        benhVien.setSoBacSi(soBacSi);
        benhVien.setSoBenhNhan(soBenhNhan);
        benhVien.setGiamDoc(giamDoc);

        boolean result = benhVienDAO.updateBenhVien(benhVien);
        if (result) {
            JOptionPane.showMessageDialog(this, "Cập nhật bệnh viện thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật bệnh viện thất bại");
        }
    }

    private void findBenhVienById() {
        int id = Integer.parseInt(txtBenhVienId.getText().trim());
        BenhVien benhVien = benhVienDAO.findBenhVienById(id);
        if (benhVien != null) {
            txtBenhVienTen.setText(benhVien.getBenhVienTen());
            txtSoDiaChi.setText(benhVien.getSoDiaChi());
            txtDuongId.setText(String.valueOf(benhVien.getDuongId()));
            txtNamThanhLap.setText(String.valueOf(benhVien.getNamThanhLap()));
            txtSoBacSi.setText(String.valueOf(benhVien.getSoBacSi()));
            txtSoBenhNhan.setText(String.valueOf(benhVien.getSoBenhNhan()));
            txtGiamDoc.setText(benhVien.getGiamDoc());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy bệnh viện");
            clearFields();
        }
    }

    private void clearFields() {
        txtBenhVienId.setText("");
        txtBenhVienTen.setText("");
        txtSoDiaChi.setText("");
        txtDuongId.setText("");
        txtNamThanhLap.setText("");
        txtSoBacSi.setText("");
        txtSoBenhNhan.setText("");
        txtGiamDoc.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BenhVienUI benhVienUI = new BenhVienUI();
                benhVienUI.setVisible(true);
            }
        });
    }
}

// Lớp BenhVienTableModel để hiển thị dữ liệu trong JTable
class BenhVienTableModel extends AbstractTableModel {
    private List<BenhVien> benhVienList;
    private final String[] columnNames = {"ID", "Tên", "Số địa chỉ", "ID Đường", "Năm thành lập", "Số bác sĩ", "Số bệnh nhân", "Giám đốc"};

    public BenhVienTableModel(List<BenhVien> benhVienList) {
        this.benhVienList = benhVienList;
    }

    public int getRowCount() {
        return benhVienList.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        BenhVien benhVien = benhVienList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return benhVien.getBenhVienId();
            case 1:
                return benhVien.getBenhVienTen();
            case 2:
                return benhVien.getSoDiaChi();
            case 3:
                return benhVien.getDuongId();
            case 4:
                return benhVien.getNamThanhLap();
            case 5:
                return benhVien.getSoBacSi();
            case 6:
                return benhVien.getSoBenhNhan();
            case 7:
                return benhVien.getGiamDoc();
            default:
                return null;
        }
    }
}