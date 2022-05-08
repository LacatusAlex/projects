package org.example.presentation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * View is the class responsible with the graphical user interface.
 * Here we have all the panel components with fields for input , a table as output, buttons and a combo box for
 * choosing our desired action.
 */

public class View extends JFrame {
    private JPanel  contentPanel;
    private JPanel inputPanel;

    private JPanel insertPanel;
    //private JLabel insertLabel;
    private JLabel field1;
    private  JTextField field1Text;
    private JLabel field2;
    private  JTextField field2Text;
    private JLabel field3;
    private  JTextField field3Text;
    private JLabel field4;
    private  JTextField field4Text;
    private JButton insertButton;

    private JPanel updatePanel;
    private JLabel idToUpdate;
    private JTextField idToUpdateText;
    private JLabel field1Update;
    private  JTextField field1UpdateText;
    private JLabel field2Update;
    private  JTextField field2UpdateText;
    private JLabel field3Update;
    private  JTextField field3UpdateText;
    private JLabel field4Update;
    private  JTextField field4UpdateText;
    private JButton UpdateButton;

    private JPanel deletePanel;
    private JLabel idToDeleteLabel;
    private JTextField idToDeleteTextField;
    private JButton deleteButton;
    private JButton deleteAllButton;

    private JPanel findPanel;
    private JLabel idToFindLabel;
    private JTextField idToFindTextField;
    private JButton findButton;
    private JButton findAllButton;



    private JComboBox tableSelectComboBox;


    private JPanel resultPanel;
    private JLabel resultLabel;
    private JTable result;
    private JScrollPane scrollPanel;


    Controller controller = new Controller(this);

    public View(String name) throws IOException {
        super(name);
        prepareGui();

    }

    public void prepareGui(){
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPanel = new JPanel(new GridLayout(2, 2));
        this.prepareInputPanel();
        this.prepareResultPanel();

        this.setContentPane(this.contentPanel);
    }

    private void prepareResultPanel(){
        this.resultPanel =new JPanel();
        this.resultPanel.setLayout(new GridLayout(2,1));
        this.resultLabel=new JLabel("Result",JLabel.CENTER);
        String[][] data = new String[100][5];

        String[] columnName = {"Id", "Name/Client/Name", "Address/Product/Category", "Email/Date/Price", "Contact/Quantify/Quantity"};
        this.result=new JTable(data,columnName);
        result.setBounds(30,40,1000,1500);
        this.scrollPanel=new JScrollPane(result);

        this.contentPanel.add(scrollPanel);
    }

