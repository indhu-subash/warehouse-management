package gui;

import model.Supplier;
import service.SupplierService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Supplier management frame for CRUD operations on suppliers
 * Provides interface to add, edit, delete, and view suppliers
 */
public class SupplierFrame extends JFrame {
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField contactField;
    private JTextField addressField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    
    private SupplierService supplierService;
    private int selectedSupplierId = -1;
    
    /**
     * Constructor to initialize the supplier frame
     */
    public SupplierFrame() {
        supplierService = new SupplierService();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFrameProperties();
        loadSuppliers();
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Table setup
        String[] columnNames = {"ID", "Name", "Contact", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        supplierTable = new JTable(tableModel);
        supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onTableSelection();
            }
        });
        
        // Input fields
        nameField = new JTextField(20);
        contactField = new JTextField(20);
        addressField = new JTextField(20);
        
        // Buttons
        addButton = new JButton("Add Supplier");
        editButton = new JButton("Edit Supplier");
        deleteButton = new JButton("Delete Supplier");
        refreshButton = new JButton("Refresh");
        
        // Initially disable edit and delete buttons
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    /**
     * Setup the layout of components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Supplier Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Supplier Details"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);
        
        // Contact field
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Contact:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(contactField, gbc);
        
        // Address field
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(addressField, gbc);
        
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup event handlers for components
     */
    private void setupEventHandlers() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSupplier();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplier();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSuppliers();
            }
        });
    }
    
    /**
     * Set frame properties
     */
    private void setFrameProperties() {
        setTitle("Supplier Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }
    
    /**
     * Load all suppliers from database and populate table
     */
    private void loadSuppliers() {
        tableModel.setRowCount(0); // Clear existing data
        
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        for (Supplier supplier : suppliers) {
            Object[] row = {
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getAddress()
            };
            tableModel.addRow(row);
        }
        
        clearFields();
    }
    
    /**
     * Handle table selection change
     */
    private void onTableSelection() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedSupplierId = (Integer) tableModel.getValueAt(selectedRow, 0);
            nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            contactField.setText((String) tableModel.getValueAt(selectedRow, 2));
            addressField.setText((String) tableModel.getValueAt(selectedRow, 3));
            
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            clearFields();
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields() {
        nameField.setText("");
        contactField.setText("");
        addressField.setText("");
        selectedSupplierId = -1;
        supplierTable.clearSelection();
    }
    
    /**
     * Add a new supplier
     */
    private void addSupplier() {
        if (!validateInput()) {
            return;
        }
        
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String address = addressField.getText().trim();
        
        Supplier supplier = new Supplier(name, contact, address);
        
        if (supplierService.addSupplier(supplier)) {
            JOptionPane.showMessageDialog(this, "Supplier added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadSuppliers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add supplier. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Edit selected supplier
     */
    private void editSupplier() {
        if (selectedSupplierId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) {
            return;
        }
        
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String address = addressField.getText().trim();
        
        Supplier supplier = new Supplier(selectedSupplierId, name, contact, address);
        
        if (supplierService.updateSupplier(supplier)) {
            JOptionPane.showMessageDialog(this, "Supplier updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadSuppliers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update supplier. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete selected supplier
     */
    private void deleteSupplier() {
        if (selectedSupplierId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this supplier?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            if (supplierService.deleteSupplier(selectedSupplierId)) {
                JOptionPane.showMessageDialog(this, "Supplier deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadSuppliers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete supplier. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Validate input fields
     * @return true if input is valid, false otherwise
     */
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter supplier name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (contactField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter contact information.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            contactField.requestFocus();
            return false;
        }
        
        if (addressField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter address.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            addressField.requestFocus();
            return false;
        }
        
        return true;
    }
}
