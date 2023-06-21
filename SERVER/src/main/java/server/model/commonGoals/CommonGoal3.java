package server.model.commonGoals;

import common.Tile;

public class CommonGoal3 extends CommonGoal{
    //Ritorna true se le caselle ai quattro angoli sono uguali, false altrimenti


    public boolean isSolved(Tile[][] matrix) {
        if(matrix[0][0] == Tile.EMPTY || matrix[5][0] == Tile.EMPTY) return false;
        return matrix[0][0] == matrix[0][4] && matrix[5][0] == matrix[5][4] && matrix[0][0] == matrix[5][4];
    }
    String description = "TBD 3";

    public String getDescription() {
        return description;
    }
}
