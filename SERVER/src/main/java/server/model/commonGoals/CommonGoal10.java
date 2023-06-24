package server.model.commonGoals;

import common.Tile;

public class CommonGoal10 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        for (int i = 1; i < COL_NUMBER - 1; i++) {
            for (int j = 1; j < ROW_NUMBER - 1; j++) {
                if (matrix[j][i] == matrix[j + 1][i + 1] && matrix[j][i] == matrix[j - 1][i + 1] && matrix[j][i] == matrix[j + 1][i - 1] && matrix[j][i] == matrix[j - 1][i - 1]) {
                    if (matrix[j][i] != matrix[j][i + 1] && matrix[j][i] != matrix[j + 1][i] && matrix[j][i] != matrix[j - 1][i] && matrix[j][i] != matrix[j][i - 1]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    String description = "5 tiles of the same type forming an X.";

    public String getDescription() {
        return description;
    }
}
