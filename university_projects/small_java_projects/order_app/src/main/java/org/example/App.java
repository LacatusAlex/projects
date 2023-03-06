package org.example;

import org.example.business.BaseProduct;
import org.example.business.DeliveryService;
import org.example.presentation.LogInGUI;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {


            List<String> stringList;
            Stream<String> stream = Files.lines(Paths.get("products.csv"));
            stringList = stream.collect(Collectors.toList());
            int i=0;
            stringList.remove(0);

            for(String string:stringList){
                String param[]=string.split(",",7);
                for(String str:param){
                    System.out.println(str+" ");
                }
                System.out.println();
                i++;
                if(i>10) break;
            }
           // System.out.println( stringList.get(0));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        DeliveryService deliveryService = new DeliveryService();
        LogInGUI logInGUI=new LogInGUI("App",deliveryService);
        logInGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInGUI.setVisible(true);
        double re= 2.5;
        System.out.println(re>2);
        Stack s=new Stack();
        Stack s1=new Stack();
        s.push("100");
        s1=s;
        System.out.println(s.peek());
        System.out.println(s1.peek());

    }
}
