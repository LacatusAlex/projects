package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;

public class ShortestQueueStrategy implements Strategy {




    @Override
    public void addTask(ArrayList<Server> servers, Task t) {
        int index=-1;
        int min=10000;
        for(int i=0;i< servers.size();i++){
            if(servers.get(i).getTasks().length< min){
                index=i;
                min=servers.get(i).getTasks().length;
            }

        }
        if(index!=-1) {

            t.setWaitingTime(servers.get(index).getWaitingPeriod().get());
            servers.get(index).addTask(t);
        }
        else System.out.println("list size=0");
    }
}
