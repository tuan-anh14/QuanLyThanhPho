package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginFrame() {
        setTitle("Member Login");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left panel for avatar image
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 400));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new GridBagLayout());

        JLabel avatarLabel = new JLabel();
        ImageIcon avatarIcon = new ImageIcon("F:\\JAVA\\QuanLyThanhPho-main\\QuanLyThanhPho-main\\QuanLyThanhPho\\images\\icon.png");
        Image avatarImage = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        avatarLabel.setIcon(new ImageIcon(avatarImage));
        leftPanel.add(avatarLabel);

        // Right panel for login form
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel loginLabel = new JLabel("Đăng nhập");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        rightPanel.add(loginLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel emailLabel = new JLabel("Username");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rightPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new RoundJTextField(20);
        emailField.setPreferredSize(new Dimension(250, 30)); // Set preferred size for emailField
        rightPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rightPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new RoundJPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 30)); // Set preferred size for passwordField
        rightPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        rightPanel.add(showPasswordCheckBox, gbc);

        gbc.gridy++;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.setBackground(new Color(76, 175, 80));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Simple authentication check
                if ("admin".equals(email) && "123".equals(password)) {
                    // Show AdminUI on successful login
                    AdminUI adminUI = new AdminUI();
                    adminUI.setVisible(true);
                    dispose(); // Close the LoginFrame
                } else {
                    errorLabel.setText("Invalid email or password. Please try again.");
                }
            }
        });
        rightPanel.add(loginButton, gbc);

        gbc.gridy++;
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        rightPanel.add(errorLabel, gbc);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}

class RoundJTextField extends JTextField {
    private Shape shape;
    public RoundJTextField(int size) {
        super(size);
        setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
        }
        return shape.contains(x, y);
    }
}

class RoundJPasswordField extends JPasswordField {
    private Shape shape;
    public RoundJPasswordField(int size) {
        super(size);
        setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
        }
        return shape.contains(x, y);
    }
}