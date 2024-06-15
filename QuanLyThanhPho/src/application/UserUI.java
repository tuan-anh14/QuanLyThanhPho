package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserUI extends JFrame {
    private JPanel panelMain;
    private DefaultListModel<String> listModel;
    private JList<String> objectList;
    private JScrollPane listScrollPane;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel statusLabel;

    private List<String> objectNames;

    public UserUI(List<String> objectNames) {
        super("User Dashboard");

        this.objectNames = objectNames;

        initializeUIComponents();
        displayObjectList(objectNames);
    }

    private void initializeUIComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit
        setLayout(new BorderLayout());

        panelMain = new JPanel(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Object list panel
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Object List"));

        listModel = new DefaultListModel<>();
        objectList = new JList<>(listModel);
        objectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listScrollPane = new JScrollPane(objectList);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        panelMain.add(listPanel, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        searchField = new JTextField(20);
        searchInputPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchInputPanel.add(searchButton);

        searchPanel.add(searchInputPanel, BorderLayout.CENTER);

        panelMain.add(searchPanel, BorderLayout.NORTH);

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Total objects: " + objectNames.size());
        statusPanel.add(statusLabel);

        panelMain.add(statusPanel, BorderLayout.SOUTH);

        add(panelMain);

        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void displayObjectList(List<String> objects) {
        listModel.clear();
        for (String object : objects) {
            listModel.addElement(object);
        }
    }

    private void performSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        List<String> filteredList = new ArrayList<>();
        for (String object : objectNames) {
            if (object.toLowerCase().contains(searchText)) {
                filteredList.add(object);
            }
        }
        displayObjectList(filteredList);
        updateStatusLabel(filteredList.size());
    }

    private void updateStatusLabel(int count) {
        statusLabel.setText("Total objects: " + count);
    }

    public static void main(String[] args) {
        List<String> objects = new ArrayList<>();
        objects.add("TTTM");
        objects.add("BenhVien");
        objects.add("CongVien");
        objects.add("NhaUi");
        objects.add("QuanUI");
        objects.add("TruongHocUI");

        SwingUtilities.invokeLater(() -> {
            new UserUI(objects);
        });
    }
}