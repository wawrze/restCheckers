package exceptions;

public class CaptureException extends Exception {

    private char row;
    private int col;

    public CaptureException(char row, int col) {
        this.row = row;
        this.col = col;
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
