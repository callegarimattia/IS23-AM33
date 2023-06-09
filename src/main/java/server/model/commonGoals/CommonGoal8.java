package server.model.commonGoals;

import common.Tile;

public class CommonGoal8 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    boolean empty;
    public boolean isSolved(Tile[][] matrix) {
        int count = 0;
        int different;
        for(int i = 0; i<ROW_NUMBER; i++){
            different = 0;
            empty = false;
            for(int j = 0; j<COL_NUMBER-1; j++){
                for(int k=j+1; k<COL_NUMBER; k++){
                    if(matrix[i][j]==Tile.EMPTY || matrix[i][j] == matrix[i][k]){
                        if(matrix[i][j]==Tile.EMPTY) empty = true;
                        j = COL_NUMBER;
                        k = COL_NUMBER;
                        different = 1;
                    }
                }
            }
            if(different == 0 && !empty){
                count++;
            }
        }
        return count == 2;
    }
}
