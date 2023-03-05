package GUI;




import helper.Policies.UpdatePolicy;
import helper.Policies.WritePolicy;
import model.Block;
import model.Cache;
import model.MainMemory;
import model.Set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Controller implements ActionListener {

    private View view;

    private boolean policiesSubmitted=false;



    public Controller(View v){
        this.view = v;
    }
    private Cache cache;
    private WritePolicy writePolicy;
    private UpdatePolicy updatePolicy;

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command == "SUBMITPOLICIES"){
            if(view.blockSize.getText().equals("")||view.capacity.getText().equals(""))
                return;

            chooseUpdatePolicy((String) view.updatePoliciesComboBox.getSelectedItem());
            chooseWritePolicy((String) view.writePoliciesComboBox.getSelectedItem());

            int associativity=1;
            switch ((String)view.associativityComboBox.getSelectedItem()){
                case "DirectMapped":
                    associativity=1;
                    break;
                case "2-Way":
                    associativity=2;
                    break;
                case "4-Way":
                    associativity=4;
                    break;
                case "FullyAssociative":
                    associativity=8;
                    break;
            }

            cache= new Cache(parseInt(view.capacity.getText()),associativity,parseInt(view.blockSize.getText()),new MainMemory());

            //create the cache
            policiesSubmitted=true;

        }
        if(command == "SUBMITINSTRUCTION"){
            System.out.println(view.loadstoreComboBox.getSelectedItem());
            if(!policiesSubmitted) throw new RuntimeException("noPoliciesRecorded");
            else{
                if(view.loadstoreComboBox.getSelectedItem().equals("Load")) {
                    System.out.println("load");
                    cache.read(parseInt(view.address.getText()), updatePolicy, writePolicy);
                }
                if(view.loadstoreComboBox.getSelectedItem().equals("Store")){
                    System.out.println("store");
                    cache.write(parseInt(view.address.getText()), parseInt(view.instruction.getText()), updatePolicy, writePolicy);
                }


            }

            updateOutput();


        }
    }

    private void chooseWritePolicy(String writePolicy){
        switch (writePolicy){
            case "WriteBack":
                this.writePolicy=WritePolicy.WRITE_BACK;
                break;
            case "WriteThrough":
                this.writePolicy=WritePolicy.WRITE_THROUGH;
                break;
            case "WriteAround":
                this.writePolicy=WritePolicy.WRITE_AROUND;
                break;
        }

    }

    private void chooseUpdatePolicy(String updatePolicy){
        switch (updatePolicy){
            case "LFU":
                this.updatePolicy=UpdatePolicy.LFU;
                break;
            case "LRU":
                this.updatePolicy=UpdatePolicy.LRU;
                break;
            case "ARC":
                this.updatePolicy=UpdatePolicy.ARC;
                break;
            case "MRU":
                this.updatePolicy=UpdatePolicy.MRU;
                break;
        }

    }

    private void updateOutput(){
        view.loadMissRate.setText("Load Misses: " + cache.getNumReadMisses());
        System.out.println(cache.getNumReadMisses());
        System.out.println(cache.getNumWriteMisses());
        System.out.println(cache.getNumReads());
        System.out.println(cache.getNumWrites());
        view.loadHitRate.setText("Load Hits: " + (cache.getNumReads()-cache.getNumReadMisses()));
        view.storeMissRate.setText("Store Misses: "+cache.getNumWriteMisses());
        view.storeHitRate.setText("Store Hits: " + (cache.getNumWrites()-cache.getNumWriteMisses()));
        view.totalMissRate.setText("Total Misses: " + (cache.getNumReadMisses()+cache.getNumWriteMisses()));
        view.totalHitRate.setText("Total Hits: " + ((cache.getNumReads()-cache.getNumReadMisses())+(cache.getNumWrites()-cache.getNumWriteMisses())));
        showTable();




    }

    private void showTable(){
        int i=0;
        clear();
        for(Set set : cache.getSets()){
            int index=0;
            for(Block block : set.getBlocks()) {
                view.cacheTable.setValueAt("" + index, i, 0);
                view.cacheTable.setValueAt("" + block.isValid(), i, 1);
                view.cacheTable.setValueAt("" + block.isDirty(), i, 2);
                view.cacheTable.setValueAt("" + block.getTag(), i, 3);
                view.cacheTable.setValueAt("" + block, i, 4);

                index++;
                i++;
            }

        }
        view.mainMemoryText.setText(cache.getMemory().print());

    }


    public void clear(){

        for(int i=0;i<14050;i++){
            view.cacheTable.setValueAt("",i,0);
            view.cacheTable.setValueAt("",i,1);
            view.cacheTable.setValueAt("",i,2);
            view.cacheTable.setValueAt("",i,3);
            view.cacheTable.setValueAt("",i,4);

        }
    }


}

