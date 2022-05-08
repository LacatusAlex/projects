package org.example.gui;

import org.example.data.models.Polynomial;
import org.example.logic.Operations;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private View view;

    private Operations operations = new Operations();

    public Controller(View v){
        this.view = v;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command == "COMPUTE"){
            Polynomial p1=Polynomial.getPolynomialFromString(view.getFirstNumberTextField().getText());
            Polynomial p2=Polynomial.getPolynomialFromString(view.getSecondNumberTextField().getText());

            //int firstNumber = Integer.valueOf(view.getFirstNumberTextField().getText());
           // int secondNumber = Integer.valueOf(view.getSecondNumberTextField().getText());

            String operation = String.valueOf(view.getOperationsComboBox().getSelectedItem());
            Polynomial result =new Polynomial();
            Polynomial resultAux = new Polynomial();

            switch(operation){
                case "Add": result = operations.add(p1, p2);
                    break;
                case "Subtract": result = operations.subtract(p1, p2);
                    break;
                case "Multiply": result = operations.multiply(p1, p2);
                    break;
                case "Divide":  ArrayList<Polynomial> resDiv=operations.divide(p1, p2);
                                result = resDiv.get(1);
                                resultAux=resDiv.get(0);
                    break;
                case "Derivative": result = operations.derivative(p1);
                    break;
                case "Integration": result = operations.integration(p1);
                    break;

            }
            view.getResultValueLabel().setText(result.toString());
            view.getResultValueLabel2().setText(resultAux.toString());
        }
    }

}
