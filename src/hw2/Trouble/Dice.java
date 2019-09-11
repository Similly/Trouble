package hw2.Trouble;

import java.util.Random;

public class Dice {

    private Random r;

    public Dice(){
        r = new Random();
    }

    public int rollIt(){
        int temp = r.nextInt(6);
        return temp + 1;
    }
}
