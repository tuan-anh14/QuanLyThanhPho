package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.NguoiDanDAO;
import entity.NguoiDan;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class NguoiDanUI extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtNguoiDanId;
    private JTextField txtHo;
    private JTextField txtTen;
    private JTextField txtNgaySinh;
    private JTextField txtGioiTinh;
    private JTextField txtCccd;
    private JTextField txtSoNha;
    private JTextField txtNgheNghiep;

    private NguoiDanDAO nguoiDanDAO;

    /**
     * Create the frame.
     */
    public NguoiDanUI() {
        setTitle("Quản lý người dân");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNguoiDanId = new JLabel("ID:");
        lblNguoiDanId.setBounds(30, 30, 80, 20);
        contentPane.add(lblNguoiDanId);

        txtNguoiDanId = new JTextField();
        txtNguoiDanId.setBounds(120, 30, 200, 20);
        txtNguoiDanId.setEditable(false);
        contentPane.add(txtNguoiDanId);
        txtNguoiDanId.setColumns(10);

        JLabel lblHo = new JLabel("Họ:");
        lblHo.setBounds(30, 60, 80, 20);
        contentPane.add(lblHo);

        txtHo = new JTextField();
        txtHo.setBounds(120, 60, 200, 20);
        contentPane.add(txtHo);
        txtHo.setColumns(10);

        JLabel lblTen = new JLabel("Tên:");
        lblTen.setBounds(30, 90, 80, 20);
        contentPane.add(lblTen);

        txtTen = new JTextField();
        txtTen.setBounds(120, 90, 200, 20);
        contentPane.add(txtTen);
        txtTen.setColumns(10);

        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setBounds(30, 120, 80, 20);
        contentPane.add(lblNgaySinh);

        txtNgaySinh = new JTextField();
        txtNgaySinh.setBounds(120, 120, 200, 20);
        contentPane.add(txtNgaySinh);
        txtNgaySinh.setColumns(10);

        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setBounds(30, 150, 80, 20);
        contentPane.add(lblGioiTinh);

        txtGioiTinh = new JTextField();
        txtGioiTinh.setBounds(120, 150, 200, 20);
        contentPane.add(txtGioiTinh);
        txtGioiTinh.setColumns(10);

        JLabel lblCccd = new JLabel("CCCD:");
        lblCccd.setBounds(30, 180, 80, 20);
        contentPane.add(lblCccd);

        txtCccd = new JTextField();
        txtCccd.setBounds(120, 180, 200, 20);
        contentPane.add(txtCccd);
        txtCccd.setColumns(10);

        JLabel lblSoNha = new JLabel("Số nhà:");
        lblSoNha.setBounds(30, 210, 80, 20);
        contentPane.add(lblSoNha);

        txtSoNha = new JTextField();
        txtSoNha.setBounds(120, 210, 200, 20);
        contentPane.add(txtSoNha);
        txtSoNha.setColumns(10);

        JLabel lblNgheNghiep = new JLabel("Nghề nghiệp:");
        lblNgheNghiep.setBounds(30, 240, 80, 20);
        contentPane.add(lblNgheNghiep);

        txtNgheNghiep = new JTextField();
        txtNgheNghiep.setBounds(120, 240, 200, 20);
        contentPane.add(txtNgheNghiep);
        txtNgheNghiep.setColumns(10);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themNguoiDan();
            }
        });
        btnThem.setBounds(350, 30, 100, 30);
        contentPane.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaNguoiDan();
            }
        });
        btnSua.setBounds(350, 70, 100, 30);
        contentPane.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaNguoiDan();
            }
        });
        btnXoa.setBounds(350, 110, 100, 30);
        contentPane.add(btnXoa);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 300, 700, 200);
        contentPane.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Họ", "Tên", "Ngày sinh", "Giới tính", "CCCD", "Số nhà", "Nghề nghiệp" });
        table.setModel(model);
        scrollPane.setViewportView(table);

        JLabel lblNewLabel = new JLabel("Danh sách người dân");
        lblNewLabel.setBounds(30, 270, 200, 20);
        contentPane.add(lblNewLabel);

        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDanhSachNguoiDan();
            }
        });
        btnLoad.setBounds(350, 150, 100, 30);
        contentPane.add(btnLoad);

        // Initialize DAO
        nguoiDanDAO = new NguoiDanDAO();

        // Load data initially
        loadDanhSachNguoiDan();
    }

    private void loadDanhSachNguoiDan() {
        // Clear table
        model.setRowCount(0);

        // Get list of NguoiDan from DAO
        List<NguoiDan> danhSachNguoiDan = nguoiDanDAO.findAllNguoiDan();

        // Add NguoiDan objects to table model
        for (NguoiDan nguoiDan : danhSachNguoiDan) {
            Object[] rowData = { nguoiDan.getNguoiDanId(), nguoiDan.getHo(), nguoiDan.getTen(), nguoiDan.getNgaySinh(),
                    nguoiDan.getGioiTinh(), nguoiDan.getCccd(), nguoiDan.getSoNha(), nguoiDan.getNgheNghiep() };
            model.addRow(rowData);
        }
    }

    private void themNguoiDan() {
        // Create a new NguoiDan object
        NguoiDan nguoiDan = new NguoiDan();
        nguoiDan.setHo(txtHo.getText());
        nguoiDan.setTen(txtTen.getText());
        nguoiDan.setNgaySinh(txtNgaySinh.getText());
        nguoiDan.setGioiTinh(txtGioiTinh.getText());
        nguoiDan.setCccd(txtCccd.getText());
        nguoiDan.setSoNha(txtSoNha.getText());
        nguoiDan.setNgheNghiep(txtNgheNghiep.getText());

        // Add NguoiDan object to database via DAO
        nguoiDanDAO.addNguoiDan(nguoiDan);

        // Reload table
        loadDanhSachNguoiDan();

        // Clear input fields
        clearFields();
    }

    private void suaNguoiDan() {
        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return; // No row selected, do nothing
        }

        // Get NguoiDan object from selected row
        int nguoiDanId = (int) table.getValueAt(selectedRow, 0);
        NguoiDan nguoiDan = nguoiDanDAO.findNguoiDanById(nguoiDanId);

        // Update NguoiDan object with new values
        nguoiDan.setHo(txtHo.getText());
        nguoiDan.setTen(txtTen.getText());
        nguoiDan.setNgaySinh(txtNgaySinh.getText());
        nguoiDan.setGioiTinh(txtGioiTinh.getText());
        nguoiDan.setCccd(txtCccd.getText());
        nguoiDan.setSoNha(txtSoNha.getText());
        nguoiDan.setNgheNghiep(txtNgheNghiep.getText());

        // Update NguoiDan object in database via DAO
        nguoiDanDAO.updateNguoiDan(nguoiDan);

        // Reload table
        loadDanhSachNguoiDan();

        // Clear input fields
        clearFields();
    }

    private void xoaNguoiDan() {
        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return; // No row selected, do nothing
        }

        // Get NguoiDan object from selected row
        int nguoiDanId = (int) table.getValueAt(selectedRow, 0);

        // Delete NguoiDan object from database via DAO
        nguoiDanDAO.deleteNguoiDan(nguoiDanId);

        // Reload table
        loadDanhSachNguoiDan();

        // Clear input fields
        clearFields();
    }

    private void clearFields() {
        txtNguoiDanId.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtNgaySinh.setText("");
        txtGioiTinh.setText("");
        txtCccd.setText("");
        txtSoNha.setText("");
        txtNgheNghiep.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NguoiDanUI frame = new NguoiDanUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}