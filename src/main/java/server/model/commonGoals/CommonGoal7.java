package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal7 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int count=0;
        for (int i=0;i<ROW_NUMBER;i++){
            for(int j=0; j<COL_NUMBER;j++){
                if(matrix[i][j]==matrix[i+1][j]&&matrix[i][j]==matrix[i][j+1]&&matrix[i][j]==matrix[i+1][j+1]){
                    if(matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i+1][j-1] && matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+2][j+1]&&matrix[i][j]!=matrix[i+1][j+2]&&matrix[i][j]!=matrix[i][j+2]){
                        count++;
                    }
                }
            }
        }
        return count == 2;
    }
}
