package common;

public class MainBoardCoordinates {
    private int row;
    private int col;

    public MainBoardCoordinates(int row, int col) throws Exception {
        set(row, col);
    }

    /**
     * <p> Set row and column value. Values are checked against the boundaries</p>
     *
     * @param row Row value
     * @param col Column value
     * @throws Exception when either col or row is less than 0 or more than 9
     */
    private void set(int row, int col) throws Exception {
        if (col < 0 || col > 9 || row < 0 || row > 9) throw new Exception();
        this.col = col;
        this.row = row;
    }

    /**
     * @return column value of coordinate
     */
    public int getCol() {
        return col;
    }

    /**
     * @return row value of coordinate
     */

    public int getRow() {
        return row;
    }
}
