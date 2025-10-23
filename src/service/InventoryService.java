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
        String sql = "INSERT INTO items (name, quantity, location) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setString(3, item.getLocation());
            
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
        String sql = "UPDATE items SET name = ?, quantity = ?, location = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setString(3, item.getLocation());
            pstmt.setInt(4, item.getId());
            
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
                    rs.getString("location")
                );
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
                    return new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("location")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving item by ID: " + e.getMessage());
        }
        
        return null;
    }
}
