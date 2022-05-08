package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;

    private Strategy strategy;

    public Scheduler(int maxNoServers) {

        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        servers=new ArrayList<>();

        for(int i=0;i<maxNoServers;i++){
            Server serverToAdd = new Server();
            servers.add(serverToAdd);
            Thread t=new Thread(serverToAdd);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){

        if(policy==SelectionPolicy.SHORTEST_QUEUE){
            strategy=new ShortestQueueStrategy();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME){
            strategy=new TimeStrategy();
        }

    }

    public void dispatchTask(Task t){

        strategy.addTask(servers,t);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
    public boolean isEmpty(){

        boolean isEmpty=true;
        for(Server server:servers){
            if(server.getTasks().length>0){
                isEmpty=false;
                break;
            }
        }
        return  isEmpty;


    }
}
