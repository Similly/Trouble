package hw2.Trouble.board;

import hw2.Trouble.player.Peg;

public class Finish {

    private String[] pegsIds;

    public Finish(){
        pegsIds = new String[4];
    }

    public void removePeg(){

    }

    public void addPeg(String id){
        for (int i = 0 ; i < pegsIds.length ; i++) {
            if(pegsIds[i] == null){
                pegsIds[i] = id;
                break;
            }
        }
    }

    public String[] getPegsIds() {
        return pegsIds;
    }
}
