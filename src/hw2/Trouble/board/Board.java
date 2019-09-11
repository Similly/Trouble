package hw2.Trouble.board;

import hw2.Trouble.player.Peg;
import hw2.Trouble.player.Player;

public class Board {

    private Field[] fields;

    public Board(){
        fields = new Field[28];
        for(int i = 0 ; i < fields.length ; i++){
            fields[i] = new Field();
        }
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Field[] getFields() {
        return fields;
    }

    public void printBoard(Player[] players){
        /*for (int i = 0 ; i < fields.length ; i++) {
            if(fields[i].isEmpty()){
                System.out.print("(   )");
            } else {
                System.out.print("(" + fields[i].getPegOnField() + ")");
            }
        }*/
        for (int i = 14 ; i < 22 ; i++) {
            if (fields[i].isEmpty()) {
                System.out.print("(   )");
            } else {
                System.out.print("(" + fields[i].getPegOnField() + ")");
            }
        }

        for (int i = 0 ; i < 6 ; i++){
            System.out.println();

            if (fields[13 - i].isEmpty()){
                System.out.print("(   )");
            } else {
                System.out.print("(" + fields[13 - i].getPegOnField() + ")");
            }

            System.out.print("                              ");

            if (fields[22 + i].isEmpty()){
                System.out.print("(   )");
            } else {
                System.out.print("(" + fields[22 + i].getPegOnField() + ")");
            }
        }

        System.out.println();
        for (int i = 7 ; i > -1 ; i--) {
            if (fields[i].isEmpty()) {
                System.out.print("(   )");
            } else {
                System.out.print("(" + fields[i].getPegOnField() + ")");
            }
        }

        System.out.println("\n");

        for (int i = 0 ; i < players.length ; i++){
            System.out.print("Player " + (i+1) + ":\n" +
                    "Home: ");

            for (int j = 0 ; j < players[i].getHome().getPegsIds().length ; j++){
                if (players[i].getHome().getPegsIds()[j] != null){
                    System.out.print("(" + players[i].getHome().getPegsIds()[j] + ")");
                } else {
                    System.out.print("(   )");
                }
            }

            System.out.print("\nFinish: ");

            for (int j = 0 ; j < players[i].getFinish().getPegsIds().length ; j++){
                if (players[i].getFinish().getPegsIds()[j] != null){
                    System.out.print("(" + players[i].getFinish().getPegsIds()[j] + ")");
                } else {
                    System.out.print("(   )");
                }
            }
            System.out.println("\n");
        }
    }
}
