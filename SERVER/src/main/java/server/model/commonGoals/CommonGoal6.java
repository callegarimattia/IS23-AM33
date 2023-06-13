package server.model.commonGoals;

import common.Tile;

public class CommonGoal6 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;

    public boolean isSolved(Tile[][] matrix) {
        int count = 0;
        int different;
        for(int i = 0; i<COL_NUMBER; i++){
            different = 0;
            for(int j = 0; j<ROW_NUMBER-1; j++){
                for(int k=j+1; k<ROW_NUMBER; k++){
                    if(matrix[j][i]==Tile.EMPTY || matrix[j][i] == matrix[k][i]){
                        j = ROW_NUMBER;
                        k = ROW_NUMBER;
                        different = 1;
                    }
                }
            }
           if(different == 0){
               count++;
           }
        }
        return count == 2;
    }
}
