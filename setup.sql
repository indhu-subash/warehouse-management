-- Warehouse Management System Database Setup Script
-- Run this script in MySQL to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS warehouse_db;
USE warehouse_db;

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT,
    location VARCHAR(100)
);

-- Create suppliers table
CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(50),
    address VARCHAR(150)
);

-- Insert some sample data
INSERT INTO items (name, quantity, location) VALUES 
('Laptop', 50, 'A1-01'),
('Mouse', 200, 'A1-02'),
('Keyboard', 150, 'A1-03'),
('Monitor', 75, 'A2-01');

INSERT INTO suppliers (name, contact, address) VALUES 
('TechCorp Inc', '555-0101', '123 Tech Street, Silicon Valley, CA'),
('Office Supplies Co', '555-0202', '456 Business Ave, New York, NY'),
('Electronics Plus', '555-0303', '789 Digital Blvd, Austin, TX');
