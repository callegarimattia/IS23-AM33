package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal9 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    int c, b, g, f, t, p, count;
    int validColumn =0;
    public boolean isSolved(Tile[][] matrix) {
        for(int j=0; j < COL_NUMBER; j++){
            c=0;b=0;g=0;f=0;t=0;p=0;count =0;
            for(int i=0; i< ROW_NUMBER; i++){
                if(matrix[i][j] == Tile.EMPTY) i=ROW_NUMBER;
                if(matrix[i][j] == Tile.BOOK)  b++;
                if(matrix[i][j] == Tile.CAT)   c++;
                if(matrix[i][j] == Tile.GAME)  g++;
                if(matrix[i][j] == Tile.FRAME) f++;
                if(matrix[i][j] == Tile.TROPHY)t++;
                if(matrix[i][j] == Tile.PLANT) p++;
            }
            if(b>0) count++;
            if(p>0) count++;
            if(t>0) count++;
            if(f>0) count++;
            if(g>0) count++;
            if(c>0) count++;
            if(count <= 3) validColumn++;
            if(validColumn == 3) return true;
        }
        return false;
    }
}
