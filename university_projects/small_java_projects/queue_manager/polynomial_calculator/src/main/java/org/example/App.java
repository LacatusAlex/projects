package org.example;

import org.example.data.models.MonomialIntCoefficient;
import org.example.data.models.Polynomial;
import org.example.gui.View;
import org.example.logic.Operations;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JFrame frame = new View("Simple polynomial calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);



    }
}
