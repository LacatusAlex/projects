package org.example.presentation;


import org.example.logic.ClientLogic;
import org.example.logic.OrderLogic;
import org.example.logic.ProductLogic;
import org.example.model.Client;
import org.example.model.Orders;
import org.example.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Here we have the controller class.
 * This class provides the connection between the gui component and the DAO classes
 * This is the responsive component of our application
 * It has methods that use the DAOs in order to make changes in our tables (insert,update,delete) as well as methods
 * used for refreshing the output in the gui (clear,findAll,find).
 * More useful methods are the ones used for working with the parameters provided by the view (used for the methods mentioned
 * before)
 *
 */

public class Controller implements ActionListener {

    private View view;
    private JFrame viewResults;
    private File logFile=new File("logFile.txt");
    FileWriter printer = new FileWriter(logFile);
    private static int receipt=0;

    public Controller(View view) throws IOException {
        this.view = view;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String table = String.valueOf(view.getTableSelectComboBox().getSelectedItem());
        if(command=="Insert"){

            switch (table){
                case "Client":insertClient();

                    break;
                case "Order":
                    try {
                        insertOrder();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Product":insertProduct();
                    break;

            }
            findAll(table);

        }

        if(command=="Update"){

            switch (table){
                case "Client":updateClient();
                    break;
                case "Order":
                    try {
                        updateOrder();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Product":updateProduct();
                    break;

            }
            findAll(table);

        }

        if(command=="Find"){

            Field[] fields;
            clearTable();
            switch (table){
                case "Client":
                    ClientLogic clientLogic=new ClientLogic();
                    Client client= clientLogic.findClientById(Integer.parseInt( view.getIdToFindTextField().getText()));
                    fields =clientLogic.getClientDAO().getType().getDeclaredFields();

                    changeHeader(fields);
                    view.getResult().setValueAt(""+client.getId(),0,0);
                    view.getResult().setValueAt(client.getName(),0,1);
                    view.getResult().setValueAt(client.getAddress(),0,2);
                    view.getResult().setValueAt(client.getEmail(),0,3);
                    view.getResult().setValueAt(""+client.getContact(),0,4);

                    break;
                case "Order":
                    OrderLogic orderLogic=new OrderLogic();
                    Orders orders = orderLogic.findOrderById(Integer.parseInt( view.getIdToFindTextField().getText()));
                    fields =orderLogic.getOrderDAO().getType().getDeclaredFields();

                    changeHeader(fields);
                    view.getResult().setValueAt( ""+orders.getId(),0,0);
                    view.getResult().setValueAt(orders.getClient(),0,1);
                    view.getResult().setValueAt(orders.getProduct(),0,2);
                    view.getResult().setValueAt(orders.getDate(),0,3);
                    view.getResult().setValueAt(""+orders.getQuantity(),0,4);

                    break;
                case "Product":
                    ProductLogic productLogic=new ProductLogic();
                    Product product = productLogic.findProductById(Integer.parseInt( view.getIdToFindTextField().getText()));
                    fields =productLogic.getProductDAO().getType().getDeclaredFields();

                    changeHeader(fields);
                    view.getResult().setValueAt(""+product.getId(),0,0);
                    view.getResult().setValueAt(product.getName(),0,1);
                    view.getResult().setValueAt(product.getCategory(),0,2);
                    view.getResult().setValueAt(""+product.getPrice(),0,3);
                    view.getResult().setValueAt(""+product.getQuantity(),0,4);
                    break;
            }

        }

        if(command=="FindAll"){
            findAll(table);
        }

        if(command=="Delete"){

            switch (table){
                case "Client":
                    ClientLogic clientLogic= new ClientLogic();
                    Client client= clientLogic.findClientById(Integer.parseInt( view.getIdToDeleteTextField().getText()));
                    clientLogic.getClientDAO().delete(null,Integer.parseInt( view.getIdToDeleteTextField().getText()));
                    break;
                case "Order":
                    OrderLogic orderLogic=new OrderLogic();
                    Orders orders = orderLogic.findOrderById(Integer.parseInt( view.getIdToDeleteTextField().getText()));
                    orderLogic.getOrderDAO().delete(null,Integer.parseInt( view.getIdToDeleteTextField().getText()));
                    break;
                case "Product":
                    ProductLogic productLogic=new ProductLogic();
                    Product product = productLogic.findProductById(Integer.parseInt( view.getIdToDeleteTextField().getText()));
                    productLogic.getProductDAO().delete(null,Integer.parseInt( view.getIdToDeleteTextField().getText()));
                    break;

            }
            findAll(table);

        }
        if(command=="DeleteAll"){

            switch (table){
                case "Client":
                    ClientLogic clientLogic= new ClientLogic();

                    clientLogic.getClientDAO().deleteAll(null);
                    break;
                case "Order":
                    OrderLogic orderLogic=new OrderLogic();

                    orderLogic.getOrderDAO().deleteAll(null);
                    break;
                case "Product":
                    ProductLogic productLogic=new ProductLogic();

                    productLogic.getProductDAO().deleteAll(null);
                    break;

            }
            findAll(table);

        }

    }

    public ArrayList<String> getUpdateParams(){
        ArrayList<String> params = new ArrayList<>();
        params.add(view.getField1UpdateText().getText());
        params.add(view.getField2UpdateText().getText());
        params.add(view.getField3UpdateText().getText());
        if(!view.getField4UpdateText().getText().equals("")) {
            params.add(view.getField4UpdateText().getText());
        }
        return params;
    }

    public void insertClient(){
        ClientLogic clientLogic=new ClientLogic();
        Client clientToInsert = new Client(view.getField1Text().getText(),view.getField2Text().getText(),view.getField3Text().getText(),view.getField4Text().getText());
        clientLogic.validate(clientToInsert);
        clientLogic.getClientDAO().insert(clientToInsert);
    }

    public void updateClient(){
        ClientLogic clientLogic= new ClientLogic();
        Client client= new Client(view.getField1UpdateText().getText(),view.getField2UpdateText().getText(),view.getField3UpdateText().getText(),view.getField4UpdateText().getText());
        clientLogic.validate(client);
        clientLogic.getClientDAO().update(null,getUpdateParams(),Integer.parseInt( view.getIdToUpdateText().getText()));
    }
    public void insertOrder() throws IOException {
        OrderLogic orderLogic=new OrderLogic();
        int ok=0;
        ClientLogic clientLogic=new ClientLogic();
        ArrayList<Client> clients= clientLogic.getClientDAO().findAll();
        for(Client client:clients){
            if(client.getName().equals(view.getField1Text().getText())){
                ok++;
                break;
            }
        }
        int price=0;
        ProductLogic productLogic=new ProductLogic();
        ArrayList<Product> products= productLogic.getProductDAO().findAll();
        for(Product product:products){
            if(product.getName().equals(view.getField2Text().getText())){
                if(product.getQuantity()>Integer.parseInt( view.getField4Text().getText())){
                    ok++;
                    price=product.getPrice();
                    int quantity=product.getQuantity()-Integer.parseInt( view.getField4Text().getText());
                    ArrayList<String> fields=new ArrayList<>();
                    fields.add(product.getName());
                    fields.add(product.getCategory());
                    fields.add(product.getPrice()+"");
                    fields.add(quantity+"");
                    productLogic.getProductDAO().update(null,fields,product.getId());
                    //product.setQuantity(quantity-Integer.parseInt( view.getField4Text().getText()));
                    break;
                }


            }
        }
        if(ok==2) {
            Orders orders = new Orders(view.getField1Text().getText(), view.getField2Text().getText(), view.getField3Text().getText(), Integer.parseInt(view.getField4Text().getText()));
            orderLogic.validate(orders);
            orderLogic.getOrderDAO().insert(orders);
            String filename="Receipt_"+ receipt++ +".txt";
             logFile=new File(filename);
             printer = new FileWriter(logFile);
             printer.write(orders.toString()+"total price="+price*orders.getQuantity());
             printer.close();
        }
        else {
            throw new IllegalArgumentException("This order is not valid");
        }
    }

    public void updateOrder() throws IOException {

        OrderLogic orderLogic=new OrderLogic();
        Orders order=orderLogic.findOrderById(Integer.parseInt( view.getIdToUpdateText().getText()));

        int ok=0;
        ClientLogic clientLogic=new ClientLogic();
        ArrayList<Client> clients= clientLogic.getClientDAO().findAll();
        for(Client client:clients){
            if(client.getName().equals(view.getField1UpdateText().getText())){
                ok++;
                break;
            }
        }
        ProductLogic productLogic=new ProductLogic();
        ArrayList<Product> products= productLogic.getProductDAO().findAll();
        int price=0;
        for(Product product:products){
            if(product.getName().equals(view.getField2UpdateText().getText())){
                int bonus=0;
                if(product.getName().equals(order.getProduct())) bonus=order.getQuantity();
                if(product.getQuantity()+bonus>Integer.parseInt( view.getField4UpdateText().getText())){
                    ok++;
                    price=product.getPrice();
                    int quantity=product.getQuantity()-Integer.parseInt( view.getField4UpdateText().getText());
                    ArrayList<String> fields=new ArrayList<>();
                    fields.add(product.getName());
                    fields.add(product.getCategory());
                    fields.add(product.getPrice()+"");
                    fields.add(quantity+"");
                    productLogic.getProductDAO().update(null,fields,product.getId());
                    product.setQuantity(quantity);

                    break;
                }

            }
        }
        if(ok==2) {
            for(Product product:products){
                if(product.getName().equals(order.getProduct())){
                    int quantity=product.getQuantity()+order.getQuantity();
                    ArrayList<String> fields=new ArrayList<>();
                    fields.add(product.getName());
                    fields.add(product.getCategory());
                    fields.add(product.getPrice()+"");
                    fields.add(quantity+"");
                    productLogic.getProductDAO().update(null,fields,product.getId());



                }
            }
            Orders orders = new Orders(view.getField1UpdateText().getText(), view.getField2UpdateText().getText(), view.getField3UpdateText().getText(), Integer.parseInt(view.getField4Text().getText()));
            orderLogic.validate(orders);
            orderLogic.getOrderDAO().update(null, getUpdateParams(), Integer.parseInt(view.getIdToUpdateText().getText()));
            String filename="Receipt_"+ receipt++ +".txt";
            logFile=new File(filename);
            printer = new FileWriter(logFile);
            printer.write(orders.toString()+"total price="+price*orders.getQuantity());
            printer.close();
        }
        else throw new IllegalArgumentException("This order is not valid");
    }
    public void insertProduct(){
        ProductLogic productLogic=new ProductLogic();
        Product product = new Product(view.getField1Text().getText(),view.getField2Text().getText(),Integer.parseInt( view.getField3Text().getText()),Integer.parseInt( view.getField4Text().getText()));
        productLogic.validate(product);
        productLogic.getProductDAO().insert(product);
    }

    public void updateProduct(){
        ProductLogic productLogic= new ProductLogic();
        Product product= new Product(view.getField1UpdateText().getText(),view.getField2UpdateText().getText(),Integer.parseInt( view.getField3UpdateText().getText()),Integer.parseInt( view.getField4UpdateText().getText()));
        productLogic.validate(product);
        productLogic.getProductDAO().update(null,getUpdateParams(),Integer.parseInt( view.getIdToUpdateText().getText()));
    }



    public void findAll(String table){

        String[][] data = new String[100][];
        String[] columnName;
        Field[] fields;

        switch (table){
            case "Client":
                ClientLogic clientLogic=new ClientLogic();
                ArrayList<Client> clients= clientLogic.getClientDAO().findAll();
                clearTable();
                 fields =clientLogic.getClientDAO().getType().getDeclaredFields();
                 changeHeader(fields);





                for(int i=0;i< clients.size();i++) {


                    view.getResult().setValueAt("" + clients.get(i).getId(),i+1,0);
                    view.getResult().setValueAt(clients.get(i).getName(),i+1,1);
                    view.getResult().setValueAt(clients.get(i).getAddress(),i+1,2);
                    view.getResult().setValueAt(clients.get(i).getEmail(),i+1,3);
                    view.getResult().setValueAt(""+clients.get(i).getContact(),i+1,4);




                }


                break;
            case "Order":
                OrderLogic orderLogic=new OrderLogic();
                ArrayList<Orders> orders= orderLogic.getOrderDAO().findAll();
                clearTable();

                 fields =orderLogic.getOrderDAO().getType().getDeclaredFields();

                changeHeader(fields);

                for(int i=0;i< orders.size();i++) {

                    view.getResult().setValueAt(""+orders.get(i).getId(),i,0);
                    view.getResult().setValueAt(orders.get(i).getClient(),i,1);
                    view.getResult().setValueAt(orders.get(i).getProduct(),i,2);
                    view.getResult().setValueAt(orders.get(i).getDate(),i,3);
                    view.getResult().setValueAt(""+orders.get(i).getQuantity(),i,4);
                }


                break;
            case "Product":
                ProductLogic productLogic=new ProductLogic();
                ArrayList<Product> products = productLogic.getProductDAO().findAll();
                clearTable();

                fields =productLogic.getProductDAO().getType().getDeclaredFields();

                changeHeader(fields);
                for(int i=0;i< products.size();i++) {
                    view.getResult().setValueAt(""+products.get(i).getId(),i,0);
                    view.getResult().setValueAt(products.get(i).getName(),i,1);
                    view.getResult().setValueAt(products.get(i).getCategory(),i,2);
                    view.getResult().setValueAt(""+products.get(i).getPrice(),i,3);
                    view.getResult().setValueAt(""+products.get(i).getQuantity(),i,4);
                }

                break;

            default:
                throw new IllegalStateException("Unexpected value: " + table);
        }



    }
    private void clearTable(){

        for(int i=0;i<100;i++){
            for(int j=0;j<5;j++){
                view.getResult().setValueAt(null,i,j);
            }

        }

    }
    private void changeHeader(Field[] fields){
        JTableHeader th = view.getResult().getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc = tcm.getColumn(0);
        tc.setHeaderValue( fields[0].getName() );
        th.repaint();
        tc = tcm.getColumn(1);
        tc.setHeaderValue( fields[1].getName() );
        th.repaint();
        tc = tcm.getColumn(2);
        tc.setHeaderValue( fields[2].getName() );
        th.repaint();
        tc = tcm.getColumn(3);
        tc.setHeaderValue( fields[3].getName() );
        th.repaint();
        tc = tcm.getColumn(4);
        tc.setHeaderValue( fields[4].getName() );
        th.repaint();


    }

}
