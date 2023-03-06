package org.example.presentation;

import org.example.business.DeliveryService;
import org.example.business.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClientGUI extends JFrame implements ActionListener {

    private DeliveryService deliveryService;
    private JPanel contentPanel;
    private JPanel inputPanel;
    private JLabel c1Label;
    private JCheckBox c1;
    private JLabel c2Label;
    private JCheckBox c2;
    private JLabel c3Label;
    private JCheckBox c3;
    private JLabel c4Label;
    private JCheckBox c4;
    private JLabel stringLabel;
    private JTextField string;
    private JButton searchButton;

    private JLabel orderLabel;
    private JTextField orderedItems;
    private JButton orderButton;

    private JScrollPane outputPanel;
    private JTable table;
    int clientId;
    public ClientGUI(String name, DeliveryService deliveryService,int clientId){
        super(name);
        this.clientId=clientId;
        prepareGUI();
        this.deliveryService=deliveryService;
    }
    private void prepareGUI(){
        this.setSize(600,1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(5, 1));
        prepareInputPanel();
        prepareOutputPanel();
        this.setContentPane(contentPanel);

    }

    public void prepareInputPanel(){
        this.inputPanel =new JPanel();
        this.inputPanel.setLayout(new GridLayout(8,2));
        c1Label=new JLabel("Well rated",JLabel.CENTER);
        c1=new JCheckBox();
        inputPanel.add(c1Label);
        inputPanel.add(c1);

        c2Label=new JLabel("Low on calories",JLabel.CENTER);
        c2=new JCheckBox();
        inputPanel.add(c2Label);
        inputPanel.add(c2);

        c3Label=new JLabel("Low on sodium",JLabel.CENTER);
        c3=new JCheckBox();
        inputPanel.add(c3Label);
        inputPanel.add(c3);

        c4Label=new JLabel("Cheap",JLabel.CENTER);
        c4=new JCheckBox();
        inputPanel.add(c4Label);
        inputPanel.add(c4);

        stringLabel=new JLabel("Keyword",JLabel.CENTER);
        string = new JTextField();
        inputPanel.add(stringLabel);
        inputPanel.add(string);

        searchButton=new JButton("Search");
        searchButton.setActionCommand("SEARCH");
        searchButton.addActionListener(this);
        inputPanel.add(searchButton);

        orderLabel=new JLabel("Items to order:",JLabel.CENTER);
        orderedItems=new JTextField();
        inputPanel.add(orderLabel);
        inputPanel.add(orderedItems);
        orderButton=new JButton("Order");
        orderButton.setActionCommand("ORDER");
        orderButton.addActionListener(this);
        inputPanel.add(orderButton);

        contentPanel.add(inputPanel);





    }

    public void prepareOutputPanel(){
        String[][] data = new String[14050][7];

        String[] columnName = {"Title", "Rating", "Calories", "Protein", "Fat","Sodium","Price"};
        this.table=new JTable(data,columnName);
        table.setBounds(30,40,1000,1500);
        this.outputPanel=new JScrollPane(table);
        outputPanel.setSize(500,500);
        contentPanel.add(outputPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command= e.getActionCommand();
        if(command.equals("SEARCH")){
            boolean[] selects=new boolean[4];
            selects[0]=c1.isSelected();
            selects[1]=c2.isSelected();
            selects[2]=c3.isSelected();
            selects[3]=c4.isSelected();
            String str=string.getText();

            Set<MenuItem> itemSet=deliveryService.select(selects,str);
            showTable(itemSet);

        }
        else{
            if(command.equals("ORDER")){
                Set<MenuItem> itemSet=new HashSet<>();
                ArrayList<String> chosenItems=new ArrayList<>();
                String[] strings= orderedItems.getText().split(",");
                for(String string:strings){
                    chosenItems.add(string);
                }
                for(MenuItem item:deliveryService.getMenuItemSet()){
                    if(chosenItems.contains(item.getTitle())){
                        itemSet.add(item);
                    }
                }
                deliveryService.newOrder(itemSet,clientId);
            }
        }
    }
    private void showTable(){
        int i=0;
        clear();
        for(MenuItem item:deliveryService.getMenuItemSet()){
            table.setValueAt(""+item.getTitle(),i,0);
            table.setValueAt(""+item.getRating(),i,1);
            table.setValueAt(""+item.getCalories(),i,2);
            table.setValueAt(""+item.getProtein(),i,3);
            table.setValueAt(""+item.getFat(),i,4);
            table.setValueAt(""+item.getSodium(),i,5);
            table.setValueAt(""+item.getPrice(),i,6);
            i++;

        }

    }
    private void showTable(Set<MenuItem> menuItemSet){
        int i=0;
        clear();
        for(MenuItem item:menuItemSet){
            table.setValueAt(""+item.getTitle(),i,0);
            table.setValueAt(""+item.getRating(),i,1);
            table.setValueAt(""+item.getCalories(),i,2);
            table.setValueAt(""+item.getProtein(),i,3);
            table.setValueAt(""+item.getFat(),i,4);
            table.setValueAt(""+item.getSodium(),i,5);
            table.setValueAt(""+item.getPrice(),i,6);
            i++;

        }

    }
    public void clear(){

        for(int i=0;i<14050;i++){
            table.setValueAt("",i,0);
            table.setValueAt("",i,1);
            table.setValueAt("",i,2);
            table.setValueAt("",i,3);
            table.setValueAt("",i,4);
            table.setValueAt("",i,5);
            table.setValueAt("",i,6);
        }
    }
}
