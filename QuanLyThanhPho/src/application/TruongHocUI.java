package ui;

import dao.TruongHocDAO;
import entity.TruongHoc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TruongHocUI extends JFrame {

    private TruongHocDAO truongHocDAO;

    private JTextField tfId;
    private JTextField tfTen;
    private JTextField tfSoDiaChi;
    private JTextField tfDuongId;
    private JTextField tfCapBac;
    private JTextField tfSoHocSinh;
    private JTextField tfSoGiangVien;
    private JTextField tfHieuTruong;
    private JTextField tfCongTrinhId;

    private JTable table;
    private DefaultTableModel model;

    public TruongHocUI() {
        truongHocDAO = new TruongHocDAO();

        setTitle("Quản lý trường học");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị cửa sổ giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // Hiển thị JFrame khi đã hoàn thành cấu hình

        setPreferredSize(new Dimension(800, 600));

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelInput = new JPanel();
        contentPane.add(panelInput, BorderLayout.NORTH);
        panelInput.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel lblId = new JLabel("ID:");
        panelInput.add(lblId);

        tfId = new JTextField();
        panelInput.add(tfId);
        tfId.setColumns(10);

        JLabel lblTen = new JLabel("Tên trường:");
        panelInput.add(lblTen);

        tfTen = new JTextField();
        panelInput.add(tfTen);
        tfTen.setColumns(10);

        JLabel lblSoDiaChi = new JLabel("Số địa chỉ:");
        panelInput.add(lblSoDiaChi);

        tfSoDiaChi = new JTextField();
        panelInput.add(tfSoDiaChi);
        tfSoDiaChi.setColumns(10);

        JLabel lblDuongId = new JLabel("ID Đường:");
        panelInput.add(lblDuongId);

        tfDuongId = new JTextField();
        panelInput.add(tfDuongId);
        tfDuongId.setColumns(10);

        JLabel lblCapBac = new JLabel("Cấp bậc:");
        panelInput.add(lblCapBac);

        tfCapBac = new JTextField();
        panelInput.add(tfCapBac);
        tfCapBac.setColumns(10);

        JLabel lblSoHocSinh = new JLabel("Số học sinh:");
        panelInput.add(lblSoHocSinh);

        tfSoHocSinh = new JTextField();
        panelInput.add(tfSoHocSinh);
        tfSoHocSinh.setColumns(10);

        JLabel lblSoGiangVien = new JLabel("Số giáo viên:");
        panelInput.add(lblSoGiangVien);

        tfSoGiangVien = new JTextField();
        panelInput.add(tfSoGiangVien);
        tfSoGiangVien.setColumns(10);

        JLabel lblHieuTruong = new JLabel("Hiệu trưởng:");
        panelInput.add(lblHieuTruong);

        tfHieuTruong = new JTextField();
        panelInput.add(tfHieuTruong);
        tfHieuTruong.setColumns(10);

        JLabel lblCongTrinhId = new JLabel("ID Công trình:");
        panelInput.add(lblCongTrinhId);

        tfCongTrinhId = new JTextField();
        panelInput.add(tfCongTrinhId);
        tfCongTrinhId.setColumns(10);

        JPanel panelButtons = new JPanel();
        contentPane.add(panelButtons, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Thêm");
        panelButtons.add(btnAdd);

        JButton btnUpdate = new JButton("Sửa");
        panelButtons.add(btnUpdate);

        JButton btnDelete = new JButton("Xoá");
        panelButtons.add(btnDelete);

        JButton btnFindById = new JButton("Tìm theo ID");
        panelButtons.add(btnFindById);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Tên trường", "Số địa chỉ", "ID Đường", "Cấp bậc",
                "Số học sinh", "Số giáo viên", "Hiệu trưởng", "ID Công trình"});
        table.setModel(model);

        loadAllTruongHoc();

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTruongHoc();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTruongHoc();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTruongHoc();
            }
        });

        btnFindById.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findTruongHocById();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void loadAllTruongHoc() {
        model.setRowCount(0); // Clear table
        List<TruongHoc> truongHocList = truongHocDAO.findAllTruongHoc();

        for (TruongHoc truongHoc : truongHocList) {
            model.addRow(new Object[]{
                    truongHoc.getTruongHocId(),
                    truongHoc.getTen(),
                    truongHoc.getSoDiaChi(),
                    truongHoc.getDuongId(),
                    truongHoc.getCapBac(),
                    truongHoc.getSoHocSinh(),
                    truongHoc.getSoGiangVien(),
                    truongHoc.getHieuTruong(),
                    truongHoc.getCongTrinhId()
            });
        }
    }

    private void addTruongHoc() {
        TruongHoc truongHoc = createTruongHocFromInput();
        boolean added = truongHocDAO.addTruongHoc(truongHoc);
        if (added) {
            JOptionPane.showMessageDialog(this, "Thêm trường học thành công.");
            loadAllTruongHoc();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm trường học thất bại.");
        }
    }

    private void updateTruongHoc() {
        TruongHoc truongHoc = createTruongHocFromInput();
        boolean updated = truongHocDAO.updateTruongHoc(truongHoc);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Sửa thông tin trường học thành công.");
            loadAllTruongHoc();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thông tin trường học thất bại.");
        }
    }

    private void deleteTruongHoc() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một trường học để xoá.");
            return;
        }

        int truongHocId = (int) model.getValueAt(selectedRow, 0);
        boolean deleted = truongHocDAO.deleteTruongHoc(truongHocId);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Xoá trường học thành công.");
            loadAllTruongHoc();
        } else {
            JOptionPane.showMessageDialog(this, "Xoá trường học thất bại.");
        }
    }

    private void findTruongHocById() {
        String idText = tfId.getText();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID trường học để tìm kiếm.");
            return;
        }

        try {
            int truongHocId = Integer.parseInt(idText);
            TruongHoc foundTruongHoc = truongHocDAO.findTruongHocById(truongHocId);
            if (foundTruongHoc != null) {
                displayTruongHocInfo(foundTruongHoc);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy trường học có ID " + truongHocId);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID trường học không hợp lệ.");
        }
    }

    private TruongHoc createTruongHocFromInput() {
        TruongHoc truongHoc = new TruongHoc();

        try {
            truongHoc.setTruongHocId(Integer.parseInt(tfId.getText()));
            truongHoc.setTen(tfTen.getText());
            truongHoc.setSoDiaChi(tfSoDiaChi.getText());
            truongHoc.setDuongId(Integer.parseInt(tfDuongId.getText()));
            truongHoc.setCapBac(tfCapBac.getText());
            truongHoc.setSoHocSinh(Integer.parseInt(tfSoHocSinh.getText()));
            truongHoc.setSoGiangVien(Integer.parseInt(tfSoGiangVien.getText()));
            truongHoc.setHieuTruong(tfHieuTruong.getText());
            truongHoc.setCongTrinhId(Integer.parseInt(tfCongTrinhId.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ. Vui lòng kiểm tra lại.");
        }

        return truongHoc;
    }

    private void displayTruongHocInfo(TruongHoc truongHoc) {
        tfId.setText(String.valueOf(truongHoc.getTruongHocId()));
        tfTen.setText(truongHoc.getTen());
        tfSoDiaChi.setText(truongHoc.getSoDiaChi());
        tfDuongId.setText(String.valueOf(truongHoc.getDuongId()));
        tfCapBac.setText(truongHoc.getCapBac());
        tfSoHocSinh.setText(String.valueOf(truongHoc.getSoHocSinh()));
        tfSoGiangVien.setText(String.valueOf(truongHoc.getSoGiangVien()));
        tfHieuTruong.setText(truongHoc.getHieuTruong());
        tfCongTrinhId.setText(String.valueOf(truongHoc.getCongTrinhId()));
    }

    public static void main(String[] args) {
        // Chạy ứng dụng trên Event Dispatch Thread (EDT) để đảm bảo thread an toàn
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    TruongHocUI frame = new TruongHocUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}