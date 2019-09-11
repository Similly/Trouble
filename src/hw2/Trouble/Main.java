package hw2.Trouble;

import hw2.Trouble.board.Board;
import hw2.Trouble.board.Field;
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

    public static void main(String[] args) {

        init();

        turn = 0;
        while (true) {
                turn(players[turn%amountOfPlayers]);
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

        if(temp.equals(temp)){
            rolledNumber = dice.rollIt();
            System.out.println("You rolled a " + rolledNumber + "!");
        }

        if (rolledNumber == 6){
            for (int i = 0 ; i < player.getHome().getPegsIds().length ; i++){
                if (player.getHome().getPegsIds()[i] != null){
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
                }
            }
            players[turn%amountOfPlayers] = player;
            board.setFields(tempFields);
        } else {
            tempFields = board.getFields();
            ArrayList<String> tempAvailablePegs = new ArrayList<>();
            int tempInt;
            boolean available = false;

            for (int i = 0 ; i < player.getPegs().length ; i++){
                if (player.getPegs()[i].getState().equals("r")){
                    tempAvailablePegs.add(player.getPegs()[i].getId());
                    available = true;
                }
            }

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
    }

    public static void move(String id, int steps, Field[] tempFields){

        Peg[] tempPegs;
        Home tempHome;
        String tempId;
        for (int i = 0 ; i < board.getFields().length ; i++){
            //System.out.println(id + " " + i + " " + tempFields[i].getPegOnField());

            if(tempFields[i].getPegOnField().equals(id)){
                tempFields[i].setPegOnField("");
                tempFields[i].setEmpty(true);
                if (!tempFields[(i + steps) % tempFields.length].isEmpty()){
                    tempId = tempFields[(i + steps) % tempFields.length].getPegOnField();
                    tempPegs = players[Character.getNumericValue(tempId.charAt(0))-1].getPegs();
                    tempHome = players[Character.getNumericValue(tempId.charAt(0))-1].getHome();

                    for (int j = 0 ; j < tempPegs.length ; j++){
                        if (tempPegs[j].getId().equals(tempId)){
                            tempPegs[j].setState("h");
                        }
                    }

                    tempHome.addPeg(tempId);
                    tempFields[(i + steps) % tempFields.length].setPegOnField(id);
                    tempFields[(i + steps) % tempFields.length].setEmpty(false);
                    players[Character.getNumericValue(tempId.charAt(0))-1].setPegs(tempPegs);
                    players[Character.getNumericValue(tempId.charAt(0))-1].setHome(tempHome);
                } else if (tempFields[(i + steps) % tempFields.length].isEmpty()){
                    tempFields[(i + steps) % tempFields.length].setPegOnField(id);
                    tempFields[(i + steps) % tempFields.length].setEmpty(false);
                }
                //System.out.println(tempFields[(i+steps)%tempFields.length].getPegOnField() + " " + tempFields[(i+steps)%28].isEmpty() + " " + (i+steps)%28);
                break;
            }
        }
        board.setFields(tempFields);
    }
}