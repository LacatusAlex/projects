package org.example.data;

import org.example.business.MenuItem;
import org.example.business.Order;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class FileWriter {
    java.io.FileWriter printer ;
    File file;


    public void createReceipt( Map<Order, Set<MenuItem>> map,Order order) throws IOException {

        String filename="Receipt_"+ order.getOrderID() +".txt";
        file=new File(filename);
        printer = new java.io.FileWriter(file);
        Set<MenuItem> itemSet= map.get(order);
        printer.write(filename +"\n");
        for(MenuItem item:itemSet){
            printer.write(item.getTitle()+"\n");
        }
        printer.write("Client id: "+order.getClientID() +"    price: "+ order.getPrice());
        printer.close();


    }
}
