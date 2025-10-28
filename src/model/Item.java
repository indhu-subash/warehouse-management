package model;

/**
 * Item model class representing an inventory item
 * Contains properties and methods for warehouse items
 */
public class Item {
    private int id;
    private String name;
    private int quantity;
    private String location;
    private String category;
    private String description;
    private int minStockLevel;
    private double price;
    private int supplierId;
    private String createdDate;
    private String updatedDate;
    
    /**
     * Default constructor
     */
    public Item() {}
    
    /**
     * Constructor with all parameters
     * @param id Item ID
     * @param name Item name
     * @param quantity Item quantity
     * @param location Item location in warehouse
     * @param category Item category
     * @param description Item description
     * @param minStockLevel Minimum stock level
     * @param price Item price
     * @param supplierId Supplier ID
     */
    public Item(int id, String name, int quantity, String location, String category, 
                String description, int minStockLevel, double price, int supplierId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
        this.category = category;
        this.description = description;
        this.minStockLevel = minStockLevel;
        this.price = price;
        this.supplierId = supplierId;
    }
    
    /**
     * Constructor without ID (for new items)
     * @param name Item name
     * @param quantity Item quantity
     * @param location Item location in warehouse
     * @param category Item category
     * @param description Item description
     * @param minStockLevel Minimum stock level
     * @param price Item price
     * @param supplierId Supplier ID
     */
    public Item(String name, int quantity, String location, String category, 
                String description, int minStockLevel, double price, int supplierId) {
        this.name = name;
        this.quantity = quantity;
        this.location = location;
        this.category = category;
        this.description = description;
        this.minStockLevel = minStockLevel;
        this.price = price;
        this.supplierId = supplierId;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getMinStockLevel() {
        return minStockLevel;
    }
    
    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    /**
     * Check if item is low in stock
     * @return true if quantity is below minimum stock level
     */
    public boolean isLowStock() {
        return quantity <= minStockLevel;
    }
    
    /**
     * String representation of Item
     * @return formatted string with item details
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
