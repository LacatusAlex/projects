package model;

public class Set {
    int associativity;
    int blockSize;
    int useCounter;
    Block[] blocks;

    public Set(int associativity, int blockSize) {
        this.associativity = associativity;
        this.blockSize = blockSize;
        useCounter = 0;
        blocks = new Block[associativity];

        for(int i=0;i<associativity;i++){
            blocks[i] = new Block(blockSize);
        }
    }

    // returns the relevant block, or null if not found
    public int read(int tag, int offset) {
        Block blockFound = findBlock(tag);
        if ( blockFound != null ) {
            return blockFound.read(offset, ++useCounter);
        } else {
            return 0; // this should not happen
        }
    }

    public void write(int tag, int offset, int data) {
        Block blockFound = findBlock(tag);
        if ( blockFound != null ) {
            blockFound.write(offset, data, ++useCounter);
        } else {
            // this should not happen
        }
    }

    public Block getLRU() {
        int index=0;
        int least=blocks[index].getRecentUse();
        for (int i = 1; i < associativity; i++) {
            if(least>blocks[i].getRecentUse())
            {
                index=i;
                least=blocks[i].getRecentUse();
            }
        }
        Block blockToReturn = blocks[index];
        return blockToReturn;
    }


    public Block getMRU() {
        int index=0;
        int most=blocks[index].getRecentUse();
        for (int i = 1; i < associativity; i++) {
            if(most<blocks[i].getRecentUse())
            {
                index=i;
                most=blocks[i].getRecentUse();
            }
        }
        Block blockToReturn = blocks[index];
        return blockToReturn;
    }


    public Block getLFU() {
        int index=0;
        System.out.println(associativity);
        int least=blocks[index].getFrequencyUse();
        for (int i = 1; i < associativity; i++) {
            if(least>blocks[i].getFrequencyUse())
            {
                index=i;
                least=blocks[i].getFrequencyUse();
            }
        }
        Block blockToReturn = blocks[index];
        return blockToReturn;
    }

    /**
     *
     * Returns the least frequently least recently used block
     *
     */

    public Block getBestForARC() {
        int index=0;
        int least=blocks[0].arcScore();
        for (int i = 1; i < associativity; i++) {
            if(least>blocks[i].arcScore())
            {
                index=i;
                least=blocks[i].arcScore();
            }
        }
        Block blockToReturn = blocks[index];
        return blockToReturn;
    }





    // returns the Block if found, null otherwise
    public Block findBlock(int tag) {

        for(Block block :blocks){
            if(block!=null && block.getTag()==tag && block.isValid())
                return block;
        }
        return null;
    }


    public Block[] getBlocks() {
        return blocks;
    }
}
