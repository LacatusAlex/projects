package org.example.business;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int orderID;
    private int clientID;
    private LocalDateTime orderDate;
    private int price;


    public Order(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        orderDate=LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
