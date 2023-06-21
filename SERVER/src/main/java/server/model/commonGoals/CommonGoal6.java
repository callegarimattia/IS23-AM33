package server.model.commonGoals;

import common.Tile;

public class CommonGoal6 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;

    public boolean isSolved(Tile[][] matrix) {
        int count = 0;
        int different;
        boolean empty = false;
        for(int i = 0; i<COL_NUMBER; i++){
            empty = false;
            different = 0;
            if (matrix[i][0] == Tile.EMPTY) empty = true;
            for(int j = 0; j<ROW_NUMBER-1; j++){
                for(int k=j+1; k<ROW_NUMBER; k++){
                    if(matrix[j][i]==Tile.EMPTY || matrix[j][i] == matrix[k][i]){
                        j = ROW_NUMBER;
                        k = ROW_NUMBER;
                        different = 1;
                    }
                }
            }
            if (different == 0 && !empty) {
                count++;
            }
        }
        return count == 2;
    }

    String description = "TBD 6";

    public String getDescription() {
        return description;
    }
}
