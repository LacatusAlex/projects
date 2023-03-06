package org.example.model;

import org.example.business.MenuItem;
import org.example.business.Order;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Client {
    public Order createNewOrder(int orderId,int clientId){
        Order order=new Order(orderId,clientId);
        return order;
    }
    public Set<MenuItem> select(Set<MenuItem> itemSet,boolean[] selections,String string ){
        Set<MenuItem> finalItemSet ;

        finalItemSet =itemSet.stream().filter(item -> item.getRating()>4||!selections[0])
                                        .filter(item -> item.getCalories()<300||!selections[1])
                                        .filter(item -> item.getSodium()<20||!selections[2])
                                        .filter(item -> item.getPrice()<25||!selections[3])
                                        .filter(item -> item.getTitle().contains(string)||string=="").collect(Collectors.toSet());




        //finalItemSet= (Set<MenuItem>) stream.collect(Collectors.toSet());

        return finalItemSet;


    }

}
