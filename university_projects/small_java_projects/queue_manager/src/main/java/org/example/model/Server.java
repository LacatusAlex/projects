package org.example.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server implements Runnable {
    private ArrayBlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int capacity=200;
    private int id;
    private static int nextId=1;
    public Server(){
        tasks=new ArrayBlockingQueue<>(capacity);
        waitingPeriod=new AtomicInteger(0);
        id=nextId++;

    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());

    }
    public void run()  {
        while(true){
            if(!tasks.isEmpty()) {
                Task taskToProcess = tasks.peek();
                try {
                    Thread.sleep(1000 * taskToProcess.getServiceTime());
                } catch (InterruptedException e) {
                    System.out.println("sleep exception");
                }
                tasks.remove();
                waitingPeriod.addAndGet(-taskToProcess.getServiceTime());
            }

        }

    }

    public Task[] getTasks(){
        Task[] tasksToGet=new Task[tasks.size()];

        tasks.toArray(tasksToGet);

        return tasksToGet;
    }

    public void setTasks(ArrayBlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
