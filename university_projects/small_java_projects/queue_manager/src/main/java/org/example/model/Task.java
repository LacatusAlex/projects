package org.example.model;

public class Task implements Comparable<Task>{
    private int arrivalTime;
    private int serviceTime;
    private int id;
    private static int nextId=1;
    private int waitingTime;

    public Task(int arrivalTime, int serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.id=nextId++;
    }
    public Task() {

    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public int compareTo(Task o) {
        if(arrivalTime<o.getArrivalTime())return -1;
        else{
            if(arrivalTime>o.getArrivalTime()) return 1;


        }
        return 0;
    }
    public String toString(){
        return "("+id+","+arrivalTime+","+serviceTime+")";
    }
}
