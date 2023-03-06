package org.example.presentation;

import org.example.business.DeliveryService;
import org.example.model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class LogInGUI extends JFrame implements ActionListener {
    private DeliveryService deliveryService;

    private JPanel contentPanel;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JTextField name;
    private JTextField password;
    private JButton logInButton;
    private int id;

   // private String user;
   // private String password;
    public LogInGUI(String name, DeliveryService deliveryService){
        super(name);
        this.prepareGUI();
        this.deliveryService=deliveryService;

    }

    public void prepareGUI(){

        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(3, 2));
        nameLabel=new JLabel("username",JLabel.CENTER);
        contentPanel.add(nameLabel);
        name=new JTextField();
        contentPanel.add(name);
        passwordLabel=new JLabel("password",JLabel.CENTER);
        contentPanel.add(passwordLabel);
        password=new JTextField();
        contentPanel.add(password);
        logInButton= new JButton("LOGIN");
        logInButton.setActionCommand("LOGIN");
        logInButton.addActionListener(this);
        contentPanel.add(logInButton);

        this.setContentPane(this.contentPanel);


    }

    public void logIn(){



    }
    private static final String ADMINISTRATOR_PATTERN ="[a][d][m][i][n][i][s][t][r][a][t][o][r]";
    private static final String CLIENT_PATTERN = "[c][l][i][e][n][t]\\d+";
    private static final String EMPLOYEE_PATTERN = "[e][m][p][l][o][y][e][e]\\d+";

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if(command.equals("LOGIN")) {
            Pattern pattern1 = Pattern.compile(ADMINISTRATOR_PATTERN);
            Pattern pattern2 = Pattern.compile(CLIENT_PATTERN);
            Pattern pattern3 = Pattern.compile(EMPLOYEE_PATTERN);

            //System.out.println(name.getText());
            if (pattern1.matcher(name.getText()).matches() && password.getText().equals(name.getText())) {
                System.out.println("kk");
                AdministratorGUI administratorGUI = new AdministratorGUI("AdministratorGUI", deliveryService);
                administratorGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                administratorGUI.setVisible(true);
            }


            if (pattern2.matcher(name.getText()).matches()) {
                int clientId= name.getText().charAt(name.getText().length()-1)-'0';
                ClientGUI clientGUI = new ClientGUI("ClientGUI", deliveryService,clientId);
                clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientGUI.setVisible(true);
            }

            if (pattern3.matcher(name.getText()).matches()) {
                EmployeeGUI employeeGUI = new EmployeeGUI("EmployeeGUI", deliveryService);
                employeeGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                employeeGUI.setVisible(true);
            }
        }
    }
}
