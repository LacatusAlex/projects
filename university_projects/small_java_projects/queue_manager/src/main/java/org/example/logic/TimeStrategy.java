package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;

public class TimeStrategy implements Strategy{

    @Override
    public void addTask(ArrayList<Server> servers, Task t) {
            if(t!=null) {
                if(!servers.isEmpty()) {
                    int index = 0;
                    int min = 10000;
                    for (int i = 0; i < servers.size(); i++) {
                        if (servers.get(i).getWaitingPeriod().get() < min) {
                            index = i;
                            min = servers.get(i).getWaitingPeriod().get();
                        }

                    }
                    if (index != -1) {
                        t.setWaitingTime(servers.get(index).getWaitingPeriod().get());
                        servers.get(index).addTask(t);
                    }
                    else System.out.println("list size=0");
                }
            }
    }
}
