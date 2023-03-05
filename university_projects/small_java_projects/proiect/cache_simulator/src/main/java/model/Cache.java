package model;

import helper.Policies.UpdatePolicy;
import helper.Policies.WritePolicy;

public class Cache {
    private int capacity;
    private int associativity;
    private int blockSize;
    private int numWrites;
    private int numReads;
    private int numWriteMisses;
    private int numReadMisses;
    private int numEvictions;
    private MainMemory memory; //Memory to which reads and writes will occur on
    private Set[] sets;
   

    /**
     *
     *
     */
    public Cache( int capacity, int associativity, int blockSize, MainMemory memory) {

        // initialize counters
        numWrites = 0;
        numReads = 0;
        numWriteMisses = 0;
        numReadMisses = 0;
        numEvictions = 0;

        // initialize values of the cache
        this.capacity = capacity * 32 / 4;
        this.associativity = associativity;
        this.blockSize = blockSize / 4;
        this.memory = memory;
        sets = new Set[capacity * 32 / (associativity * blockSize)];
        //sets = new Set[ this.capacity / (this.associativity * this.blockSize) ];

        // create the sets that make up this cache
        for (int i = 0; i < sets.length; i++)
            sets[i] = new Set(this.associativity, this.blockSize);
    }

    public int read(int address, UpdatePolicy updatePolicy,WritePolicy writePolicy) {

        // if the block isn't found in the cache, put it there
        if ( !isInMemory(address) ) {
            allocate(address, updatePolicy,writePolicy);
            numReadMisses++;
        }

        // calculate what set the block is in and ask it for the data
        Set set = sets[ ( address / blockSize ) % sets.length ];
        int blockOffset = address % blockSize;
        numReads++;
        return set.read( getTag(address), blockOffset );
    }

    public void write(int address, int data, UpdatePolicy updatePolicy,WritePolicy writePolicy) {

        // if the block isn't found in the cache, put it there
        if ( !isInMemory(address) ) {
            allocate(address, updatePolicy,writePolicy);
            numWriteMisses++;
            if(writePolicy.equals(WritePolicy.WRITE_AROUND))
                return;
        }

        // calculate what set the block is in and ask it for the data
        int index = ( address / blockSize ) % sets.length;
        int blockOffset = address % blockSize;
        Set set = sets[index];

        numWrites++;
        set.write( getTag(address), blockOffset, data );
    }
    
    

    // should only be called if we already know the address is not in cache
    private void allocate(int address, UpdatePolicy updatePolicy, WritePolicy writePolicy) {

        int index = ( address / blockSize ) % sets.length;
        Set set = sets[index];
        Block blockToBeEvicted=null;
        switch (updatePolicy){
            case LRU ->blockToBeEvicted = set.getLRU();
            case LFU ->blockToBeEvicted = set.getLFU();
            case ARC ->blockToBeEvicted = set.getBestForARC();
            case MRU ->blockToBeEvicted = set.getMRU();
        }


        switch(writePolicy){
            case WRITE_BACK -> writeBack(address,blockToBeEvicted,index);
            case WRITE_AROUND -> writeAround(address,blockToBeEvicted,index);
            case WRITE_THROUGH ->writeThrough(address,blockToBeEvicted,index);

        }

    }

    private void writeBack(int address,Block blockToBeEvicted,int index){

        if ( blockToBeEvicted.isDirty() ) {
            writeToMemory(blockToBeEvicted,index);
        }
        // Then write the block with the data we need
        writeBlock(blockToBeEvicted,address);
    }
    private void writeAround(int address,Block blockToBeEvicted,int index){
        writeToMemory(blockToBeEvicted,index);

        // Then write the block with the data we need
        writeBlock(blockToBeEvicted,address);
    }
    private void writeThrough(int address,Block blockToBeEvicted,int index){

        writeToMemory(blockToBeEvicted,index);

        // Then write the block with the data we need
        writeBlock(blockToBeEvicted,address);
    }

    private void writeToMemory(Block block,int index){
        numEvictions++;
        int[] dataEvicted = block.readAllData();
        int addressEvicted = ( block.getTag() * sets.length + index ) * blockSize;
        for (int i = 0; i < blockSize; i++)
            memory.write(addressEvicted + i, dataEvicted[i]);
    }
    private void writeBlock(Block block,int address){
        // Write the block with the data we need
        int[] newData = new int[blockSize];
        int memoryAddress = (address/blockSize)*blockSize;
        for (int i = 0; i < blockSize; i++)
            newData[i] = memory.read(memoryAddress + i);
        block.writeBlock( getTag(address), newData );
    }

    private boolean isInMemory( int address ) {
        int index = ( address / blockSize ) % sets.length;
        return sets[index].findBlock( getTag(address) ) != null;
    }

    private int getTag(int address) {
        //System.out.println((int) ( (double)address / ((double)sets.length * (double)blockSize)));
        //System.out.println("tag:"+( address / (sets.length * blockSize)));
        return ( address / (sets.length * blockSize));
    }


    public int getNumWrites() {
        return numWrites;
    }

    public int getNumReads() {
        return numReads;
    }

    public int getNumWriteMisses() {
        return numWriteMisses;
    }

    public int getNumReadMisses() {
        return numReadMisses;
    }

    public int getNumEvictions() {
        return numEvictions;
    }

    public Set[] getSets() {
        return sets;
    }

    public MainMemory getMemory() {
        return memory;
    }
}
