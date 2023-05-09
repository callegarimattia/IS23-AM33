package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal11 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int c=0, b=0, g=0, f=0, t=0, p=0;
        for(int i=0; i<ROW_NUMBER; i++){
            for(int j=0; j<COL_NUMBER; j++){
                if(matrix[i][j] == Tile.BOOK){
                    b++;
                }
                if(matrix[i][j] == Tile.CAT){
                    c++;
                }
                if(matrix[i][j] == Tile.GAME){
                    g++;
                }
                if(matrix[i][j] == Tile.FRAME){
                    f++;
                }
                if(matrix[i][j] == Tile.TROPHY){
                    t++;
                }
                if(matrix[i][j] == Tile.PLANT){
                    p++;
                }
            }
        }
        return b==8 || p==8 || g==8 || t==8 || f==8 || c==8;
    }
}
