<<<<<<< HEAD
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

## Prerequisites

1. **Java Development Kit (JDK) 8 or higher**
2. **MySQL Server 5.7 or higher**
3. **MySQL JDBC Driver** (mysql-connector-java-8.0.33.jar or compatible)
4. **IDE** (IntelliJ IDEA, Eclipse, or any Java IDE)

## Database Setup

### Step 1: Install and Start MySQL Server
- Install MySQL Server on your system
- Start the MySQL service
- Note down your MySQL root password

### Step 2: Create Database and Tables
1. Open MySQL Command Line Client or MySQL Workbench
2. Run the provided SQL script:
   ```bash
   mysql -u root -p < setup.sql
   ```
   Or copy and paste the contents of `setup.sql` into your MySQL client

### Step 3: Verify Database Setup
The script will create:
- Database: `warehouse_db`
- Table: `items` (id, name, quantity, location)
- Table: `suppliers` (id, name, contact, address)
- Sample data for testing

## Configuration

### Database Connection Settings
Edit `src/database/DBConnection.java` and update the connection parameters:

```java
private static final String URL = "jdbc:mysql://localhost:3306/warehouse_db";
private static final String USERNAME = "root";           // Your MySQL username
private static final String PASSWORD = "yourpassword";   // Your MySQL password
```

### Login Credentials
Default login credentials (hardcoded for demo):
- **Username**: `admin`
- **Password**: `admin123`

## Running the Application

### Method 1: Using IDE
1. Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Add the MySQL JDBC driver to your project's classpath
3. Run the `Main.java` file

### Method 2: Command Line
1. Compile all Java files:
   ```bash
   javac -cp "path/to/mysql-connector-java.jar" src/*.java src/*/*.java
   ```

2. Run the application:
   ```bash
   java -cp "src:path/to/mysql-connector-java.jar" Main
   ```

### Method 3: Using Maven (if you convert to Maven project)
Add this dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

## Usage Guide

### 1. Login
- Launch the application
- Enter username: `admin`
- Enter password: `admin123`
- Click "Login"

### 2. Dashboard
- Use the dashboard to navigate between different modules
- Available options:
  - **Manage Inventory**: Add, edit, delete, and view inventory items
  - **Manage Suppliers**: Add, edit, delete, and view suppliers
  - **View Reports**: Placeholder for future reports feature
  - **Logout**: Return to login screen

### 3. Inventory Management
- View all items in a table format
- Add new items with name, quantity, and location
- Edit existing items by selecting them in the table
- Delete items with confirmation dialog
- Refresh the table to see latest data

### 4. Supplier Management
- View all suppliers in a table format
- Add new suppliers with name, contact, and address
- Edit existing suppliers by selecting them in the table
- Delete suppliers with confirmation dialog
- Refresh the table to see latest data

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Ensure MySQL server is running
   - Check username and password in `DBConnection.java`
   - Verify database `warehouse_db` exists
   - Ensure MySQL JDBC driver is in classpath

2. **ClassNotFoundException: com.mysql.cj.jdbc.Driver**
   - Download MySQL JDBC driver
   - Add it to your project's classpath
   - Ensure you're using a compatible version

3. **Access Denied for User**
   - Check MySQL user permissions
   - Ensure the user has access to `warehouse_db`
   - Try connecting with MySQL root user

4. **Table Doesn't Exist**
   - Run the `setup.sql` script
   - Check if you're connected to the correct database

### Getting Help
- Check the console output for detailed error messages
- Ensure all prerequisites are properly installed
- Verify database connection settings

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

## License

This project is created for educational purposes. Feel free to use and modify as needed.

## Support

For issues or questions, please check the troubleshooting section above or review the code comments for implementation details.
=======
# warehouse-management
>>>>>>> 893f8fdb91ee7355bac12ff348bc5bd67118f4ef
