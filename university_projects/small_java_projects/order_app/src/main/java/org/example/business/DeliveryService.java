package org.example.business;
import org.example.model.*;
import org.example.presentation.EmployeeGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The id of the order is at any time bigger than 0
 *
 * @invariant orderId>0
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private static Set<MenuItem> menuItemSet= new HashSet<>();
    private static Map<Order,Set<MenuItem>> mapOrder =new HashMap<>();
    private static ArrayList<Order> orders=new ArrayList<>();
    private static int orderId=1;
    private static ArrayList<Integer> clientsWhoOrdered= new ArrayList<>();
    private static EmployeeGUI employeeGUI;
    @Override
    public Set<MenuItem> importProducts() {
        return administrator.importProducts(menuItemSet);
    }

    @Override
    public MenuItem add(String[] params) {
        assert params[0].length()>=3 : "Too short title";
        MenuItem item=administrator.add(params,menuItemSet);
        assert item!=null:"Returned invalid MenuItem";
        return  administrator.add(params,menuItemSet);
    }

    @Override
    public MenuItem delete(String name) {
        return administrator.delete(name,menuItemSet);
    }

    @Override
    public MenuItem modify(String name,String[] params) {
        return administrator.modify(name,params,menuItemSet);
    }

    @Override
    public Order newOrder(Set<MenuItem> items,int clientId) {
        assert clientId>0:"Wrong client id provided";
        addObserver((Observer) employee);
        Order order=client.createNewOrder(orderId++,clientId);
        clientsWhoOrdered.add(clientId);
        int price=0;
        for (MenuItem item:items){
            price+=item.computePrice();
            item.incrementTimesOrdered();
        }
        order.setPrice(price);
        mapOrder.put(order,items);
        try {
            fileWriter.createReceipt(mapOrder,order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        orders.add(order);
        setChanged();
        notifyObservers(this);
        assert order!=null:"Returned an invalid order";
        return order;
    }

    public Set<MenuItem> select(boolean[] selections,String string){

        return client.select(menuItemSet,selections,string);
    }
    public void generateReports(int selection ,int[] params,LocalDateTime date){
        try {
            administrator.generateReports(selection,mapOrder,orders,menuItemSet,params,clientsWhoOrdered,date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MenuItem createMenuItem(String name,ArrayList<String> chosenItems){
        return administrator.createMenuItem(name,this.menuItemSet,chosenItems);
    }

    public Set<MenuItem> getMenuItemSet() {
        return menuItemSet;
    }

    public void setMenuItemSet(Set<MenuItem> menuItemSet) {
        this.menuItemSet = menuItemSet;
    }

    public Map<Order, Set<MenuItem>> getMapOrder() {
        return mapOrder;
    }

    public void setMapOrder(Map<Order, Set<MenuItem>> mapOrder) {
        this.mapOrder = mapOrder;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public static EmployeeGUI getEmployeeGUI() {
        return employeeGUI;
    }

    public static void setEmployeeGUI(EmployeeGUI employeeGUI) {
        DeliveryService.employeeGUI = employeeGUI;
    }
}
