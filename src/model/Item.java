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
     */
    public Item(int id, String name, int quantity, String location) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }
    
    /**
     * Constructor without ID (for new items)
     * @param name Item name
     * @param quantity Item quantity
     * @param location Item location in warehouse
     */
    public Item(String name, int quantity, String location) {
        this.name = name;
        this.quantity = quantity;
        this.location = location;
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
                '}';
    }
}
