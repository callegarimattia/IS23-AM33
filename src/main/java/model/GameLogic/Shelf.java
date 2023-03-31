package model.GameLogic;

import java.util.ArrayList;
import java.util.List;

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

    public boolean insertTiles(int column, ArrayList<Tile> pickedTiles) {
        int ROW = 0;
        if (isColumnValid(pickedTiles.size(), column)) return false;
        for ( ROW = ROW_NUMBER-1; shelf[ROW][column]==Tile.EMPTY; ROW--);
        for (int x=0; x< pickedTiles.size(); x++){
            shelf[ROW - x][column]=pickedTiles.get(x);
        }
        return true;
    }
}
