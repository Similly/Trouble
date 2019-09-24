package hw2.Trouble;

import hw2.Trouble.board.Field;
import hw2.Trouble.player.Peg;
import hw2.Trouble.player.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainTest {

    /*
    * This test tests the checkIfPlayerHasPegOnBoard function.
    * The function should return 'true' if a player has a Peg on board and 'false' if the player has no Peg on board.
    * This test calls the function and compares its output with the expected output.
     */
    @Test
    public void checkIfPlayerHasPegOnBoard() {
        boolean works = true;

        Player player = new Player(1);
        Peg pegsPlayer[] = player.getPegs();

        pegsPlayer[1].setState("r");
        player.setPegs(pegsPlayer);

        boolean compare = true;

        boolean isPegOnBoard = Main.checkIfPlayerHasPegOnBoard(player);

        if(!(compare == isPegOnBoard)){
            works = false;
        }

        System.out.println("chekIfPlayerHasPegOnBoard works: " + works);
    }

    /*
    * This test tests the getAvailablePegs function.
    * The function should return the Pegs a player currently has on the board.
    * This test calls this function and compares it with the expected output.
     */
    @Test
    public void getAvailablePegs() {
        boolean works = true;

        Player player = new Player(1);
        Peg pegsPlayer[] = player.getPegs();

        pegsPlayer[1].setState("r");
        pegsPlayer[3].setState("r");

        ArrayList<String> comparePegs = new ArrayList<>();

        comparePegs.add("1.1");
        comparePegs.add("1.3");

        player.setPegs(pegsPlayer);

        ArrayList<String> availablePegs = Main.getAvailablePegs(player);

        for (int i = 0; i < availablePegs.size(); i++) {
            if (!comparePegs.get(i).equals(availablePegs.get(i))){
                works = false;
            }
        }

        System.out.println("getAvailablePegs works: " + works);
    }



    @Test
    public void checkForOpponentPegs() {
        boolean works = true;
        boolean compare = true;

        Player player = new Player(1);
        Field field = new Field();

        field.setEmpty(false);
        field.setPegOnField("2.2");

        boolean opponentPeg = Main.checkForOpponentPegs(player, field);

        if (!(compare == opponentPeg)){
            works = false;
        }

        System.out.println("checkForOpponentPegs works: " + works);
    }

    /*
    *
     */
    @Test
    public void checkForWinner() {
        boolean works = true;
        boolean compare = true;

        Player player = new Player(1);
        Peg pegsPlayer[] = player.getPegs();

        for (int i = 0; i < pegsPlayer.length; i++){
            pegsPlayer[i].setState("f");
        }

        player.setPegs(pegsPlayer);

        boolean winner = Main.checkForWinner(player);

        if (!(winner == compare)){
            works = false;
        }

        System.out.println("checkForWinner works: " + works);
    }
}