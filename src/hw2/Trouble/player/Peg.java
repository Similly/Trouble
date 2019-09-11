package hw2.Trouble.player;

public class Peg {

    private String id;
    private String state;

    public Peg(String id){
        this.id = id;
        this.state = "h";
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
}
