package hw2.Trouble;

import java.util.Random;

public class Dice {

    private Random r;

    public Dice(){
        r = new Random();
    }

    /*
    * Returns a random number between 1 and 6
     */
    public int rollIt(){
        int temp = r.nextInt(6);
        return temp + 1;
    }
}
