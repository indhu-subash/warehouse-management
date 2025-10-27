package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard frame - main navigation hub for the warehouse management system
 * Provides buttons to access different modules of the system
 */
public class DashboardFrame extends JFrame {
    private JButton inventoryButton;
    private JButton supplierButton;
    private JButton reportsButton;
    private JButton logoutButton;
    
    /**
     * Constructor to initialize the dashboard frame
     */
    public DashboardFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFrameProperties();
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        inventoryButton = new JButton("Manage Inventory");
        supplierButton = new JButton("Manage Suppliers");
        reportsButton = new JButton("View Reports");
        logoutButton = new JButton("Logout");
        
        // Set button sizes
        Dimension buttonSize = new Dimension(200, 50);
        inventoryButton.setPreferredSize(buttonSize);
        supplierButton.setPreferredSize(buttonSize);
        reportsButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);
        
        // Set fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        inventoryButton.setFont(buttonFont);
        supplierButton.setFont(buttonFont);
        reportsButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);
    }
    
    /**
     * Setup the layout of components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Warehouse Management System - Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Add buttons in a grid layout
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(inventoryButton, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        mainPanel.add(supplierButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(reportsButton, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(logoutButton, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Welcome, Admin!"));
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup event handlers for components
     */
    private void setupEventHandlers() {
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInventoryFrame();
            }
        });
        
        supplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSupplierFrame();
            }
        });
        
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportsPlaceholder();
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogout();
            }
        });
    }
    
    /**
     * Set frame properties
     */
    private void setFrameProperties() {
        setTitle("Warehouse Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen
    }
    
    /**
     * Open the inventory management frame
     */
    private void openInventoryFrame() {
        SwingUtilities.invokeLater(() -> {
            new InventoryFrame().setVisible(true);
        });
    }
    
    /**
     * Open the supplier management frame
     */
    private void openSupplierFrame() {
        SwingUtilities.invokeLater(() -> {
            new SupplierFrame().setVisible(true);
        });
    }
    
    /**
     * Show placeholder message for reports feature
     */
    private void showReportsPlaceholder() {
        JOptionPane.showMessageDialog(this, 
            "Reports feature is not implemented yet.\nThis is a placeholder for future development.", 
            "Reports", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Perform logout and return to login screen
     */
    private void performLogout() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Logout Confirmation", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
        }
    }
}
