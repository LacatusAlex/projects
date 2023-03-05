package GUI;


import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JPanel contentPane;

    private JPanel policyInputPanel;

    private JPanel sizePanel;
    private JLabel capacityLabel;
    JTextField capacity;

    private JLabel blockSizeLabel;
    JTextField blockSize;
    private JPanel policiesPanel;


    private JLabel writePolicyLabel;
    JComboBox writePoliciesComboBox;

    private JLabel updatePolicyLabel;
    JComboBox updatePoliciesComboBox;

    private JLabel associativityLabel;
    JComboBox associativityComboBox;

    private JButton submitButtonPolicies;

    private JPanel inputPanel;

    private JLabel addressLabel;

    JTextField address;
    private JLabel instructionLabel;
    JComboBox loadstoreComboBox;
    JTextField instruction;
    private JButton submitButtonInstruction;

    private JPanel resultPanel;

    private JPanel statistics;
    JLabel loadHitRate;
    JLabel loadMissRate;
    JLabel storeHitRate;
    JLabel storeMissRate;
    JLabel totalHitRate;
    JLabel totalMissRate;

    private JScrollPane cacheTablePanel;
    JTable cacheTable;

    private JScrollPane mainMemoryPanel;

    JTextArea mainMemoryText;


    Controller controller = new Controller(this);

    public View(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(5, 2));
        preparePolicyInputPanels();


        prepareResultPanel();
        prepareMainMemoryPanel();
       // contentPane.add(new JLabel(""));
        //contentPane.add(new JLabel(""));
        this.setContentPane(contentPane);
    }

    private void preparePolicyInputPanels(){
        this.policyInputPanel = new JPanel(new GridLayout(4, 2));


        prepareSizePanel();
        this.policyInputPanel.add(new JLabel(""));
        this.policyInputPanel.add(new JLabel("Policies"));
        this.policyInputPanel.add(new JLabel("Input"));
        preparePoliciesPanel();

        prepareInputPanel();
        this.contentPane.add(policyInputPanel);


    }

    private void prepareSizePanel(){
        this.sizePanel = new JPanel();
        this.sizePanel.setLayout(new GridLayout(2,2));

        this.capacityLabel=new JLabel("Cache capacity:");
        this.capacity=new JTextField();

        this.blockSizeLabel=new JLabel("Block size:");
        this.blockSize=new JTextField();

        this.sizePanel.add(capacityLabel);
        this.sizePanel.add(capacity);
        this.sizePanel.add(blockSizeLabel);
        this.sizePanel.add(blockSize);

        this.policyInputPanel.add(sizePanel);



    }


    private void preparePoliciesPanel() {
        this.policiesPanel = new JPanel();
        this.policiesPanel.setLayout(new GridLayout(2,2));

        this.writePolicyLabel = new JLabel("WritePolicy", JLabel.CENTER);
        String[] items = new String[]{"WriteBack","WriteThrough","WriteAround"};
        this.writePoliciesComboBox = new JComboBox(items);
        this.policiesPanel.add(this.writePolicyLabel);
        this.policiesPanel.add(this.writePoliciesComboBox);

        this.updatePolicyLabel = new JLabel("UpdatePolicy", JLabel.CENTER);
        items = new String[]{"LFU","LRU","MRU"};
        this.updatePoliciesComboBox = new JComboBox(items);
        this.policiesPanel.add(this.updatePolicyLabel);
        this.policiesPanel.add(this.updatePoliciesComboBox);

        this.associativityLabel = new JLabel("Associativity", JLabel.CENTER);
        items = new String[]{"DirectMapped","2-Way","4-Way","FullyAssociative"};
        this.associativityComboBox = new JComboBox(items);
        this.policiesPanel.add(this.associativityLabel);
        this.policiesPanel.add(this.associativityComboBox);

        this.submitButtonPolicies = new JButton("Submit");
        this.submitButtonPolicies.setActionCommand("SUBMITPOLICIES");
        this.submitButtonPolicies.addActionListener(controller);
        this.policiesPanel.add(this.submitButtonPolicies);


        this.policyInputPanel.add(this.policiesPanel);
    }

    private void prepareInputPanel() {
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new GridLayout(3,2));
        this.addressLabel = new JLabel("Address",JLabel.CENTER);
        this.address = new JTextField("0");
        this.instructionLabel = new JLabel("Instruction", JLabel.CENTER);
        this.instruction = new JTextField("0");
        String[] items = new String[]{"Load","Store"};
        this.loadstoreComboBox = new JComboBox(items);
        this.inputPanel.add(this.addressLabel);
        this.inputPanel.add(this.address);
        this.inputPanel.add(this.instructionLabel);
        this.inputPanel.add(this.instruction);
        this.inputPanel.add(this.loadstoreComboBox);



        this.submitButtonInstruction = new JButton("Submit");
        this.submitButtonInstruction.setActionCommand("SUBMITINSTRUCTION");
        this.submitButtonInstruction.addActionListener(controller);
        this.inputPanel.add(this.submitButtonInstruction);

        this.policyInputPanel.add(this.inputPanel);
    }

    private void prepareResultPanel() {
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(new GridLayout(4,2));


        this.statistics = new JPanel();
        this.statistics.setLayout(new GridLayout(2,1));
        this.loadHitRate=new JLabel("Load Hit Rate:");
        this.loadMissRate=new JLabel("Load Miss Rate:");
        this.storeHitRate=new JLabel("Store Hit Rate:");
        this.storeMissRate=new JLabel("Store Miss Rate:");
        this.totalHitRate=new JLabel("Total Hit Rate:");
        this.totalMissRate=new JLabel("Total Miss Rate:");

        this.statistics.add(loadHitRate);
        this.statistics.add(loadMissRate);
        this.statistics.add(storeHitRate);
        this.statistics.add(storeMissRate);
        this.statistics.add(totalHitRate);
        this.statistics.add(totalMissRate);

        this.resultPanel.add(statistics);


        String[][] data = new String[14050][5];

        String[] columnName = {"Index", "Valid", "Dirty", "Tag", "Data"};
        this.cacheTable=new JTable(data,columnName);
        cacheTable.setBounds(30,40,1000,1500);
        this.cacheTablePanel=new JScrollPane(cacheTable);
        cacheTablePanel.setSize(500,500);





        this.contentPane.add(this.resultPanel);
        contentPane.add(cacheTablePanel);
    }
    private void prepareMainMemoryPanel(){


        this.mainMemoryText=new JTextArea();

        this.mainMemoryText.setBounds(30,40,1000,1500);
        this.mainMemoryPanel=new JScrollPane(this.mainMemoryText);
        mainMemoryPanel.setSize(500,500);
        contentPane.add(mainMemoryPanel);

    }


}

