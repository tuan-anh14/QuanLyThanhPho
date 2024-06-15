package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;

public class AdminUI extends JFrame {
    private JPanel panelMain;
    private CardLayout cardLayout;
    private DefaultListModel<String> listModel;
    private JList<String> objectList;
    private JScrollPane listScrollPane;
    private TTTMUI tttmUI;
    private BenhVienUI benhVienUI;
    private CongVienUI congVienUI;
    // Add other UI variables as needed

    public AdminUI() {
        super("Admin Dashboard");

        initializeUIComponents();

        // Initialize UI objects for each type
        tttmUI = new TTTMUI();
        benhVienUI = new BenhVienUI();
        congVienUI = new CongVienUI();
        // Initialize other UI objects as needed

        // Add UI panels to CardLayout
        panelMain.add(tttmUI.getContentPane(), "TTTM");
        panelMain.add(benhVienUI.getRootPane(), "BenhVien");
        panelMain.add(congVienUI.getRootPane(), "CongVien");
        // Add other UI panels to the CardLayout as needed

        // Set CardLayout for panelMain
        cardLayout = (CardLayout) panelMain.getLayout();
    }

    private void initializeUIComponents() {
        panelMain = new JPanel();
        panelMain.setLayout(new CardLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(panelMain, BorderLayout.CENTER);

        // Add objects to display in the list
        listModel = new DefaultListModel<>();
        listModel.addElement("TTTM");
        listModel.addElement("BenhVien");
        listModel.addElement("CongVien");
        // Add other object names as needed

        objectList = new JList<>(listModel);
        objectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        objectList.setDragEnabled(true);

        listScrollPane = new JScrollPane(objectList);
        listScrollPane.setPreferredSize(new Dimension(150, 400));
        add(listScrollPane, BorderLayout.WEST);

        // Set DropTarget to handle drag and drop events
        DropTarget dropTarget = new DropTarget(tttmUI.getContentPane(), new MyDropTargetListener());
        tttmUI.getContentPane().setDropTarget(dropTarget);

        // Add selection listener to objectList
        objectList.addListSelectionListener(e -> {
            String selectedObject = objectList.getSelectedValue();
            if (selectedObject != null) {
                switch (selectedObject) {
                    case "TTTM":
                        cardLayout.show(panelMain, "TTTM");
                        break;
                    case "BenhVien":
                        cardLayout.show(panelMain, "BenhVien");
                        break;
                    case "CongVien":
                        cardLayout.show(panelMain, "CongVien");
                        break;
                    // Add cases for other objects as needed
                }
            }
        });

        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    // DropTargetListener to handle drag and drop events
    class MyDropTargetListener implements DropTargetListener {
        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            // Not needed to handle
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            // Not needed to handle
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
            // Not needed to handle
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            // Not needed to handle
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            Transferable transferable = dtde.getTransferable();
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    String droppedObject = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    if (droppedObject != null) {
                        switch (droppedObject) {
                            case "TTTM":
                                cardLayout.show(panelMain, "TTTM");
                                break;
                            case "BenhVien":
                                cardLayout.show(panelMain, "BenhVien");
                                break;
                            case "CongVien":
                                cardLayout.show(panelMain, "CongVien");
                                break;
                            // Add cases for other objects as needed
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
            dtde.dropComplete(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                AdminUI adminUI = new AdminUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}