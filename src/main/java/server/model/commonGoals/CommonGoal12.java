package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal12 extends CommonGoal{
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int check = 1;
        for(int k =0; k< COL_NUMBER; k++){
            for(int i = 0; i<k; i++){
                if(matrix[i][k]!=Tile.EMPTY){
                    k=COL_NUMBER;
                    check=0;
                }
            }
            if(matrix[k][k]==Tile.EMPTY && k!= COL_NUMBER){
                k=COL_NUMBER;
                check=0;
            }
        }
        if(check == 1) return true;
        check =1;

        for(int k =0; k< COL_NUMBER; k++){
            for(int i = 0; i<k+1; i++){
                if(matrix[i][k]!=Tile.EMPTY){
                    k=COL_NUMBER;
                    check=0;
                }
            }
            if(matrix[k+1][k]==Tile.EMPTY){
                k=COL_NUMBER;
                check=0;
            }
        }
        if(check == 1) return true;
        check =1;

        for(int k=0; k<COL_NUMBER; k++){
            for(int i=0; i<COL_NUMBER-k-1;i++ ){
                if(matrix[i][k]!=Tile.EMPTY){
                    k=COL_NUMBER;
                    check = 0;
                }
                if(matrix[COL_NUMBER-k-1][k]==Tile.EMPTY){
                    k=COL_NUMBER;
                    check = 0;
                }
            }
        }
        if(check == 1) return true;
        check = 1;
        for(int k=0; k<COL_NUMBER; k++){
            for(int i=0; i<COL_NUMBER-k; i++){
                if(matrix[COL_NUMBER-i][k]!=Tile.EMPTY){
                    k=COL_NUMBER;
                    check = 0;
                }
                if(matrix[COL_NUMBER-k][k]!=Tile.EMPTY){
                    k=COL_NUMBER;
                    check = 0;
                }
            }
        }
        if(check == 1) return true;
        return false;
    }
}
