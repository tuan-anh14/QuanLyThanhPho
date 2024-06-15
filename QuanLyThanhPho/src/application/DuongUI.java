package ui;

import dao.DuongDAO;
import entity.Duong;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DuongUI extends JPanel {

    private JTextField txtDuongId;
    private JTextField txtTenDuong;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JTable tableDuong;

    private DuongTableModel tableModel;
    private DuongDAO duongDAO;

    private List<Duong> duongList;

    public DuongUI() {
        initializeComponents();
        duongDAO = new DuongDAO();
        duongList = duongDAO.getAllDuong();
        tableModel = new DuongTableModel(duongList);
        tableDuong.setModel(tableModel);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelCenter = new JPanel(new BorderLayout());

        JLabel lblDuongId = new JLabel("ID Đường:");
        txtDuongId = new JTextField(10);
        JLabel lblTenDuong = new JLabel("Tên Đường:");
        txtTenDuong = new JTextField(20);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        panelTop.add(lblDuongId);
        panelTop.add(txtDuongId);
        panelTop.add(lblTenDuong);
        panelTop.add(txtTenDuong);
        panelTop.add(btnThem);
        panelTop.add(btnSua);
        panelTop.add(btnXoa);

        tableDuong = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableDuong);

        panelCenter.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDuong();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDuong();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDuong();
            }
        });

        tableDuong.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableDuong.getSelectedRow();
            if (selectedRow >= 0) {
                Duong duong = duongList.get(selectedRow);
                displayDuong(duong);
            }
        });
    }

    private void addDuong() {
        int duongId = Integer.parseInt(txtDuongId.getText().trim());
        String tenDuong = txtTenDuong.getText().trim();

        Duong newDuong = new Duong();
        newDuong.setDuongId(duongId);
        newDuong.setTenDuong(tenDuong);

        boolean added = duongDAO.addDuong(newDuong);
        if (added) {
            duongList.add(newDuong);
            tableModel.fireTableDataChanged();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm đường thất bại.");
        }
    }

    private void updateDuong() {
        int selectedRow = tableDuong.getSelectedRow();
        if (selectedRow >= 0) {
            Duong selectedDuong = duongList.get(selectedRow);

            int duongId = Integer.parseInt(txtDuongId.getText().trim());
            String tenDuong = txtTenDuong.getText().trim();

            selectedDuong.setDuongId(duongId);
            selectedDuong.setTenDuong(tenDuong);

            boolean updated = duongDAO.updateDuong(selectedDuong);
            if (updated) {
                tableModel.fireTableDataChanged();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa đường thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đường để sửa.");
        }
    }

    private void deleteDuong() {
        int selectedRow = tableDuong.getSelectedRow();
        if (selectedRow >= 0) {
            Duong selectedDuong = duongList.get(selectedRow);

            int duongId = selectedDuong.getDuongId();

            boolean deleted = duongDAO.deleteDuong(duongId);
            if (deleted) {
                duongList.remove(selectedDuong);
                tableModel.fireTableDataChanged();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa đường thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đường để xóa.");
        }
    }

    private void displayDuong(Duong duong) {
        txtDuongId.setText(String.valueOf(duong.getDuongId()));
        txtTenDuong.setText(duong.getTenDuong());
    }

    private void clearFields() {
        txtDuongId.setText("");
        txtTenDuong.setText("");
    }

    private class DuongTableModel extends AbstractTableModel {
        private List<Duong> duongList;
        private String[] columnNames = {"ID Đường", "Tên Đường"};

        public DuongTableModel(List<Duong> duongList) {
            this.duongList = duongList;
        }

        @Override
        public int getRowCount() {
            return duongList.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Duong duong = duongList.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return duong.getDuongId();
                case 1:
                    return duong.getTenDuong();
                default:
                    return null;
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }
            return Object.class;
        }
    }
}