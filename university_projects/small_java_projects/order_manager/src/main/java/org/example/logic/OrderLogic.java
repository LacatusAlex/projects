package org.example.logic;

import org.example.data.access.OrderDAO;
import org.example.logic.validator.*;
import org.example.model.Orders;

import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * OrderLogic class represents the highest level in the architecture for an order having the OrderDAO and
 * being directly called at the higher level of our app.
 * It is also the one responsible for calling the validators for each order
 */

public class OrderLogic {
    private ArrayList<Validator<Orders>> validators;
    private OrderDAO orderDAO;
    public OrderLogic(){
        validators= new ArrayList<>();
        validators.add(new DateValidator());
        orderDAO= new OrderDAO();

    }

    public Orders findOrderById(int id){
        Orders orders = orderDAO.findById(id);
        for(Validator validator:validators){
            validator.validate(orders);
        }
        if(orders ==null){
            throw new NoSuchElementException();
        }
        return orders;
    }
    public Orders validate(Orders orders){
        for(Validator validator :validators){

            validator.validate(orders);
        }
        if(orders ==null){
            throw new NoSuchElementException();
        }
        return orders;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
}
