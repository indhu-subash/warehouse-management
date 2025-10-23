package service;

import database.DBConnection;
import model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for supplier management operations
 * Handles all database operations related to suppliers
 */
public class SupplierService {
    
    /**
     * Adds a new supplier
     * @param supplier Supplier object to add
     * @return true if successful, false otherwise
     */
    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (name, contact, address) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, supplier.getName());
            pstmt.setString(2, supplier.getContact());
            pstmt.setString(3, supplier.getAddress());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding supplier: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Updates an existing supplier
     * @param supplier Supplier object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET name = ?, contact = ?, address = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, supplier.getName());
            pstmt.setString(2, supplier.getContact());
            pstmt.setString(3, supplier.getAddress());
            pstmt.setInt(4, supplier.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating supplier: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deletes a supplier
     * @param id ID of the supplier to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting supplier: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves all suppliers
     * @return List of all suppliers
     */
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Supplier supplier = new Supplier(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("address")
                );
                suppliers.add(supplier);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving suppliers: " + e.getMessage());
        }
        
        return suppliers;
    }
    
    /**
     * Retrieves a supplier by its ID
     * @param id Supplier ID
     * @return Supplier object or null if not found
     */
    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("address")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving supplier by ID: " + e.getMessage());
        }
        
        return null;
    }
}
