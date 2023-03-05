package org.example;

import GUI.View;
import helper.Converter;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        JFrame frame = new View("CacheSimulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        System.out.println(Integer.toHexString(123 ));
        System.out.println(Converter.intToHex(123));
    }
}
