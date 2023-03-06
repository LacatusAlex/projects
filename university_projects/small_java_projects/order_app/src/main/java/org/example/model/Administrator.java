package org.example.model;

import org.example.business.BaseProduct;
import org.example.business.CompositeProduct;
import org.example.business.MenuItem;
import org.example.business.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Administrator {
    public Set<MenuItem> importProducts(Set<MenuItem> menuItemSet) {
        List<String> stringList;

        try {
            Stream<String> stream = Files.lines(Paths.get("products.csv"));
            stringList=stream.collect(Collectors.toList());
            stringList.remove(0);
            for(String string:stringList){
                String param[]=string.split(",",7);
                menuItemSet.add(new BaseProduct(param[0],Double.parseDouble(param[1]),Integer.parseInt(param[2])
                        ,Integer.parseInt(param[3]),Integer.parseInt(param[4]),Integer.parseInt(param[5]),Integer.parseInt(param[6])));
            }
            return menuItemSet;

        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public MenuItem add(String[] params,Set<MenuItem> menuItemSet) {
        MenuItem item = new MenuItem();
        item.setParams(params);
        menuItemSet.add(item);
        return item;
    }


    public MenuItem delete(String name,Set<MenuItem> menuItemSet) {
        for(MenuItem item:menuItemSet){
            if(item.getTitle().equals(name)){
                menuItemSet.remove(item);
                return item;
            }
        }
        return null;
    }


    public MenuItem modify(String name, String[] params,Set<MenuItem> menuItemSet) {
        MenuItem itemFound=null;
        for(MenuItem item:menuItemSet){
            if(item.getTitle().equals(name)){
                item.setParams(params);
                itemFound= item;
                break;
            }
        }
        if(itemFound!=null) {
            for (MenuItem item : menuItemSet) {
                item.update();
            }
        }

        return itemFound;
    }
    private static int id=0;
    public void generateReports(int selection, Map<Order,Set<MenuItem>> setMap, ArrayList<Order> orders,Set<MenuItem> itemSet,
                                int[] params,ArrayList<Integer> clients,LocalDateTime date) throws IOException {
        String filename="Report"+id++ +".txt";
        File file=new File(filename);
        FileWriter printer = new FileWriter(file);
        switch (selection){
            case 0:
                printer.write("Orders between time interval "+params[0]+"-"+params[1]+"\n");
                Set<Order> ordersSelected=  orders.stream().filter(order -> order.getOrderDate().getHour()>=params[0]
                        && order.getOrderDate().getHour()<params[1]).collect(Collectors.toSet());
                for(Order order:ordersSelected){
                    printer.write("Order id: " +order.getOrderID() + " Client id: "+ order.getClientID()+" time: "+order.getOrderDate().getHour()+":"+order.getOrderDate().getMinute()+"\n" );
                }
                break;
            case 1:
                printer.write("Product ordered more then "+params[2]+" times\n");
                Set<MenuItem> items=  itemSet.stream().filter(item -> item.getTimesOrdered()>params[2]).collect(Collectors.toSet());
                for(MenuItem item:items){
                    printer.write("Product id: " +item.getTitle() + " times: "+ item.getTimesOrdered()+"\n");
                }
                break;
            case 2:
                printer.write("Clients who ordered more than "+params[3]+"times and have an order higher than"+params[4]+"\n");
                Set<Order> ordersSelected1=orders.stream().filter(order -> order.getPrice()>params[4]).collect(Collectors.toSet());
                for(Order order:ordersSelected1){
                    int count=0;
                    for(Integer client:clients){
                        if(client ==order.getClientID()){
                            count++;
                            //clients.remove(client);
                        }
                    }
                    if(count>params[3]){
                        printer.write("Client id: "+order.getClientID() +" has ordered "+count+"times and has an order of "+order.getPrice()+"\n" );
                    }

                }
                break;
            case 3:
                printer.write("Products ordered on  "+date.toString()+"with total ordered times higher than"+params[5]+"\n");

                Set<Order> ordersSelected2=  orders.stream().filter(order -> order.getOrderDate().toLocalDate().equals( date.toLocalDate())).collect(Collectors.toSet());
                for(Order order:ordersSelected2){
                    for(MenuItem item:setMap.get(order)){
                        printer.write("Product: " +item.getTitle() + "times: " +item.getTimesOrdered()+"\n" );
                    }
                }
                break;




        }
        printer.close();



    }

    public MenuItem createMenuItem(String name,Set<MenuItem> itemSet,ArrayList<String> chosenItems){


        Set<MenuItem> chosenItemSet=new HashSet<>();
        for(MenuItem item:itemSet){
            if(chosenItems.contains(item.getTitle())){
                chosenItemSet.add(item);
            }
        }
        MenuItem item=new CompositeProduct(name,chosenItemSet);
        itemSet.add(item);
        return item;



    }


}
