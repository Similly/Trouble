package hw2.Trouble.player;

import hw2.Trouble.board.Finish;
import hw2.Trouble.board.Home;

public class Player {

    private int id;
    private Home home;
    private Finish finish;
    private Peg[] pegs;
    private int startingField;
    private int finishField;

    /*
    * When a player object is created it gets an individual id, home, finish, array of four pegs, starting field and finish field
     */
    public Player(int id){
        this.id = id;

        home = new Home(this);
        finish = new Finish();
        pegs = new Peg[4];
        startingField = (id-1) * 7;
        if (startingField != 0){
            finishField = startingField - 1;
        } else {
            finishField = 27;
        }

        for (int i = 0 ; i < pegs.length ; i++) {
            pegs[i] = new Peg(this.id + "." + i, finishField);
        }
    }

    public int getId() {
        return id;
    }

    public Home getHome() {
        return home;
    }

    public Finish getFinish() {
        return finish;
    }

    public int getStartingField() {
        return startingField;
    }

    public int getFinishField() {
        return finishField;
    }

    public Peg[] getPegs() {
        return pegs;
    }

    public void setPegs(Peg[] pegs) {
        this.pegs = pegs;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setFinish(Finish finish) {
        this.finish = finish;
    }
}
