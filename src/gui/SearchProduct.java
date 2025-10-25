package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchProduct extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private DefaultTableModel model;

    public SearchProduct() {
        setTitle("Search Products");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("Warehouse Product Search", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        topPanel.add(new JLabel("Enter Product Name: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.SOUTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Product ID");
        model.addColumn("Name");
        model.addColumn("Category");
        model.addColumn("Quantity");
        model.addColumn("Price");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                searchProducts(keyword);
            }
        });
    }

    private void searchProducts(String keyword) {
        model.setRowCount(0);
        String url = "jdbc:mysql://localhost:3306/warehouse_db";
        String user = "root";
        String pass = "";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SearchProduct().setVisible(true));
    }
}
