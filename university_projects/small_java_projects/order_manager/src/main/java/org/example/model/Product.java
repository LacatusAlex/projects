package org.example.model;

/**
 *
 * Product is the class used for modeling a product from the database.It mirrors a row from the product table
 * and has 3 constructors along with getters and setters needed in order to better manipulate our data
 */
public class Product {
    private int id;
    private String name;
    private String category;
    private int price;
    private int quantity;


    public Product(){

        //empty constructor
    }

    public Product(String name, String category, int price,int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity=quantity;
    }

    public Product(int id, String name, String category, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity=quantity;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Override
    public String toString(){
        return "Product [id=" + id + ", name=" + name + ", category=" + category
                +", price=" + price +"]";
    }
}
