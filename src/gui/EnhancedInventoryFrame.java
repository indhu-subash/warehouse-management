package gui;

import database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EnhancedInventoryFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtQuantity, txtCategory, txtPrice, txtSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnClear;

    public EnhancedInventoryFrame() {
        setTitle("Warehouse Inventory Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel lblTitle = new JLabel("Warehouse Inventory Management", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblTitle, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"ID", "Name", "Quantity","Category", "Price"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form panel
        // Form panel (realigned neatly)
JPanel formPanel = new JPanel(new GridBagLayout());
formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
gbc.fill = GridBagConstraints.HORIZONTAL;

txtName = new JTextField(15);
txtQuantity = new JTextField(15);
txtCategory = new JTextField(15);
txtPrice = new JTextField(15);

// Row 1: Name and Quantity
gbc.gridx = 0; gbc.gridy = 0;
formPanel.add(new JLabel("Name:"), gbc);
gbc.gridx = 1;
formPanel.add(txtName, gbc);
gbc.gridx = 2;
formPanel.add(new JLabel("Quantity:"), gbc);
gbc.gridx = 3;
formPanel.add(txtQuantity, gbc);

// Row 2: Category and Price
gbc.gridx = 0; gbc.gridy = 1;
formPanel.add(new JLabel("Category:"), gbc);
gbc.gridx = 1;
formPanel.add(txtCategory, gbc);
gbc.gridx = 2;
formPanel.add(new JLabel("Price:"), gbc);
gbc.gridx = 3;
formPanel.add(txtPrice, gbc);


        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        btnClear = new JButton("Clear");

        txtSearch = new JTextField(15);
        btnPanel.add(new JLabel("Search by Name:"));
        btnPanel.add(txtSearch);
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSearch);
        btnPanel.add(btnClear);

        // ✅ Combine form and button panels at bottom
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load existing products
        loadProducts();

        // Button actions
        btnAdd.addActionListener(e -> addProduct());
        btnEdit.addActionListener(e -> editProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnSearch.addActionListener(e -> searchProduct());
        btnClear.addActionListener(e -> clearFields());

     table.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        txtName.setText(model.getValueAt(row, 1).toString());
        txtQuantity.setText(model.getValueAt(row, 2).toString());
        txtCategory.setText(model.getValueAt(row, 3).toString());
        txtPrice.setText(model.getValueAt(row, 4).toString());
    }
});

    }


    private void loadProducts() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getDouble("price")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage());
        }
    }

    private void addProduct() {
    try (Connection conn = DBConnection.getConnection()) {
        String query = "INSERT INTO products (name, quantity, category, price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, txtName.getText());
        ps.setInt(2, Integer.parseInt(txtQuantity.getText()));
        ps.setString(3, txtCategory.getText());
        ps.setDouble(4, Double.parseDouble(txtPrice.getText()));
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Product added successfully!");
        loadProducts();
        clearFields();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage());
    }
}
    private void editProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE products SET name=?, quantity=?,category=?, price=? WHERE product_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, txtName.getText());
            ps.setInt(2, Integer.parseInt(txtQuantity.getText()));
            ps.setString(3, txtCategory.getText());
            ps.setDouble(4, Double.parseDouble(txtPrice.getText()));
            ps.setInt(5, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product updated successfully!");
            loadProducts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating product: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM products WHERE product_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            loadProducts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting product: " + ex.getMessage());
        }
    }

    private void searchProduct() {
        model.setRowCount(0);
        String search = txtSearch.getText();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM products WHERE name LIKE ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getDouble("price")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching product: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtQuantity.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        txtSearch.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnhancedInventoryFrame().setVisible(true));
    }
}
