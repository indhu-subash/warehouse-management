package model;

/**
 * Supplier model class representing a supplier entity
 * Contains properties and methods for supplier information
 */
public class Supplier {
    private int id;
    private String name;
    private String contact;
    private String address;
    
    /**
     * Default constructor
     */
    public Supplier() {}
    
    /**
     * Constructor with all parameters
     * @param id Supplier ID
     * @param name Supplier name
     * @param contact Supplier contact information
     * @param address Supplier address
     */
    public Supplier(int id, String name, String contact, String address) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }
    
    /**
     * Constructor without ID (for new suppliers)
     * @param name Supplier name
     * @param contact Supplier contact information
     * @param address Supplier address
     */
    public Supplier(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
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
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * String representation of Supplier
     * @return formatted string with supplier details
     */
    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
