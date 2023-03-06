package org.example.model;


import org.example.business.DeliveryService;

import java.util.Observable;
import java.util.Observer;


public class Employee implements Observer {



    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof DeliveryService){
            if(((DeliveryService) o).getEmployeeGUI()!=null) {
                ((DeliveryService) o).getEmployeeGUI().showTable(((DeliveryService) o).getOrders());
            }
        }
    }


}
