import java.sql.*;
import gui.LoginFrame;
import database.DBConnection;
import javax.swing.*;
import javax.swing.UIManager;

/**
 * Main entry point for the Warehouse Management System
 * Initializes the application and shows the login frame
 */
public class Main {
    
    /**
     * Main method - entry point of the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set look and feel to system default (optional)
        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        
        // Test database connection
        System.out.println("Testing database connection...");
        if (!DBConnection.testConnection()) {
            JOptionPane.showMessageDialog(null, 
                "Failed to connect to database!\n\n" +
                "Please ensure:\n" +
                "1. MySQL server is running\n" +
                "2. Database 'warehouse_db' exists\n" +
                "3. Username and password in DBConnection.java are correct\n" +
                "4. MySQL JDBC driver is in classpath", 
                "Database Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        System.out.println("Database connection successful!");
        
        // Create and show login frame on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginFrame().setVisible(true);
                System.out.println("Warehouse Management System started successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error starting application: " + e.getMessage(), 
                    "Startup Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
