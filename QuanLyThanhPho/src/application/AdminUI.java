package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI extends JFrame implements ActionListener {
    private JButton btnBenhVien, btnCongVien, btnDuong, btnKhachSan, btnNguoiDan, btnNha, btnQuan, btnTruongHoc, btnTTTM;

    public AdminUI() {
        super("Admin Dashboard");

        // Panel chính chứa hình ảnh và nút điều hướng
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.setBackground(Color.BLACK); // Đổi màu nền thành đen

        // Hình ảnh và tiêu đề ở giữa
        ImageIcon cityImageIcon = new ImageIcon("F:\\JAVA\\QuanLyThanhPho-main\\QuanLyThanhPho-main\\QuanLyThanhPho\\images\\city.jpg"); // Đường dẫn tới hình ảnh "Quản lý thành phố ảo"
        JLabel cityImageLabel = new JLabel(cityImageIcon);
        JLabel titleLabel = new JLabel("Quản lý thành phố ảo", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Đổi màu tiêu đề thành trắng

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(cityImageLabel, BorderLayout.CENTER);
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.BLACK); // Đổi màu nền của panel chứa hình ảnh thành đen

        // Panel chứa các nút điều hướng
        JPanel panelButtons = new JPanel(new GridLayout(3, 3, 10, 10));
        panelButtons.setBackground(Color.BLACK); // Đổi màu nền của panel chứa nút thành đen

        // Tạo các nút điều hướng
        btnBenhVien = createNavigationButton("Bệnh viện");
        btnCongVien = createNavigationButton("Công viên");
        btnDuong = createNavigationButton("Đường");
        btnKhachSan = createNavigationButton("Khách sạn");
        btnNguoiDan = createNavigationButton("Người dân");
        btnNha = createNavigationButton("Nhà");
        btnQuan = createNavigationButton("Quận");
        btnTruongHoc = createNavigationButton("Trường học");
        btnTTTM = createNavigationButton("TTTM");

        // Thêm các nút vào panel
        panelButtons.add(btnBenhVien);
        panelButtons.add(btnCongVien);
        panelButtons.add(btnDuong);
        panelButtons.add(btnKhachSan);
        panelButtons.add(btnNguoiDan);
        panelButtons.add(btnNha);
        panelButtons.add(btnQuan);
        panelButtons.add(btnTruongHoc);
        panelButtons.add(btnTTTM);

        // Thêm panel hình ảnh vào bên trái
        panelMain.add(imagePanel, BorderLayout.CENTER);

        // Thêm panel nút vào bên dưới hình ảnh
        panelMain.add(panelButtons, BorderLayout.SOUTH);

        // Thêm panel chính vào frame
        getContentPane().add(panelMain);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Bệnh viện":
                new BenhVienUI();
                break;
            case "Công viên":
                new CongVienUI();
                break;
            case "Đường":
                new DuongUI();
                break;
            case "Khách sạn":
                new KhachSanUI();
                break;
            case "Người dân":
                new NguoiDanUI();
                break;
            case "Nhà":
                new NhaUI();
                break;
            case "Quận":
                new QuanUI();
                break;
            case "Trường học":
                new TruongHocUI();
                break;
            case "TTTM":
                new TTTMUI();
                break;
            default:
                break;
        }
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE); // Đổi màu chữ nút thành trắng
        button.setBackground(Color.DARK_GRAY); // Đổi màu nền của nút thành xám đen
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(180, 60)); // Set button size
        button.addActionListener(this);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new AdminUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}