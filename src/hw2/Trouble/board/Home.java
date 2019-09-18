package hw2.Trouble.board;

import hw2.Trouble.player.Peg;
import hw2.Trouble.player.Player;

public class Home {

    private String[] pegsIds;

    public Home(Player player){
        pegsIds = new String[4];
        for (int i = 0 ; i < pegsIds.length ; i++) {
            pegsIds[i] = player.getId() + "." + i;
        }
    }

    /*
    * remove the Peg at index i from home
     */
    public void removePeg(int i){
        pegsIds[i] = null;
    }

    /*
    * Adds a Peg at an empty spot in the array
     */
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
