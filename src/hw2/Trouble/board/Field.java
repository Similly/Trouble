package hw2.Trouble.board;

public class Field {

    private boolean empty;
    private String pegOnField;

    public Field(){
        this.empty = true;
        this.pegOnField = " ";
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setPegOnField(String pegOnField) {
        this.pegOnField = pegOnField;
    }

    public String getPegOnField() {
        return pegOnField;
    }
}
