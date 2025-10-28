package service;

import database.DBConnection;
import model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for inventory management operations
 * Handles all database operations related to items
 */
public class InventoryService {
    
    /**
     * Adds a new item to the inventory
     * @param item Item object to add
     * @return true if successful, false otherwise
     */
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (name, quantity, location, category, description, min_stock_level, price, supplier_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setString(3, item.getLocation());
            pstmt.setString(4, item.getCategory());
            pstmt.setString(5, item.getDescription());
            pstmt.setInt(6, item.getMinStockLevel());
            pstmt.setDouble(7, item.getPrice());
            pstmt.setInt(8, item.getSupplierId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding item: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Updates an existing item in the inventory
     * @param item Item object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, quantity = ?, location = ?, category = ?, description = ?, min_stock_level = ?, price = ?, supplier_id = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setString(3, item.getLocation());
            pstmt.setString(4, item.getCategory());
            pstmt.setString(5, item.getDescription());
            pstmt.setInt(6, item.getMinStockLevel());
            pstmt.setDouble(7, item.getPrice());
            pstmt.setInt(8, item.getSupplierId());
            pstmt.setInt(9, item.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating item: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deletes an item from the inventory
     * @param id ID of the item to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteItem(int id) {
        String sql = "DELETE FROM items WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting item: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves all items from the inventory
     * @return List of all items
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getString("location"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getInt("min_stock_level"),
                    rs.getDouble("price"),
                    rs.getInt("supplier_id")
                );
                item.setCreatedDate(rs.getString("created_date"));
                item.setUpdatedDate(rs.getString("updated_date"));
                items.add(item);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving items: " + e.getMessage());
        }
        
        return items;
    }
    
    /**
     * Retrieves an item by its ID
     * @param id Item ID
     * @return Item object or null if not found
     */
    public Item getItemById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getInt("min_stock_level"),
                        rs.getDouble("price"),
                        rs.getInt("supplier_id")
                    );
                    item.setCreatedDate(rs.getString("created_date"));
                    item.setUpdatedDate(rs.getString("updated_date"));
                    return item;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving item by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Search items by name
     * @param searchTerm Search term for item name
     * @return List of matching items
     */
    public List<Item> searchItemsByName(String searchTerm) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE name LIKE ? ORDER BY name";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + searchTerm + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getInt("min_stock_level"),
                        rs.getDouble("price"),
                        rs.getInt("supplier_id")
                    );
                    item.setCreatedDate(rs.getString("created_date"));
                    item.setUpdatedDate(rs.getString("updated_date"));
                    items.add(item);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching items: " + e.getMessage());
        }
        
        return items;
    }
    
    /**
     * Get items by category
     * @param category Category name
     * @return List of items in the category
     */
    public List<Item> getItemsByCategory(String category) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE category = ? ORDER BY name";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, category);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getInt("min_stock_level"),
                        rs.getDouble("price"),
                        rs.getInt("supplier_id")
                    );
                    item.setCreatedDate(rs.getString("created_date"));
                    item.setUpdatedDate(rs.getString("updated_date"));
                    items.add(item);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting items by category: " + e.getMessage());
        }
        
        return items;
    }
    
    /**
     * Get items with low stock (quantity <= min_stock_level)
     * @return List of items with low stock
     */
    public List<Item> getLowStockItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE quantity <= min_stock_level ORDER BY quantity ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getString("location"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getInt("min_stock_level"),
                    rs.getDouble("price"),
                    rs.getInt("supplier_id")
                );
                item.setCreatedDate(rs.getString("created_date"));
                item.setUpdatedDate(rs.getString("updated_date"));
                items.add(item);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving low stock items: " + e.getMessage());
        }
        
        return items;
    }
    
    /**
     * Get all available categories
     * @return List of category names
     */
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM items WHERE category IS NOT NULL ORDER BY category";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving categories: " + e.getMessage());
        }
        
        return categories;
    }
}

