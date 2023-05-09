package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal3 extends CommonGoal{
    //Ritorna true se le caselle ai quattro angoli sono uguali, false altrimenti
    public boolean isSolved(Tile[][] matrix) {
        return matrix[0][0] == matrix[0][4] && matrix[5][0] == matrix[5][4] && matrix[0][0] == matrix[5][4];
    }
}
