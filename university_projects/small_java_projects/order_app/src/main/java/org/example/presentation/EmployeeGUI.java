package org.example.presentation;

import org.example.business.DeliveryService;
import org.example.business.MenuItem;
import org.example.business.Order;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class EmployeeGUI extends JFrame {
    private JScrollPane outputPanel;
    private JPanel contentPanel;
    private JTable table;
    private  DeliveryService deliveryService=new DeliveryService();
    public EmployeeGUI(String name, DeliveryService deliveryService){
        super(name);
        prepareGUI();
        this.deliveryService=deliveryService;
        deliveryService.setEmployeeGUI(this);
    }
    private void prepareGUI(){
        this.setSize(600,1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(5, 1));
        String[][] data = new String[100][4];

        String[] columnName = {"OrderId", "ClientId", "Date", "Price"};
        this.table=new JTable(data,columnName);
        table.setBounds(30,40,1000,1500);
        this.outputPanel=new JScrollPane(table);
        outputPanel.setSize(500,500);
        contentPanel.add(outputPanel);
        this.setContentPane(contentPanel);

    }
    public void showTable(ArrayList<Order> orders){
        int i=0;
        clear();
        for(Order order:orders){
            table.setValueAt(""+order.getOrderID(),i,0);
            table.setValueAt(""+order.getClientID(),i,1);
            table.setValueAt(""+order.getOrderDate(),i,2);
            table.setValueAt(""+order.getPrice(),i,3);

            i++;

        }

    }
    public void clear(){

        for(int i=0;i<100;i++){
            table.setValueAt("",i,0);
            table.setValueAt("",i,1);
            table.setValueAt("",i,2);
            table.setValueAt("",i,3);

        }
    }
}
