package org.example.model;

/**
 *
 * Client is the class used for modeling a client from the database.It mirrors a row from the client table
 * and has 3 constructors along with getters and setters needed in order to better manipulate our data
 */

public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private String contact;




    public Client(){
        //empty constructor
    }
    public Client(int id, String name, String address, String email, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public Client(String name, String address, String email, String contact) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @Override
    public String toString(){
        return "Client [id=" + id + ", name=" + name + ", address=" +
                address + ", email=" + email + ", contact=" + contact +"]";
    }
}
