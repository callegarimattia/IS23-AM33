package server.model.commonGoals;

import common.Tile;


public class CommonGoal12 extends CommonGoal{
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int check = 1;
        int i;
        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[i][i] == Tile.EMPTY) check = 0;
            if (i != 0) {
                if (matrix[i - 1][i] != Tile.EMPTY) check = 0;
            }
        }
        if (check == 1) return true;

        check = 1;

        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[i + 1][i] == Tile.EMPTY || matrix[i][i] != Tile.EMPTY) check = 0;
        }
        if (check == 1) return true;

        check = 1;

        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[COL_NUMBER - 1 - i][i] == Tile.EMPTY) check = 0;
            if (i != COL_NUMBER - 1) {
                if (matrix[COL_NUMBER - 2 - i][i] != Tile.EMPTY) check = 0;
            }
        }
        if (check == 1) return true;

        check = 1;
        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[COL_NUMBER - i][i] == Tile.EMPTY || matrix[COL_NUMBER - i - 1][i] != Tile.EMPTY) check = 0;
        }
        return check == 1;
    }

    String description = "5 columns of increasing or decreasing height: starting from the first column on the left or right, each subsequent column must consist of one more tile. The tiles can be of any type.";

    public String getDescription() {
        return description;
    }

}
