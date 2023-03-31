package model.GameLogic;

public class Shelf {

    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    private Tile[][] shelf;

    public Shelf() {
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++)
                shelf[ROW][COL] = Tile.EMPTY;
    }

    public boolean isColumnValid(int numTilesToBeInserted, int column) {
        int count = 0;
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            if (shelf[ROW][column] != Tile.EMPTY)
                break;
            count++;
        }
        return count >= numTilesToBeInserted;
    }

    public void insertTiles(int column, Tile tile1, Tile tile2, Tile tile3) {
    }
}
