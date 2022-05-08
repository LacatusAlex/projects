package org.example.model;

/**
 *
 * Orders is the class used for modeling an order from the database.It mirrors a row from the orders table
 * and has 3 constructors along with getters and setters needed in order to better manipulate our data.Note that the class is called "Orders" instead of
 * "Order" because "order" is a reserved word in sql language
 */
public class Orders {
    private int id;
    private String client;
    private String product;
    private String date;
    private int quantity;



    public Orders(){
        //empty constructor
    }

    public Orders(String client, String product, String date, int quantity) {
        this.client = client;
        this.product = product;
        this.date = date;
        this.quantity = quantity;
    }

    public Orders(int id, String client, String product, String date, int quantity) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.date = date;
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String toString(){
        return "Order [id=" + id + ", client=" + client + ", product=" + product
                +", date=" + date + ", quantity=" +quantity+"]";
    }
}
