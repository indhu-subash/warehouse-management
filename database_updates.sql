-- Enhanced Warehouse Management System Database Updates
-- Run these commands to add new features

USE warehouse_db;

-- Add new columns to items table
ALTER TABLE items 
ADD COLUMN category VARCHAR(50) DEFAULT 'General',
ADD COLUMN description TEXT,
ADD COLUMN min_stock_level INT DEFAULT 10,
ADD COLUMN price DECIMAL(10,2) DEFAULT 0.00,
ADD COLUMN supplier_id INT,
ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Add foreign key constraint for supplier
ALTER TABLE items 
ADD CONSTRAINT fk_items_supplier 
FOREIGN KEY (supplier_id) REFERENCES suppliers(id);

-- Create categories table
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Insert default categories
INSERT INTO categories (name, description) VALUES 
('Electronics', 'Electronic devices and components'),
('Office Supplies', 'Office equipment and supplies'),
('Furniture', 'Office and warehouse furniture'),
('Tools', 'Tools and equipment'),
('General', 'General warehouse items');

-- Create stock_movements table for tracking
CREATE TABLE IF NOT EXISTS stock_movements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    movement_type ENUM('IN', 'OUT', 'ADJUSTMENT') NOT NULL,
    quantity INT NOT NULL,
    reason VARCHAR(100),
    user_name VARCHAR(50),
    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(id)
);

-- Create users table for user management
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'STAFF') DEFAULT 'STAFF',
    email VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert default admin user (password: admin123)
INSERT INTO users (username, password, full_name, role, email) VALUES 
('admin', 'admin123', 'System Administrator', 'ADMIN', 'admin@warehouse.com');

-- Update existing items with categories
UPDATE items SET category = 'Electronics' WHERE name IN ('Laptop', 'Mouse', 'Keyboard', 'Monitor');
UPDATE items SET supplier_id = 1 WHERE name IN ('Laptop', 'Mouse');
UPDATE items SET supplier_id = 2 WHERE name IN ('Keyboard', 'Monitor');
UPDATE items SET min_stock_level = 20, price = 999.99 WHERE name = 'Laptop';
UPDATE items SET min_stock_level = 50, price = 25.99 WHERE name = 'Mouse';
UPDATE items SET min_stock_level = 30, price = 75.99 WHERE name = 'Keyboard';
UPDATE items SET min_stock_level = 15, price = 299.99 WHERE name = 'Monitor';
