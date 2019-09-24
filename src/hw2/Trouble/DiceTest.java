package hw2.Trouble;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/*
* I used JUnit 4 for testing.
 */
public class DiceTest {


    /*
    * This test tests the Dice class.
    * It simulates 1000 dice rolls and checks if all of them are between 1 and 6.
     */
    @Test
    public void rollIt() {

        Dice dice = new Dice();
        ArrayList<Integer> randoms = new ArrayList<>();
        boolean works = true;

        for(int i = 0; i < 1000; i++){
            randoms.add(dice.rollIt());
        }

        for (int i = 0; i < randoms.size(); i++){
            if (randoms.get(i) > 6 || randoms.get(i) < 1){
                works = false;
            }
        }

        System.out.println(works);
    }
}