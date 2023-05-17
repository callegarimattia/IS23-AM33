package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal1 extends CommonGoal {
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    int count=0;
    @Override
    public boolean isSolved(Tile[][] matrix) {
        for (int i=0;i<ROW_NUMBER-1;i++){
            for(int j=0; j<COL_NUMBER;j++){
                if(matrix[i][j]==matrix[i][j+1]){
                    if(matrix[i][j]!=matrix[i][j-1]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j+2]&&matrix[i][j]!=matrix[i][j+2]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+1][j]){
                        count++;
                    }
                } else if (matrix[i][j]==matrix[i+1][j]) {
                    if(matrix[i][j]!=matrix[i][j-1]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j-1]){
                        count++;
                    }
                }
            }
        }
    return count==6;
    }
}
