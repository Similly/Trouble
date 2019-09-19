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

        /*
        * To run the game, run the following code
         */

        init(false);

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


        /* Test Cases
        * Comment the code to run the game and uncomment the test function you want to print.
        * */

        //testPrintBoard();
        //testInit();
        //testRollTheDice();
        //testTryPutNewPegOnBoard(3);

    }

    /*
    * Initializes the game
    * Asks for the amount of players
    * sets up the initial board and prints it
     */
    public static void init(Boolean testCase){

        if (!testCase){
            System.out.println("+++ Welcome to Trouble! +++\n" +
                    "Please enter amount of players(1-4):");
            in0 = new Scanner(System.in);
            amountOfPlayers = in0.nextInt();
        }else if(testCase){
            amountOfPlayers = 4;
        }

        board = new Board();
        players = new Player[amountOfPlayers];
        dice = new Dice();

        for (int i = 0 ; i < players.length ; i++) {
            players[i] = new Player(i+1);
        }

        board.printBoard(players);
    }

    /*
    * Rolls the dice
    * Decides if the rolled number is a 6 or not
    * If it's a six and the player still has pegs in his Home than the tryPutNewPegOnBoard() function gets called
    * If it's not a six or the player has no pegs left in his home the move() function gets called
     */
    public static void turn(Player player){
        System.out.println("It's the turn of Player" + player.getId() + "!\n" +
                "Please enter any Character to roll the dice!");

        String temp = in0.next();
        int rolledNumber = 0;
        Field[] tempFields = board.getFields();
        Peg[] tempPegs = player.getPegs();
        Home tempHome = player.getHome();
        Boolean pegLeftInHome = false;

        //Rolls the dice
        if(temp.equals(temp)){
            rolledNumber = dice.rollIt();
            System.out.println("You rolled a " + rolledNumber + "!");
        }

        pegLeftInHome = pegLeftInHome(player);

        //Decides if a new peg has to be set on board or not
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

        //If the rolled number was a six the player gets another turn
        if (rolledNumber == 6){
            turn(player);
        }
    }

    /*
    * Moves the selected peg for the rolled number
    * If the destination field is occupied than it removes the peg there and puts it back in its home
    * If the peg completed on round around the board than this function tries to put it in the finish section
     */
    public static void move(String id, int steps, Field[] tempFields){

        Peg[] tempPegs1;
        Finish tempFinish1;

        Peg[] tempPegs2;
        Home tempHome2;
        String tempId2;

        int tempSteps;
        int index;

        boolean pegInFinish = false;

        for (int i = 0 ; i < board.getFields().length ; i++){
            //System.out.println(id + " " + i + " " + tempFields[i].getPegOnField());

            //gets the field the selected peg is on
            if(tempFields[i].getPegOnField().equals(id)){
                //If the next field is not empty the following code is executed
                if (!tempFields[(i + steps) % tempFields.length].isEmpty()){
                    tempFields[i].setPegOnField("");
                    tempFields[i].setEmpty(true);

                    tempPegs1 = players[Character.getNumericValue(id.charAt(0))-1].getPegs();
                    tempFinish1 = players[Character.getNumericValue(id.charAt(0))-1].getFinish();
                    for (Peg peg : tempPegs1) {
                        if (peg.getId().equals(id)) {
                            tempSteps = peg.getTotalSteps() + steps;
                            peg.setTotalSteps(peg.getTotalSteps() + steps);
                            //Checks if the peg can move into the finish section
                            if (tempSteps > peg.getSTEPSTOGO()){
                                peg.setTotalSteps(peg.getSTEPSTOGO());
                                index = tempSteps - peg.getTotalSteps() - 1;

                                if(index < 4){
                                    for (int j = 0; j < players[Character.getNumericValue(id.charAt(0))-1].getFinish().getPegsIds().length; j++){
                                        if(players[Character.getNumericValue(id.charAt(0))-1].getFinish().getPegsIds()[j] == null && j == index){
                                            peg.setState("f");
                                            //tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setPegOnField("");
                                            //tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setEmpty(true);

                                            tempFinish1.addPeg(id,index);
                                            pegInFinish = true;
                                        } else if(players[Character.getNumericValue(id.charAt(0))-1].getFinish().getPegsIds()[j] != null && j == index){
                                            tempId2 = tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].getPegOnField();

                                            if(!tempId2.equals("")){
                                                tempPegs2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getPegs();
                                                tempHome2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getHome();

                                                for (Peg peg1 : tempPegs2) {
                                                    if (peg1.getId().equals(tempId2)) {
                                                        peg1.setState("h");
                                                        peg1.setTotalSteps(0);
                                                    }
                                                }
                                                tempHome2.addPeg(tempId2);

                                                players[Character.getNumericValue(tempId2.charAt(0))-1].setPegs(tempPegs2);
                                                players[Character.getNumericValue(tempId2.charAt(0))-1].setHome(tempHome2);
                                            }

                                            tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setPegOnField(id);
                                            tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setEmpty(false);
                                            pegInFinish = true;
                                        }
                                    }
                                }
                                //tempFields[(i + steps) % tempFields.length].setPegOnField("");
                                //tempFields[(i + steps) % tempFields.length].setEmpty(true);
                            }
                        }
                    }

                    tempId2 = tempFields[(i + steps) % tempFields.length].getPegOnField();
                    tempPegs2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getPegs();
                    tempHome2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getHome();

                    if (!pegInFinish){
                        for (Peg peg : tempPegs2) {
                            if (peg.getId().equals(tempId2)) {
                                peg.setState("h");
                                peg.setTotalSteps(0);
                            }
                        }

                        tempHome2.addPeg(tempId2);

                        tempFields[(i + steps) % tempFields.length].setPegOnField(id);
                        tempFields[(i + steps) % tempFields.length].setEmpty(false);
                    }

                    players[Character.getNumericValue(id.charAt(0))-1].setPegs(tempPegs1);
                    players[Character.getNumericValue(id.charAt(0))-1].setFinish(tempFinish1);

                    players[Character.getNumericValue(tempId2.charAt(0))-1].setPegs(tempPegs2);
                    players[Character.getNumericValue(tempId2.charAt(0))-1].setHome(tempHome2);

                    //If the next field is not empty the following code is executed
                } else if (tempFields[(i + steps) % tempFields.length].isEmpty()){

                    tempPegs1 = players[Character.getNumericValue(id.charAt(0))-1].getPegs();
                    tempFinish1 = players[Character.getNumericValue(id.charAt(0))-1].getFinish();
                    for (Peg peg : tempPegs1) {
                        if (peg.getId().equals(id)) {
                            tempSteps = peg.getTotalSteps() + steps;
                            peg.setTotalSteps(peg.getTotalSteps() + steps);
                            if (tempSteps > peg.getSTEPSTOGO()){
                                peg.setTotalSteps(peg.getSTEPSTOGO());
                                index = tempSteps - peg.getTotalSteps() - 1;
                                //Checks if the peg can move into home
                                if(index < 4){
                                    tempFields[i].setPegOnField("");
                                    tempFields[i].setEmpty(true);

                                    tempFields[(i + steps) % tempFields.length].setPegOnField("");
                                    tempFields[(i + steps) % tempFields.length].setEmpty(true);

                                    for (int j = 0; j < players[Character.getNumericValue(id.charAt(0))-1].getFinish().getPegsIds().length; j++){
                                        if(players[Character.getNumericValue(id.charAt(0))-1].getFinish().getPegsIds()[j] == null && j == index){
                                            peg.setState("f");

                                            tempFinish1.addPeg(id,index);
                                        } else if(players[Character.getNumericValue(id.charAt(0))-1].getFinish().getPegsIds()[j] != null && j == index){
                                            tempId2 = tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].getPegOnField();
                                            if(!tempId2.equals(id)){
                                                tempFields[(i + steps) % tempFields.length].setPegOnField("");
                                                tempFields[(i + steps) % tempFields.length].setEmpty(true);

                                                if (!tempId2.equals("")){
                                                    tempPegs2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getPegs();
                                                    tempHome2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getHome();

                                                    for (Peg peg1 : tempPegs2) {
                                                        if (peg1.getId().equals(tempId2)) {
                                                            peg1.setState("h");
                                                            peg1.setTotalSteps(0);
                                                        }
                                                    }

                                                    tempHome2.addPeg(tempId2);

                                                    players[Character.getNumericValue(tempId2.charAt(0))-1].setPegs(tempPegs2);
                                                    players[Character.getNumericValue(tempId2.charAt(0))-1].setHome(tempHome2);
                                                }

                                                peg.setState("r");
                                                tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setPegOnField(id);
                                                tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setEmpty(false);
                                            }
                                        }
                                    }

                                } else {
                                    tempId2 = tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].getPegOnField();


                                    if(!tempId2.equals("")){
                                        tempPegs2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getPegs();
                                        tempHome2 = players[Character.getNumericValue(tempId2.charAt(0))-1].getHome();
                                        if(!tempId2.equals(id)){
                                            for (Peg peg1 : tempPegs2) {
                                                if (peg1.getId().equals(tempId2)) {
                                                    peg1.setState("h");
                                                    peg1.setTotalSteps(0);
                                                }
                                            }

                                            players[Character.getNumericValue(tempId2.charAt(0))-1].setPegs(tempPegs2);
                                            players[Character.getNumericValue(tempId2.charAt(0))-1].setHome(tempHome2);

                                        }
                                    }

                                    tempFields[i].setPegOnField("");
                                    tempFields[i].setEmpty(true);

                                    peg.setState("r");
                                    tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setPegOnField(id);
                                    tempFields[players[Character.getNumericValue(id.charAt(0))-1].getFinishField()].setEmpty(false);

                                }
                            } else {
                                tempFields[(i + steps) % tempFields.length].setPegOnField(id);
                                tempFields[(i + steps) % tempFields.length].setEmpty(false);

                                tempFields[i].setPegOnField("");
                                tempFields[i].setEmpty(true);
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

    /*
    * Checks if a player still has a Peg left in his home
     */
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

    /*
    * Tries to put a new peg on board if a player still has pegs left in his home
     */
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

    /*
    * Checks if a player has at least one Peg on the board
     */
    public static boolean checkIfPlayerHasPegOnBoard(Player player){

        for (int i = 0 ; i < player.getPegs().length ; i++){
            if (player.getPegs()[i].getState().equals("r")){
                return true;
            }
        }

        return false;
    }

    /*
    * Return an ArrayList of Pegs on the board by a specific player
     */
    public static ArrayList<String> getAvailablePegs(Player player){
        ArrayList<String> tempAvailablePegs = new ArrayList<>();

        for (int i = 0 ; i < player.getPegs().length ; i++){
            if (player.getPegs()[i].getState().equals("r")){
                tempAvailablePegs.add(player.getPegs()[i].getId());
            }
        }

        return tempAvailablePegs;
    }

    /*
    * Checks if field is occupied by an opponents Peg
     */
    public static boolean checkForOpponentPegs(Player player, Field field){
        return Character.getNumericValue(field.getPegOnField().charAt(0)) != player.getId();
    }


    /*
    * Checks if there is a player with all four Pegs in the finish section
     */
    public static boolean checkForWinner(Player player){
        for (int i = 0; i < player.getPegs().length; i++){
            if (!player.getPegs()[i].getState().equals("f")){
                return false;
            }
        }
        return true;
    }



    /*
    * The following functions are only for testing purposes.
    * Just run them inside the main to test the case.
     */


    /*
    * Test if an empty board can be printed.
     */
    public static void testPrintBoard(){
        Board board = new Board();
        board.printBoard();
        System.out.println("Empty board printed successfully!");
    }

    /*
    * Test if a game of four players can be initialized.
     */
    public static void testInit(){
        init(true);
        System.out.println("Game successfully initialized!");
    }

    /*
    * Test if a peg of a player can be put from the Home on the board.
     */
    public static void testTryPutNewPegOnBoard(int player){


        init(true);
        Peg[] tempPegs = players[player-1].getPegs();
        Home tempHome = players[player-1].getHome();
        Field[] tempFields = board.getFields();

        tryPutNewPegOnBoard(players[player-1],tempFields,tempPegs,tempHome,6);
        board.printBoard(players);
    }

    /*
    * Prints n array of 30 random numbers between 1 and 6.
     */
    public static void testRollTheDice(){
        Dice dice = new Dice();
        for (int i = 0; i < 30; i++){
            System.out.print(dice.rollIt() + " ");
        }
    }
}