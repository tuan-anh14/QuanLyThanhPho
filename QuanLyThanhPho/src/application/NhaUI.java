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

import dao.NhaDAO;
import entity.Nha;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NhaUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtNhaId;
    private JTextField txtSoNha;
    private JTextField txtLoaiNha;
    private JTextField txtCongTrinhId;
    private JTable table;

    private NhaDAO nhaDAO;

    private DefaultTableModel model;

    /**
     * Create the frame.
     */
    public NhaUI() {
        setTitle("Quản lý thông tin nhà");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNhaId = new JLabel("ID Nhà:");
        lblNhaId.setBounds(10, 20, 80, 14);
        contentPane.add(lblNhaId);

        txtNhaId = new JTextField();
        txtNhaId.setBounds(100, 20, 120, 20);
        contentPane.add(txtNhaId);
        txtNhaId.setColumns(10);

        JLabel lblSoNha = new JLabel("Số nhà:");
        lblSoNha.setBounds(10, 50, 80, 14);
        contentPane.add(lblSoNha);

        txtSoNha = new JTextField();
        txtSoNha.setBounds(100, 50, 120, 20);
        contentPane.add(txtSoNha);
        txtSoNha.setColumns(10);

        JLabel lblLoaiNha = new JLabel("Loại nhà:");
        lblLoaiNha.setBounds(10, 80, 80, 14);
        contentPane.add(lblLoaiNha);

        txtLoaiNha = new JTextField();
        txtLoaiNha.setBounds(100, 80, 120, 20);
        contentPane.add(txtLoaiNha);
        txtLoaiNha.setColumns(10);

        JLabel lblCongTrinhId = new JLabel("ID Công trình:");
        lblCongTrinhId.setBounds(10, 110, 80, 14);
        contentPane.add(lblCongTrinhId);

        txtCongTrinhId = new JTextField();
        txtCongTrinhId.setBounds(100, 110, 120, 20);
        contentPane.add(txtCongTrinhId);
        txtCongTrinhId.setColumns(10);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themNha();
            }
        });
        btnThem.setBounds(10, 140, 89, 23);
        contentPane.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaNha();
            }
        });
        btnSua.setBounds(109, 140, 89, 23);
        contentPane.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaNha();
            }
        });
        btnXoa.setBounds(208, 140, 89, 23);
        contentPane.add(btnXoa);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 174, 614, 176);
        contentPane.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID Nhà", "Số nhà", "Loại nhà", "ID Công trình" }
        );
        table.setModel(model);
        scrollPane.setViewportView(table);

        // Initialize DAO
        nhaDAO = new NhaDAO();

        // Load table data
        loadDanhSachNha();

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    // Method to load danh sach nha from database
    private void loadDanhSachNha() {
        // Clear table
        model.setRowCount(0);

        // Get danh sach nha from DAO
        List<Nha> danhSachNha = nhaDAO.findAllNha();

        // Add danh sach nha to table
        for (Nha nha : danhSachNha) {
            Object[] row = {
                nha.getNhaId(),
                nha.getSonha(),
                nha.getLoaiNha(),
                nha.getCongTrinhId()
            };
            model.addRow(row);
        }
    }

    // Method to add new nha
    private void themNha() {
        // Create new Nha object
        Nha nha = new Nha();

        // Set values from input fields
        nha.setNhaId(Integer.parseInt(txtNhaId.getText()));
        nha.setSonha(Integer.parseInt(txtSoNha.getText()));
        nha.setLoaiNha(txtLoaiNha.getText());
        nha.setCongTrinhId(Integer.parseInt(txtCongTrinhId.getText()));

        // Add Nha object to database via DAO
        nhaDAO.addNha(nha);

        // Reload table
        loadDanhSachNha();

        // Clear input fields
        clearFields();
    }

    // Method to update existing nha
    private void suaNha() {
        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return; // No row selected, do nothing
        }

        // Get Nha object from selected row
        int nhaId = (int) table.getValueAt(selectedRow, 0);
        Nha nha = nhaDAO.findNhaById(nhaId);

        // Update Nha object with new values
        nha.setSonha(Integer.parseInt(txtSoNha.getText()));
        nha.setLoaiNha(txtLoaiNha.getText());
        nha.setCongTrinhId(Integer.parseInt(txtCongTrinhId.getText()));

        // Update Nha object in database via DAO
        nhaDAO.updateNha(nha);

        // Reload table
        loadDanhSachNha();

        // Clear input fields
        clearFields();
    }

    // Method to delete existing nha
    private void xoaNha() {
        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return; // No row selected, do nothing
        }

        // Get Nha object from selected row
        int nhaId = (int) table.getValueAt(selectedRow, 0);

        // Delete Nha object from database via DAO
        nhaDAO.deleteNha(nhaId);

        // Reload table
        loadDanhSachNha();

        // Clear input fields
        clearFields();
    }

    // Method to clear input fields
    private void clearFields() {
        txtNhaId.setText("");
        txtSoNha.setText("");
        txtLoaiNha.setText("");
        txtCongTrinhId.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NhaUI frame = new NhaUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}