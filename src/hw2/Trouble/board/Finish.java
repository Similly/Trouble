package hw2.Trouble.board;

public class Finish {

    private String[] pegsIds;

    public Finish(){
        pegsIds = new String[4];
    }

    /*
    * Adds Peg to the finish section at the index i
     */
    public void addPeg(String id, int index){
        pegsIds[index] = id;
    }

    public String[] getPegsIds() {
        return pegsIds;
    }
}
