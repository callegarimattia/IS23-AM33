package server.model;

public class MainBoardCoordinates {
    private int row;
    private int col;

    public MainBoardCoordinates(int row, int col) throws Exception {
        set(row, col);
    }

    private void set(int row, int col) throws Exception {
        if (col < 0 || col > 9 || row < 0 || row > 9) throw new Exception();
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
