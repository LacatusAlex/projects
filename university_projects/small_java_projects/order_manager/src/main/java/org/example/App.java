package org.example;

import org.example.data.access.ClientDAO;
import org.example.data.access.OrderDAO;
import org.example.data.access.ProductDAO;
import org.example.model.Client;
import org.example.model.Orders;
import org.example.model.Product;
import org.example.presentation.View;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Here we have the app class that starts our application
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        JFrame frame = new View("Order management app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);




    }
}
