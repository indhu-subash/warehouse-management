
# Warehouse Management System

A complete desktop application built in Java using Swing for the GUI and MySQL for data persistence. The system follows MVC architecture and provides functionality for managing inventory items and suppliers.

## Features

- **User Authentication**: Secure login system with hardcoded admin credentials
- **Inventory Management**: Full CRUD operations for warehouse items
- **Supplier Management**: Complete supplier information management
- **Dashboard**: Central navigation hub for all system modules
- **Database Integration**: MySQL database with JDBC connectivity

## Project Structure

```
src/
├── Main.java                    # Application entry point
├── database/
│   └── DBConnection.java        # Database connection utility
├── gui/
│   ├── LoginFrame.java          # User authentication interface
│   ├── DashboardFrame.java      # Main navigation dashboard
│   ├── InventoryFrame.java      # Inventory management interface
│   └── SupplierFrame.java       # Supplier management interface
├── model/
│   ├── Item.java               # Item entity model
│   └── Supplier.java           # Supplier entity model
└── service/
    ├── InventoryService.java    # Inventory business logic
    └── SupplierService.java     # Supplier business logic
```


# 🏭 Warehouse Management System

A Java-based Warehouse Management System that helps manage inventory, suppliers, and stock operations efficiently.  
This project uses **Java Swing** for the GUI and **MySQL** as the backend database.

---

## 🚀 Features

- 📦 Manage inventory items (add, update, delete)
- 🧾 Track suppliers and purchase details
- 📊 Generate reports and view stock levels
- 🔐 Login system for admin and users
- 💾 MySQL database integration

---

## 🧰 Tech Stack

- **Frontend:** Java Swing  
- **Backend:** MySQL  
- **Database Connector:** JDBC  
- **IDE Used:** NetBeans / IntelliJ / Eclipse  

---


## Technical Details

### Architecture
- **MVC Pattern**: Clear separation of Model, View, and Controller
- **Service Layer**: Business logic separated from GUI
- **DAO Pattern**: Data access operations in service classes

### Database Design
- **Items Table**: Stores inventory information
- **Suppliers Table**: Stores supplier information
- **Auto-increment IDs**: Primary keys for both tables

### Security Features
- **Prepared Statements**: Protection against SQL injection
- **Input Validation**: Client-side validation for all inputs
- **Error Handling**: Comprehensive error handling and user feedback

## Future Enhancements

- User management system with multiple user roles
- Advanced reporting and analytics
- Barcode scanning integration
- Email notifications for low stock
- Data export functionality (CSV, PDF)
- Multi-warehouse support
- Purchase order management

