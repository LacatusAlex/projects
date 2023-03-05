package model;

import helper.Converter;

public class Block {

    private int tag;
    private boolean isValid;
    private boolean isDirty;

    private int blockSize;
    private int recentUse; // higher values are more recently used
    private int frequencyUse=0;
    private int[] words;

    /**
     * Class constructor specifing size of block to create in bytes. All blocks
     * are created with this constructor and then modified.
     */
    public Block(int blockSize){
        isValid = false; // doesn't contain valid data yet
        isDirty = false; // doesn't need to be written back
        recentUse = 0; // indicates not used yet
        this.blockSize = blockSize;
        words = new int[blockSize];
    }

    /**
     * Returns the word (32 bytes) of data associated with a particular word
     * address. Both data and address are expressed as (base 10) ints at this
     * level.
     *
     *
     */
    protected int read(int address, int useCounter) {
        frequencyUse++;
        recentUse = useCounter;
        return words[ (address) % (blockSize) ];
    }

    /**
     * Replaces a word of data within the block for the byte address
     * specified.
     *
     *
     */
    public void write(int offset, int data, int useCounter) {
        frequencyUse++;
        recentUse = useCounter;
        words[offset] = data;
        isValid = true;
        isDirty = true;
    }

    /**
     * Fills the block with new data. Should only be used when the block is not
     * dirty and is about to be used for a read or write.

     */
    protected void writeBlock(int tag, int[] words) {
        this.tag = tag;
        this.words = words;
        isValid = true;
        isDirty = false;
    }

    /**
     * Returns the array of all words in the block. Used when the block is dirty
     * and in the least recently used block, so the data can be written back to
     * memory. This allocates new space for other data.
     */
    public int[] readAllData() {
        return words;
    }

    /**
     * Returns the tag field of the address. The containing set uses this to
     * find an exact address.
     */
    public int getTag() {
        return tag;
    }

    /**
     * Returns an int which indicates when the block was last used. Higher
     * numbers indicate more recent use.
     */
    protected int getRecentUse() {
        return recentUse;
    }

    /**
     * Returns an int which indicates how many times the block was used.
     */
    public int getFrequencyUse() {
        return frequencyUse;
    }

    /**
     * Returns true if the data contained in this block is valid or false if it
     * has not been set yet.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Returns true if the data contained in this block has been updated or
     * false if it is still consistent with data in main memory.
     */
    public boolean isDirty() {
        return isDirty;
    }

    /**
     * Returns the score for Adaptive Replacement Method (method which takes
     * in consideration bot least frequently used least recently used

     */
    public int arcScore(){
        return this.getFrequencyUse()+this.getRecentUse();
    }

    /**
     * Prints the block values to standard out.
     */
    @Override
    public String toString() {
        String resultString="" ;
        if(isValid) resultString+="1  ";
        else resultString+="0  ";
        if(isDirty) resultString+="1  ";
        else resultString+="0  ";

        resultString+=Converter.intToHex(tag)+"  ";
        for (int word : words)
            resultString += Converter.intToHex(word) + "  ";
        return resultString;
    }


}
