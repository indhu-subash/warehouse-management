package gui;

import model.Item;
import model.Supplier;
import service.InventoryService;
import service.SupplierService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Reports frame for generating various inventory and supplier reports
 * Provides comprehensive reporting functionality
 */
public class ReportsFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable inventoryTable;
    private JTable lowStockTable;
    private JTable supplierTable;
    private JTable categoryTable;
    
    private InventoryService inventoryService;
    private SupplierService supplierService;
    
    /**
     * Constructor to initialize the reports frame
     */
    public ReportsFrame() {
        inventoryService = new InventoryService();
        supplierService = new SupplierService();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFrameProperties();
        loadAllReports();
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        
        // Inventory Summary Table
        String[] inventoryColumns = {"ID", "Name", "Quantity", "Location", "Category", "Price", "Min Stock", "Status"};
        DefaultTableModel inventoryModel = new DefaultTableModel(inventoryColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inventoryTable = new JTable(inventoryModel);
        
        // Low Stock Table
        String[] lowStockColumns = {"ID", "Name", "Current Stock", "Min Required", "Deficit", "Category", "Location"};
        DefaultTableModel lowStockModel = new DefaultTableModel(lowStockColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lowStockTable = new JTable(lowStockModel);
        
        // Supplier Summary Table
        String[] supplierColumns = {"ID", "Name", "Contact", "Address", "Items Supplied"};
        DefaultTableModel supplierModel = new DefaultTableModel(supplierColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        supplierTable = new JTable(supplierModel);
        
        // Category Summary Table
        String[] categoryColumns = {"Category", "Total Items", "Total Quantity", "Total Value", "Low Stock Items"};
        DefaultTableModel categoryModel = new DefaultTableModel(categoryColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        categoryTable = new JTable(categoryModel);
    }
    
    /**
     * Setup the layout of components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Warehouse Reports & Analytics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
        // Create tabs
        tabbedPane.addTab("Inventory Summary", createScrollPane(inventoryTable));
        tabbedPane.addTab("Low Stock Alert", createScrollPane(lowStockTable));
        tabbedPane.addTab("Supplier Summary", createScrollPane(supplierTable));
        tabbedPane.addTab("Category Analysis", createScrollPane(categoryTable));
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh All Reports");
        JButton exportButton = new JButton("Export to CSV");
        JButton closeButton = new JButton("Close");
        
        refreshButton.addActionListener(_ -> loadAllReports());
        exportButton.addActionListener(_ -> exportToCSV());
        closeButton.addActionListener(_ -> dispose());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Create a scroll pane for a table
     */
    private JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        return scrollPane;
    }
    
    /**
     * Setup event handlers for components
     */
    private void setupEventHandlers() {
        // Add any additional event handlers here
    }
    
    /**
     * Set frame properties
     */
    private void setFrameProperties() {
        setTitle("Warehouse Reports & Analytics");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }
    
    /**
     * Load all reports
     */
    private void loadAllReports() {
        loadInventorySummary();
        loadLowStockReport();
        loadSupplierSummary();
        loadCategoryAnalysis();
    }
    
    /**
     * Load inventory summary report
     */
    private void loadInventorySummary() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0);
        
        List<Item> items = inventoryService.getAllItems();
        for (Item item : items) {
            String status = item.isLowStock() ? "LOW STOCK" : "OK";
            Object[] row = {
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getLocation(),
                item.getCategory(),
                String.format("$%.2f", item.getPrice()),
                item.getMinStockLevel(),
                status
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load low stock report
     */
    private void loadLowStockReport() {
        DefaultTableModel model = (DefaultTableModel) lowStockTable.getModel();
        model.setRowCount(0);
        
        List<Item> lowStockItems = inventoryService.getLowStockItems();
        for (Item item : lowStockItems) {
            int deficit = item.getMinStockLevel() - item.getQuantity();
            Object[] row = {
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getMinStockLevel(),
                deficit,
                item.getCategory(),
                item.getLocation()
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load supplier summary report
     */
    private void loadSupplierSummary() {
        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        model.setRowCount(0);
        
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        for (Supplier supplier : suppliers) {
            // Count items supplied by this supplier
            int itemCount = countItemsBySupplier(supplier.getId());
            
            Object[] row = {
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getAddress(),
                itemCount
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load category analysis report
     */
    private void loadCategoryAnalysis() {
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.setRowCount(0);
        
        List<String> categories = inventoryService.getAllCategories();
        for (String category : categories) {
            List<Item> categoryItems = inventoryService.getItemsByCategory(category);
            
            int totalItems = categoryItems.size();
            int totalQuantity = categoryItems.stream().mapToInt(Item::getQuantity).sum();
            double totalValue = categoryItems.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
            long lowStockCount = categoryItems.stream().filter(Item::isLowStock).count();
            
            Object[] row = {
                category,
                totalItems,
                totalQuantity,
                String.format("$%.2f", totalValue),
                lowStockCount
            };
            model.addRow(row);
        }
    }
    
    /**
     * Count items supplied by a specific supplier
     */
    private int countItemsBySupplier(int supplierId) {
        List<Item> allItems = inventoryService.getAllItems();
        return (int) allItems.stream().filter(item -> item.getSupplierId() == supplierId).count();
    }
    
    /**
     * Export current report to CSV (placeholder)
     */
    private void exportToCSV() {
        JOptionPane.showMessageDialog(this, 
            "CSV export functionality would be implemented here.\n" +
            "This would export the currently visible report to a CSV file.", 
            "Export to CSV", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
