package hw2.Trouble;

import hw2.Trouble.board.Board;
import hw2.Trouble.board.Field;
import hw2.Trouble.board.Finish;
import hw2.Trouble.board.Home;
import hw2.Trouble.player.Peg;
import hw2.Trouble.player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner in0;

    private static Board board;
    private static Player[] players;
    private static Dice dice;

    private static int amountOfPlayers;
    private static int turn;

    private static boolean gameOver;

    public static void main(String[] args) {

        init();

        turn = 0;
        while (true) {
                turn(players[turn%amountOfPlayers]);
                gameOver = checkForWinner(players[turn%amountOfPlayers]);

                if (gameOver){
                    System.out.println("Congratulations! Player " + players[turn%amountOfPlayers].getId() + " won the game!");
                    break;
                }

                turn++;
        }

    }

    public static void init(){

        System.out.println("+++ Welcome to Trouble! +++\n" +
                "Please enter amount of players:");
        in0 = new Scanner(System.in);
        amountOfPlayers = in0.nextInt();

        board = new Board();
        players = new Player[amountOfPlayers];
        dice = new Dice();

        for (int i = 0 ; i < players.length ; i++) {
            players[i] = new Player(i+1);
        }

        board.printBoard(players);
    }

    public static void turn(Player player){
        System.out.println("It's the turn of Player" + player.getId() + "!\n" +
                "Please enter any Character to roll the dice!");

        String temp = in0.next();
        int rolledNumber = 0;
        Field[] tempFields = board.getFields();
        Peg[] tempPegs = player.getPegs();
        Home tempHome = player.getHome();
        Boolean pegLeftInHome = false;

        if(temp.equals(temp)){
            rolledNumber = dice.rollIt();
            System.out.println("You rolled a " + rolledNumber + "!");
        }

        pegLeftInHome = pegLeftInHome(player);

        if (pegLeftInHome && rolledNumber == 6){
            tryPutNewPegOnBoard(player, tempFields, tempPegs, tempHome, rolledNumber);
        } else {
            tempFields = board.getFields();
            ArrayList<String> tempAvailablePegs = new ArrayList<>();
            int tempInt;
            boolean available = false;

            available = checkIfPlayerHasPegOnBoard(player);

            tempAvailablePegs = getAvailablePegs(player);

            if (available){
                System.out.println("Please choose one of the following Pegs by entering its' number.");
                for (int i = 0 ; i < tempAvailablePegs.size() ; i++){
                    System.out.println(i+1 + ": " + tempAvailablePegs.get(i));
                }

                tempInt = in0.nextInt();
                //System.out.println(tempAvailablePegs.get(tempInt-1) + " " + rolledNumber + " " + tempFields);
                move(tempAvailablePegs.get(tempInt-1),rolledNumber,tempFields);
            }
        }
        board.printBoard(players);

        if (rolledNumber == 6){
            turn(player);
        }
    }

    public static void move(String id, int steps, Field[] tempFields){

        Peg[] tempPegs1;
        Finish tempFinish1;

        Peg[] tempPegs2;
        Home tempHome2;
        String tempId2;

        boolean pegInHome = false;

        for (int i = 0 ; i < board.getFields().length ; i++){
            //System.out.println(id + " " + i + " " + tempFields[i].getPegOnField());

            if(tempFields[i].getPegOnField().equals(id)){
                tempFields[i].setPegOnField("");
                tempFields[i].setEmpty(true);
                if (!tempFields[(i + steps) % tempFields.length].isEmpty()){
                    tempId2 = tempFields[(i + steps) % tempFields.length].getPegOnField();
                    tempPegs2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getPegs();
                    tempHome2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getHome();

                    tempPegs1 = players[Character.getNumericValue(id.charAt(0))-1].getPegs();
                    tempFinish1 = players[Character.getNumericValue(id.charAt(0))-1].getFinish();
                    for (Peg peg : tempPegs1) {
                        if (peg.getId().equals(id)) {
                            peg.setTotalSteps(peg.getTotalSteps() + steps);
                            if (peg.getTotalSteps() >= peg.getSTEPSTOGO()){
                                peg.setState("f");
                                //tempFields[(i + steps) % tempFields.length].setPegOnField("");
                                //tempFields[(i + steps) % tempFields.length].setEmpty(true);

                                tempFinish1.addPeg(id);

                                pegInHome = true;
                            }
                        }
                    }

                    if (!pegInHome){
                        for (Peg peg : tempPegs2) {
                            if (peg.getId().equals(tempId2)) {
                                peg.setState("h");
                                peg.setTotalSteps(0);
                            }
                        }

                        tempHome2.addPeg(tempId2);

                        tempFields[(i + steps) % tempFields.length].setPegOnField(tempId2);
                        tempFields[(i + steps) % tempFields.length].setEmpty(false);
                    }

                    players[Character.getNumericValue(id.charAt(0))-1].setPegs(tempPegs1);
                    players[Character.getNumericValue(id.charAt(0))-1].setFinish(tempFinish1);

                    players[Character.getNumericValue(tempId2.charAt(0))-1].setPegs(tempPegs2);
                    players[Character.getNumericValue(tempId2.charAt(0))-1].setHome(tempHome2);
                } else if (tempFields[(i + steps) % tempFields.length].isEmpty()){
                    tempFields[(i + steps) % tempFields.length].setPegOnField(id);
                    tempFields[(i + steps) % tempFields.length].setEmpty(false);

                    tempPegs1 = players[Character.getNumericValue(id.charAt(0))-1].getPegs();
                    tempFinish1 = players[Character.getNumericValue(id.charAt(0))-1].getFinish();
                    for (Peg peg : tempPegs1) {
                        System.out.println(peg.getId());
                        System.out.println(id);
                        if (peg.getId().equals(id)) {
                            peg.setTotalSteps(peg.getTotalSteps() + steps);
                            System.out.println(peg.getTotalSteps());
                            if (peg.getTotalSteps() >= peg.getSTEPSTOGO()){
                                peg.setState("f");
                                tempFields[(i + steps) % tempFields.length].setPegOnField("");
                                tempFields[(i + steps) % tempFields.length].setEmpty(true);

                                tempFinish1.addPeg(id);
                            }
                        }
                    }
                    players[Character.getNumericValue(id.charAt(0))-1].setPegs(tempPegs1);
                    players[Character.getNumericValue(id.charAt(0))-1].setFinish(tempFinish1);
                }
                //System.out.println(tempFields[(i+steps)%tempFields.length].getPegOnField() + " " + tempFields[(i+steps)%28].isEmpty() + " " + (i+steps)%28);
                break;
            }
        }
        board.setFields(tempFields);
    }

    public static boolean pegLeftInHome(Player player){
        boolean pegLeftInHome = false;
        for (int i = 0 ; i < player.getHome().getPegsIds().length ; i++) {
            if (player.getHome().getPegsIds()[i] != null){
                pegLeftInHome = true;
                break;
            }
        }
        return pegLeftInHome;
    }

    public static void tryPutNewPegOnBoard(Player player, Field[] tempFields, Peg[] tempPegs, Home tempHome, int rolledNumber){
        for (int i = 0 ; i < player.getHome().getPegsIds().length ; i++){
            if (player.getHome().getPegsIds()[i] != null && tempFields[player.getStartingField()].isEmpty()){
                tempFields[player.getStartingField()].setPegOnField(player.getHome().getPegsIds()[i]);
                tempFields[player.getStartingField()].setEmpty(false);

                for (int j = 0 ; j < tempPegs.length ; j++) {
                    if (tempPegs[j].getId().equals(player.getHome().getPegsIds()[i])) {
                        tempPegs[j].setState("r");
                    }
                }

                tempHome.removePeg(i);
                player.setHome(tempHome);
                player.setPegs(tempPegs);
                break;
            } else if(player.getHome().getPegsIds()[i] != null && !tempFields[player.getStartingField()].isEmpty()){
                if(checkForOpponentPegs(player,tempFields[player.getStartingField()])){
                    String tempPegId = tempFields[player.getStartingField()].getPegOnField();
                    Player tempPlayer = players[Character.getNumericValue(tempPegId.charAt(0)) - 1];
                    Peg[] tempPegs1 = tempPlayer.getPegs();
                    Home tempHome1 = tempPlayer.getHome();

                    tempFields[player.getStartingField()].setPegOnField("");
                    tempFields[player.getStartingField()].setEmpty(true);

                    for(int j = 0 ; j < tempPegs1.length ; j++){
                        if (tempPegs1[j].getId().equals(tempPegId)){
                            tempPegs1[j].setState("h");
                            tempPegs1[j].setTotalSteps(0);
                            tempHome1.addPeg(tempPegId);
                        }
                    }

                    tempPlayer.setHome(tempHome1);
                    tempPlayer.setPegs(tempPegs1);
                    players[tempPlayer.getId()-1] = tempPlayer;

                    tempFields[player.getStartingField()].setEmpty(false);
                    tempFields[player.getStartingField()].setPegOnField(player.getHome().getPegsIds()[i]);

                    for (int j = 0 ; j < tempPegs.length ; j++) {
                        if (tempPegs[j].getId().equals(player.getHome().getPegsIds()[i])) {
                            tempPegs[j].setState("r");
                        }
                    }

                    tempHome.removePeg(i);
                    player.setHome(tempHome);
                    player.setPegs(tempPegs);
                    break;
                } else {
                    System.out.println("You starting field is occupied please move the peg on it.\n");
                    int tempInt;
                    tempInt = in0.nextInt();

                    move(tempFields[player.getStartingField()].getPegOnField(), rolledNumber, tempFields);
                    break;
                }
            }
        }
        players[turn%amountOfPlayers] = player;
        board.setFields(tempFields);
    }

    public static boolean checkIfPlayerHasPegOnBoard(Player player){

        for (int i = 0 ; i < player.getPegs().length ; i++){
            if (player.getPegs()[i].getState().equals("r")){
                return true;
            }
        }

        return false;
    }

    public static ArrayList<String> getAvailablePegs(Player player){
        ArrayList<String> tempAvailablePegs = new ArrayList<>();

        for (int i = 0 ; i < player.getPegs().length ; i++){
            if (player.getPegs()[i].getState().equals("r")){
                tempAvailablePegs.add(player.getPegs()[i].getId());
            }
        }

        return tempAvailablePegs;
    }

    public static boolean checkForOpponentPegs(Player player, Field field){
        return Character.getNumericValue(field.getPegOnField().charAt(0)) != player.getId();
    }

    public static boolean checkForWinner(Player player){
        for (int i = 0; i < player.getPegs().length; i++){
            if (!player.getPegs()[i].getState().equals("f")){
                return false;
            }
        }
        return true;
    }

}