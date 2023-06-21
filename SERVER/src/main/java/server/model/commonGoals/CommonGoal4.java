package server.model.commonGoals;
import common.Tile;

public class CommonGoal4 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        Tile type1 = Tile.EMPTY;
        Tile type2 = Tile.EMPTY;
        Tile type3 = Tile.EMPTY;
        boolean empty;
        int counter;
        int rowCount=0;

        for(int i=0; i<ROW_NUMBER;i++) {
            counter = 0;
            type1 = Tile.EMPTY;
            type2 = Tile.EMPTY;
            type3 = Tile.EMPTY;
            empty = false;
            for (int j = 0; j < COL_NUMBER; j++) {
                if (matrix[i][j] != Tile.EMPTY && type1 == Tile.EMPTY) {
                    type1 = matrix[i][j];
                    counter++;
                } else if (matrix[i][j] != Tile.EMPTY && type1 != Tile.EMPTY) {
                    if (matrix[i][j] != type1) {
                        if (type2 == Tile.EMPTY) {
                            type2 = matrix[i][j];
                            counter++;
                        } else if (type2 != Tile.EMPTY) {
                            if (matrix[i][j] != type2) {
                                if (type3 == Tile.EMPTY) {
                                    type3 = matrix[i][j];
                                    counter++;
                                } else if (type3 != Tile.EMPTY) {
                                    if (type3 != matrix[i][j]) {
                                        counter = ROW_NUMBER;
                                    }
                                }
                            }
                        }
                    }
                }
                if (matrix[i][j] == Tile.EMPTY) empty = true;
            }
            if (counter <= 3 && empty != true) {
                rowCount++;
            }
        }
        return rowCount == 4;
    }

    String description = "TBD 4";

    public String getDescription() {
        return description;
    }
}
