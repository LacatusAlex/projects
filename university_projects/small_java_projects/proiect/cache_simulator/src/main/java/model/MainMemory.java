package model;

import helper.Converter;

public class MainMemory {
    static int SIZE = 4194304;
    int[] data;

    public MainMemory() {
        data = new int[SIZE];
        for (int i = 0; i < data.length; i++)
            data[i] = i; // data is same as word-address by default
    }

    public int read(int address) {
        if(address>SIZE || address<0)
            throw new RuntimeException("Wrong address");
        return data[address];
    }

    public void write(int address, int value) {
        if(address>SIZE || address<0)
            throw new RuntimeException("Wrong address");
        data[address] = value;
    }


    public String print() {
        String resultedString="";
        resultedString+="MAIN MEMORY: \n" ;
        resultedString+="Address    Words\n";



        for (int i = 0; i < 128; i++) {
            resultedString+= Converter.intToHex(i*8 + 0) + " ";
            for (int j = 0; j < 8; j++)
                resultedString+= Converter.intToHex( data[i*8 + j + 0] ) + " " ;
            resultedString+="\n";
        }

        return resultedString;
    }
}