    private void prepareInputPanel(){
        this.inputPanel= new JPanel();
        this.inputPanel.setLayout(new GridLayout(4,1));



        this.insertPanel = new JPanel();
        this.insertPanel.setLayout(new GridLayout(5,1));
        this.field1 = new JLabel("Name/Client/Name",JLabel.CENTER);
        this.field1Text = new JTextField();
        this.field2 = new JLabel("Address/Product/Category",JLabel.CENTER);
        this.field2Text = new JTextField();
        this.field3 = new JLabel("Email/Date/Price",JLabel.CENTER);
        this.field3Text = new JTextField();
        this.field4 = new JLabel("Contact/Quantity/Quantity",JLabel.CENTER);
        this.field4Text = new JTextField();
        this.insertButton = new JButton("Insert");
        insertButton.setActionCommand("Insert");
        insertButton.addActionListener(controller);
        insertPanel.add(field1);
        insertPanel.add(field1Text);
        insertPanel.add(field2);
        insertPanel.add(field2Text);
        insertPanel.add(field3);
        insertPanel.add(field3Text);
        insertPanel.add(field4);
        insertPanel.add(field4Text);
        insertPanel.add(insertButton);
        inputPanel.add(insertPanel);

        this.updatePanel = new JPanel();
        this.updatePanel.setLayout(new GridLayout(6,1));
        this.idToUpdate= new JLabel("Id",JLabel.CENTER);
        this.idToUpdateText = new JTextField();
        this.field1Update = new JLabel("Name",JLabel.CENTER);
        this.field1UpdateText = new JTextField();
        this.field2Update = new JLabel("Address/Product/Category",JLabel.CENTER);
        this.field2UpdateText = new JTextField();
        this.field3Update = new JLabel("Email/Date/Price",JLabel.CENTER);
        this.field3UpdateText = new JTextField();
        this.field4Update = new JLabel("Contact/Quantity/Quantity",JLabel.CENTER);
        this.field4UpdateText = new JTextField();
        this.UpdateButton = new JButton("Update");
        UpdateButton.setActionCommand("Update");
        UpdateButton.addActionListener(controller);
        updatePanel.add(idToUpdate);
        updatePanel.add(idToUpdateText);
        updatePanel.add(field1Update);
        updatePanel.add(field1UpdateText);
        updatePanel.add(field2Update);
        updatePanel.add(field2UpdateText);
        updatePanel.add(field3Update);
        updatePanel.add(field3UpdateText);
        updatePanel.add(field4Update);
        updatePanel.add(field4UpdateText);
        updatePanel.add(UpdateButton);
        inputPanel.add(updatePanel);

        this.findPanel = new JPanel();
        this.findPanel.setLayout(new GridLayout(2,3));
        this.idToFindLabel = new JLabel("Id",JLabel.CENTER);
        this.idToFindTextField = new JTextField();
        this.findButton= new JButton("Find");
        findButton.setActionCommand("Find");
        findButton.addActionListener(controller);
        this.findAllButton=new JButton("Find all");
        findAllButton.setActionCommand("FindAll");
        findAllButton.addActionListener(controller);
        findPanel.add(idToFindLabel);
        findPanel.add(idToFindTextField);
        findPanel.add(findButton);
        findPanel.add(findAllButton);
        inputPanel.add(findPanel);

        this.deletePanel = new JPanel();
        this.deletePanel.setLayout(new GridLayout(2,3));
        this.idToDeleteLabel = new JLabel("Id",JLabel.CENTER);
        this.idToDeleteTextField = new JTextField();
        this.deleteButton= new JButton("Delete");
        deleteButton.setActionCommand("Delete");
        deleteButton.addActionListener(controller);
        this.deleteAllButton=new JButton("Delete all");
        deleteAllButton.setActionCommand("DeleteAll");
        deleteAllButton.addActionListener(controller);
        deletePanel.add(idToDeleteLabel);
        deletePanel.add(idToDeleteTextField);
        deletePanel.add(deleteButton);
        deletePanel.add(deleteAllButton);
        inputPanel.add(deletePanel);

        String[] tables = new String[]{"Client","Order","Product"};
        this.tableSelectComboBox = new JComboBox(tables);
        this.inputPanel.add(tableSelectComboBox);


        this.contentPanel.add(inputPanel);


    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public void setInputPanel(JPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    public JPanel getInsertPanel() {
        return insertPanel;
    }

    public void setInsertPanel(JPanel insertPanel) {
        this.insertPanel = insertPanel;
    }

    public JLabel getField1() {
        return field1;
    }

    public void setField1(JLabel field1) {
        this.field1 = field1;
    }

    public JTextField getField1Text() {
        return field1Text;
    }

    public void setField1Text(JTextField field1Text) {
        this.field1Text = field1Text;
    }

    public JLabel getField2() {
        return field2;
    }

    public void setField2(JLabel field2) {
        this.field2 = field2;
    }

    public JTextField getField2Text() {
        return field2Text;
    }

    public void setField2Text(JTextField field2Text) {
        this.field2Text = field2Text;
    }

    public JLabel getField3() {
        return field3;
    }

    public void setField3(JLabel field3) {
        this.field3 = field3;
    }

    public JTextField getField3Text() {
        return field3Text;
    }

    public void setField3Text(JTextField field3Text) {
        this.field3Text = field3Text;
    }

    public JLabel getField4() {
        return field4;
    }

    public void setField4(JLabel field4) {
        this.field4 = field4;
    }

    public JTextField getField4Text() {
        return field4Text;
    }

    public void setField4Text(JTextField field4Text) {
        this.field4Text = field4Text;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public void setInsertButton(JButton insertButton) {
        this.insertButton = insertButton;
    }

    public JPanel getUpdatePanel() {
        return updatePanel;
    }

    public void setUpdatePanel(JPanel updatePanel) {
        this.updatePanel = updatePanel;
    }

    public JLabel getIdToUpdate() {
        return idToUpdate;
    }

    public void setIdToUpdate(JLabel idToUpdate) {
        this.idToUpdate = idToUpdate;
    }

    public JTextField getIdToUpdateText() {
        return idToUpdateText;
    }

    public void setIdToUpdateText(JTextField idToUpdateText) {
        this.idToUpdateText = idToUpdateText;
    }

    public JLabel getField1Update() {
        return field1Update;
    }

    public void setField1Update(JLabel field1Update) {
        this.field1Update = field1Update;
    }

    public JTextField getField1UpdateText() {
        return field1UpdateText;
    }

    public void setField1UpdateText(JTextField field1UpdateText) {
        this.field1UpdateText = field1UpdateText;
    }

    public JLabel getField2Update() {
        return field2Update;
    }

    public void setField2Update(JLabel field2Update) {
        this.field2Update = field2Update;
    }

    public JTextField getField2UpdateText() {
        return field2UpdateText;
    }

    public void setField2UpdateText(JTextField field2UpdateText) {
        this.field2UpdateText = field2UpdateText;
    }

    public JLabel getField3Update() {
        return field3Update;
    }

    public void setField3Update(JLabel field3Update) {
        this.field3Update = field3Update;
    }

    public JTextField getField3UpdateText() {
        return field3UpdateText;
    }

    public void setField3UpdateText(JTextField field3UpdateText) {
        this.field3UpdateText = field3UpdateText;
    }

    public JLabel getField4Update() {
        return field4Update;
    }

    public void setField4Update(JLabel field4Update) {
        this.field4Update = field4Update;
    }

    public JTextField getField4UpdateText() {
        return field4UpdateText;
    }

    public void setField4UpdateText(JTextField field4UpdateText) {
        this.field4UpdateText = field4UpdateText;
    }

    public JButton getUpdateButton() {
        return UpdateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        UpdateButton = updateButton;
    }

    public JPanel getDeletePanel() {
        return deletePanel;
    }

    public void setDeletePanel(JPanel deletePanel) {
        this.deletePanel = deletePanel;
    }

    public JLabel getIdToDeleteLabel() {
        return idToDeleteLabel;
    }

    public void setIdToDeleteLabel(JLabel idToDeleteLabel) {
        this.idToDeleteLabel = idToDeleteLabel;
    }

    public JTextField getIdToDeleteTextField() {
        return idToDeleteTextField;
    }

    public void setIdToDeleteTextField(JTextField idToDeleteTextField) {
        this.idToDeleteTextField = idToDeleteTextField;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getDeleteAllButton() {
        return deleteAllButton;
    }

    public void setDeleteAllButton(JButton deleteAllButton) {
        this.deleteAllButton = deleteAllButton;
    }

    public JPanel getFindPanel() {
        return findPanel;
    }

    public void setFindPanel(JPanel findPanel) {
        this.findPanel = findPanel;
    }

    public JLabel getIdToFindLabel() {
        return idToFindLabel;
    }

    public void setIdToFindLabel(JLabel idToFindLabel) {
        this.idToFindLabel = idToFindLabel;
    }

    public JTextField getIdToFindTextField() {
        return idToFindTextField;
    }

    public void setIdToFindTextField(JTextField idToFindTextField) {
        this.idToFindTextField = idToFindTextField;
    }

    public JButton getFindButton() {
        return findButton;
    }

    public void setFindButton(JButton findButton) {
        this.findButton = findButton;
    }

    public JButton getFindAllButton() {
        return findAllButton;
    }

    public void setFindAllButton(JButton findAllButton) {
        this.findAllButton = findAllButton;
    }

    public JComboBox getTableSelectComboBox() {
        return tableSelectComboBox;
    }

    public void setTableSelectComboBox(JComboBox tableSelectComboBox) {
        this.tableSelectComboBox = tableSelectComboBox;
    }

    public JPanel getResultPanel() {
        return resultPanel;
    }

    public void setResultPanel(JPanel resultPanel) {
        this.resultPanel = resultPanel;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    public void setResultLabel(JLabel resultLabel) {
        this.resultLabel = resultLabel;
    }

    public JTable getResult() {
        return result;
    }

    public void setResult(JTable result) {
        this.result = result;
    }

    public JScrollPane getScrollPane() {
        return scrollPanel;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPanel = scrollPane;
    }
}
