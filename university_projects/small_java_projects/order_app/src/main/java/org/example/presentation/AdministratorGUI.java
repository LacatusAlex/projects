package org.example.presentation;


import org.example.business.DeliveryService;
import org.example.business.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AdministratorGUI extends JFrame implements ActionListener {

    private JPanel contentPanel;
    private JComboBox select;
    private JComboBox selectReport;
    private JPanel addPanel;
    private JTextField productName;
    private JTextField productRating;
    private JTextField productCalories;
    private JTextField productProtein;
    private JTextField productFat;
    private JTextField productSodium;
    private JTextField productPrice;
    private JButton doOperation;

    private JScrollPane outputPanel;
    private JTextField selectedProducts;
    private JTextField p1;
    private JTextField p2;
    private JTextField date;

    private JTable table;
    private JButton generate;
    private JButton importButton;







    private DeliveryService deliveryService;
    public AdministratorGUI(String name, DeliveryService deliveryService){
        super(name);
        prepareGUI();
        this.deliveryService=deliveryService;
    }

    private void prepareGUI(){
        this.setSize(600,1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(5, 1));
        prepareAddPanel();
        prepareOutputPanel();
        this.setContentPane(contentPanel);
    }
    private void prepareAddPanel(){
        this.addPanel =new JPanel();
        this.addPanel.setLayout(new GridLayout(5,4));

        JLabel nameLabel=new JLabel("title",JLabel.CENTER);
        productName=new JTextField();
        addPanel.add(nameLabel);
        addPanel.add(productName);

        JLabel ratingLabel=new JLabel("rating",JLabel.CENTER);
        productRating=new JTextField();
        addPanel.add(ratingLabel);
        addPanel.add(productRating);

        JLabel caloriesLabel=new JLabel("calories",JLabel.CENTER);
        productCalories=new JTextField();
        addPanel.add(caloriesLabel);
        addPanel.add(productCalories);

        JLabel proteinLabel=new JLabel("protein",JLabel.CENTER);
        productProtein=new JTextField();
        addPanel.add(proteinLabel);
        addPanel.add(productProtein);

        JLabel fatLabel=new JLabel("fat",JLabel.CENTER);
        productFat=new JTextField();
        addPanel.add(fatLabel);
        addPanel.add(productFat);

        JLabel sodiumLabel=new JLabel("sodium",JLabel.CENTER);
        productSodium=new JTextField();
        addPanel.add(sodiumLabel);
        addPanel.add(productSodium);

        JLabel priceLabel=new JLabel("price",JLabel.CENTER);
        productPrice=new JTextField();
        addPanel.add(priceLabel);
        addPanel.add(productPrice);

        JLabel selectItems=new JLabel("selected items",JLabel.CENTER);
        selectedProducts= new JTextField();
        addPanel.add(selectItems);
        addPanel.add(selectedProducts);

        String[] tables = new String[]{"add","delete","modify","create"};
        this.select = new JComboBox(tables);
        addPanel.add(select);
        doOperation=new JButton("Do");
        doOperation.setActionCommand("DO");
        doOperation.addActionListener(this);
        addPanel.add(doOperation);

        String[] tables2 = new String[]{"time interval","products ordered more than","regulars with high order","orders on the date"};
        this.selectReport = new JComboBox(tables2);
        addPanel.add(selectReport);
        JLabel p1Label=new JLabel("parameter1",JLabel.CENTER);
        p1=new JTextField();
        addPanel.add(p1Label);
        addPanel.add(p1);
        JLabel p2Label=new JLabel("parameter2",JLabel.CENTER);
        p2=new JTextField();
        addPanel.add(p2Label);
        addPanel.add(p2);
        JLabel dateLabel=new JLabel("date",JLabel.CENTER);
        date=new JTextField();
        addPanel.add(dateLabel);
        addPanel.add(date);



        generate=new JButton("Generate Report");
        generate.setActionCommand("GENERATE");
        generate.addActionListener(this);
        addPanel.add(generate);
        //contentPanel.add(addPanel);

        importButton=new JButton("Import");
        importButton.setActionCommand("IMPORT");
        importButton.addActionListener(this);
        addPanel.add(importButton);
        contentPanel.add(addPanel);




    }

    private void prepareOutputPanel(){

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

        String command=e.getActionCommand();
        if(command.equals("DO")){
            String[] params=new String[7];
            params[0]=productName.getText();
            params[1]=productRating.getText();
            params[2]=productCalories.getText();
            params[3]=productProtein.getText();
            params[4]=productFat.getText();
            params[5]=productSodium.getText();
            params[6]=productPrice.getText();
            switch (String.valueOf( select.getSelectedItem())){
                case "add":
                    deliveryService.add(params);
                    showTable();
                    break;
                case "delete":
                    deliveryService.delete(params[0]);
                    showTable();
                    break;
                case "modify":
                    deliveryService.modify(params[0],params);
                    showTable();
                    break;
                case "create":
                    ArrayList<String> chosenItems=new ArrayList<>();
                    String[] strings= selectedProducts.getText().split(",");
                    for(String string:strings){
                        chosenItems.add(string);
                    }
                    deliveryService.createMenuItem(params[0],chosenItems);
                    showTable();
                    break;

            }

        }
        else{
            if(command.equals("IMPORT")){
                deliveryService.importProducts();
                showTable();
            }
            else {
                if(command.equals("GENERATE")){
                    System.out.println("ff");
                    int selection=0;
                   // "time interval","products ordered more than","regulars with high order","orders on the date"
                    switch(String.valueOf(selectReport.getSelectedItem())){
                        case "time interval":
                            selection=0;
                            break;
                        case "products ordered more than":
                            selection=1;
                            break;
                        case "regulars with high order":
                            selection=2;
                            break;
                        case "orders on the date":
                            selection=3;
                            break;
                    }

                    int[] params = new int[6];
                    params[0]=Integer.valueOf(p1.getText());
                    params[1]=Integer.valueOf(p2.getText());
                    params[2]=Integer.valueOf(p1.getText());
                    params[3]=Integer.valueOf(p1.getText());
                    params[4]=Integer.valueOf(p2.getText());
                    params[5]=Integer.valueOf(p1.getText());
                    String[] par= date.getText().split("/");
                    LocalDateTime dateTime= LocalDateTime.of(Integer.parseInt(par[0]),Integer.parseInt(par[1]),Integer.parseInt(par[2]),0,0);
                    deliveryService.generateReports(selection,params,dateTime);
                    clear();
                    table.setValueAt("report",0,0);
                    table.setValueAt("created",1,0);


                }
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
