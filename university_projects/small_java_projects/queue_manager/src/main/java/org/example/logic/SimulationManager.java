package org.example.logic;

import org.example.gui.SimulationFrame;
import org.example.model.Server;
import org.example.model.Task;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulationManager implements Runnable{
    public int timeLimit = 60;
    public int maxProcessingTime = 4;
    public int minProcessingTime = 2;
    public int minArrivalTime = 2;
    public int maxArrivalTime = 30;
    public int numberOfServers = 2;
    public int numberOfClients = 10;
    public SelectionPolicy selectionPolicy=SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private ArrayList<Task> generatedTasks;
    private float averageServiceTime =0;
    private int peakTime =0;
    private int peakNrClients=0;
    private float averageWaitingTime=0;

    public SimulationManager() throws IOException {

        frame=new SimulationFrame("Queue simulation app",this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);





    }
    public void initialiseSimulationManager(){
        scheduler=new Scheduler(numberOfServers);
        generatedTasks=new ArrayList<>();
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomTasks();


    }
    private void generateNRandomTasks(){

        Random rndGen= new Random();

        for(int i=0;i<numberOfClients;i++){
            Task newTask= new Task(rndGen.nextInt(minArrivalTime,maxArrivalTime+1),
                    rndGen.nextInt(minProcessingTime,maxProcessingTime+1));
            generatedTasks.add(newTask);
            averageServiceTime+=newTask.getServiceTime();

        }
        averageServiceTime/=numberOfClients;
        Collections.sort(generatedTasks);
    }

    public ArrayList<Task> getGeneratedTasks() {
        return generatedTasks;
    }

    @Override
    public void run() {
        while (true) {
            int currentTime = 0;
            while (!frame.startedRunning) {
                System.out.println("still not running");
            }
            while ((currentTime < timeLimit)  && (!generatedTasks.isEmpty() || !scheduler.isEmpty())){

                System.out.println("running...");
                for (int i = 0; i < generatedTasks.size(); i++) {
                    if (generatedTasks.get(i).getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(generatedTasks.get(i));
                        averageWaitingTime+=generatedTasks.get(i).getWaitingTime();
                        generatedTasks.remove(generatedTasks.get(i));
                        i--;
                    }
                }
                try {
                    frame.update(currentTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(howBusy()>peakNrClients){
                    peakTime=currentTime;
                    peakNrClients=howBusy();
                }
                currentTime++;


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("sleep error");
                }

            }
            averageWaitingTime/=numberOfClients;
            frame.startedRunning = false;
            try {
                frame.clearLog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int howBusy(){

        int busyness=0;
        for(Server server: scheduler.getServers()){
            busyness+=server.getTasks().length;
        }
        return busyness;


    }
    public String getStatistics(){
        String statistics="";
        statistics+="Statistics: \n";
        statistics+="Average waiting time: "+ averageWaitingTime+"\n";
        statistics+="Average service time: "+averageServiceTime+"\n";
        statistics+="Peak time: "+peakTime+ " with "+peakNrClients+ " clients in the queues"+"\n";

        return statistics;


    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
