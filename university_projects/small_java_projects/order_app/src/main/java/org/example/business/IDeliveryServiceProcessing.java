package org.example.business;

import org.example.data.FileWriter;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IDeliveryServiceProcessing {

    Administrator administrator = new Administrator();
    Client client = new Client();
    Employee employee = new Employee();
    FileWriter fileWriter = new FileWriter();



    Set<MenuItem> importProducts();

    /**
     *-tag pre:a:"precondition"
     *      *-tag post:a:"postcondition"
     *      *-tag invariant:a:"invariant"
     * the title always has at least length 3
     * @pre params[0].length>=3
     * always returns a valid MenuItem
     * @post return!=NULL
     *
     */
    MenuItem add(String[] params);
    MenuItem delete(String name);
    MenuItem modify(String name,String[] params);
    /**
     *
     *the id of the client always positive
     * @pre clientId>=0
     * always returns a valid Order
     * @post return!=NULL
     *
     */
    Order newOrder(Set<MenuItem> items,int clientId);






}
