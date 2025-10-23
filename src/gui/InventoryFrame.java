package gui;

import model.Item;
import service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Inventory management frame for CRUD operations on items
 * Provides interface to add, edit, delete, and view inventory items
 */
public class InventoryFrame extends JFrame {
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField locationField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    
    private InventoryService inventoryService;
    private int selectedItemId = -1;
    
    /**
     * Constructor to initialize the inventory frame
     */
    public InventoryFrame() {
        inventoryService = new InventoryService();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFrameProperties();
        loadItems();
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Table setup
        String[] columnNames = {"ID", "Name", "Quantity", "Location"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        itemTable = new JTable(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onTableSelection();
            }
        });
        
        // Input fields
        nameField = new JTextField(20);
        quantityField = new JTextField(20);
        locationField = new JTextField(20);
        
        // Buttons
        addButton = new JButton("Add Item");
        editButton = new JButton("Edit Item");
        deleteButton = new JButton("Delete Item");
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
        JLabel titleLabel = new JLabel("Inventory Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);
        
        // Quantity field
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(quantityField, gbc);
        
        // Location field
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(locationField, gbc);
        
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
                addItem();
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editItem();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadItems();
            }
        });
    }
    
    /**
     * Set frame properties
     */
    private void setFrameProperties() {
        setTitle("Inventory Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
    }
    
    /**
     * Load all items from database and populate table
     */
    private void loadItems() {
        tableModel.setRowCount(0); // Clear existing data
        
        List<Item> items = inventoryService.getAllItems();
        for (Item item : items) {
            Object[] row = {
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getLocation()
            };
            tableModel.addRow(row);
        }
        
        clearFields();
    }
    
    /**
     * Handle table selection change
     */
    private void onTableSelection() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedItemId = (Integer) tableModel.getValueAt(selectedRow, 0);
            nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            quantityField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            locationField.setText((String) tableModel.getValueAt(selectedRow, 3));
            
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
        quantityField.setText("");
        locationField.setText("");
        selectedItemId = -1;
        itemTable.clearSelection();
    }
    
    /**
     * Add a new item
     */
    private void addItem() {
        if (!validateInput()) {
            return;
        }
        
        try {
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String location = locationField.getText().trim();
            
            Item item = new Item(name, quantity, location);
            
            if (inventoryService.addItem(item)) {
                JOptionPane.showMessageDialog(this, "Item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add item. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity (number).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Edit selected item
     */
    private void editItem() {
        if (selectedItemId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) {
            return;
        }
        
        try {
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String location = locationField.getText().trim();
            
            Item item = new Item(selectedItemId, name, quantity, location);
            
            if (inventoryService.updateItem(item)) {
                JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update item. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity (number).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete selected item
     */
    private void deleteItem() {
        if (selectedItemId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this item?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            if (inventoryService.deleteItem(selectedItemId)) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete item. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Validate input fields
     * @return true if input is valid, false otherwise
     */
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter item name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (quantityField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            quantityField.requestFocus();
            return false;
        }
        
        if (locationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter location.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            locationField.requestFocus();
            return false;
        }
        
        return true;
    }
}
