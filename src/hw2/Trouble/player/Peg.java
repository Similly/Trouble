package hw2.Trouble.player;

public class Peg {

    private String id;
    private String state;
    private int finishField;
    private int totalSteps;
    private final int STEPSTOGO = 27;

    public Peg(String id, int finishField){
        this.id = id;
        this.state = "h";
        this.finishField = finishField;
        this.totalSteps = 0;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTotalSteps(int totalSteps) { this.totalSteps = totalSteps; }

    public int getTotalSteps() { return totalSteps; }

    public int getSTEPSTOGO() {
        return STEPSTOGO;
    }
}
