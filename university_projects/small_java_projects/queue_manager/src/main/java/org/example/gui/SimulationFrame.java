package org.example.gui;

import org.example.logic.SimulationManager;
import org.example.model.Server;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class SimulationFrame  extends JFrame implements ActionListener {

    private final File logFile=new File("logFile.txt");
    FileWriter printer = new FileWriter(logFile);
    private JPanel contentPanel;
    private JTextField NTextField;
    private JTextField QTextField;
    private JTextField tTextField;
    private JTextField clientLowerBoundTextField;
    private JTextField clientUpperBoundTextField;
    private JTextField serviceLowerBoundTextField;
    private JTextField serviceUpperBoundTextField;
    private final SimulationManager manager;
    public boolean startedRunning=false;
    private JTextArea logValueLabel;


    public SimulationFrame(String name,SimulationManager manager) throws IOException {
        super(name);
        this.prepareGui();
        this.manager=manager;
    }

    public void prepareGui(){
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(2, 2));
        this.prepareInputPanel();
        this.prepareLogPanel();

        this.setContentPane(this.contentPanel);
    }

    private void prepareLogPanel() {
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new GridLayout(1,1));
        JLabel logLabel = new JLabel("Log of events", JLabel.CENTER);
        this.logValueLabel = new JTextArea(25,1000);
        logPanel.add(logLabel);
        logPanel.add(this.logValueLabel);

        this.contentPanel.add(logPanel);
    }

    private void prepareInputPanel() {
         JPanel inputPanel;
         JLabel NLabel;
         JLabel QLabel;
         JLabel tLabel;
         JLabel clientLowerBoundLabel;
         JLabel clientUpperBoundLabel;
         JLabel serviceLowerBoundLabel;
         JLabel serviceUpperBoundLabel;

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        NLabel = new JLabel("Number of clients:", JLabel.CENTER);
        inputPanel.add(NLabel);
        this.NTextField=new JTextField();
        inputPanel.add(this.NTextField);

        QLabel = new JLabel("Number of queues:", JLabel.CENTER);
        inputPanel.add(QLabel);
        this.QTextField=new JTextField();
        inputPanel.add(this.QTextField);

        tLabel = new JLabel("Simulation time:", JLabel.CENTER);
        inputPanel.add(tLabel);
        this.tTextField=new JTextField();
        inputPanel.add(this.tTextField);

        clientLowerBoundLabel = new JLabel("Min time of arrival:", JLabel.CENTER);
        inputPanel.add(clientLowerBoundLabel);
        this.clientLowerBoundTextField=new JTextField();
        inputPanel.add(this.clientLowerBoundTextField);

        clientUpperBoundLabel = new JLabel("Max time of arrival:", JLabel.CENTER);
        inputPanel.add(clientUpperBoundLabel);
        this.clientUpperBoundTextField=new JTextField();
        inputPanel.add(this.clientUpperBoundTextField);

        serviceLowerBoundLabel = new JLabel("Min service for a client:", JLabel.CENTER);
        inputPanel.add(serviceLowerBoundLabel);
        this.serviceLowerBoundTextField=new JTextField();
        inputPanel.add(this.serviceLowerBoundTextField);

        serviceUpperBoundLabel = new JLabel("Max service for a client:", JLabel.CENTER);
        inputPanel.add(serviceUpperBoundLabel);
        this.serviceUpperBoundTextField=new JTextField();
        inputPanel.add(this.serviceUpperBoundTextField);


        JButton runButton = new JButton("Run");
        runButton.setActionCommand("RUN");
        runButton.addActionListener(this);
        inputPanel.add(runButton);
        this.contentPanel.add(inputPanel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("RUN")){

            manager.timeLimit=Integer.parseInt( tTextField.getText());
            manager.numberOfClients=Integer.parseInt(NTextField.getText());
            manager.numberOfServers=Integer.parseInt(QTextField.getText());
            manager.minArrivalTime=Integer.parseInt(clientLowerBoundTextField.getText());
            manager.maxArrivalTime=Integer.parseInt(clientUpperBoundTextField.getText());
            manager.minProcessingTime=Integer.parseInt(serviceLowerBoundTextField.getText());
            manager.maxProcessingTime=Integer.parseInt(serviceUpperBoundTextField.getText());
            manager.initialiseSimulationManager();

            if(validateInput()) {
                startedRunning = true;
            }
            else{
                logValueLabel.setText("Invalid input. Please introduce a set of valid data");
            }


        }
    }
    public void update(int time) throws IOException {

            String textToShow = "";
            textToShow+="Time: "+time+"\n ";
            textToShow+="Waiting clients: ";
            for(Task task : manager.getGeneratedTasks())
            {
                textToShow+=task.toString();

            }
            textToShow+="\n";
            for (Server server : manager.getScheduler().getServers()) {
                textToShow += "Queue " + server.getId() + ": ";
                for (Task task : server.getTasks()) {
                    //if(task!=null) {
                        textToShow += task.toString() ;
                    //}
                }
                textToShow+="\n";
            }


        if(startedRunning) {
            logValueLabel.setText(textToShow);
            printer.write(textToShow);


        }





    }
    public void clearLog() throws IOException {

        logValueLabel.setText(manager.getStatistics());
        printer.write(manager.getStatistics());
        printer.close();
    }
    public boolean validateInput(){

        if(manager.timeLimit<=0)return false;
        if(manager.numberOfClients<=0) return false;
        if(manager.numberOfServers<=0) return false;
        if(manager.minProcessingTime>manager.maxProcessingTime) return false;
        if(manager.minArrivalTime>manager.maxArrivalTime) return false;
        if(manager.maxArrivalTime> manager.timeLimit) return false;

        return true;
    }
}
