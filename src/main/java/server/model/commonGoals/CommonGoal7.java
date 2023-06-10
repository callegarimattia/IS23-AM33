package server.model.commonGoals;

import common.Tile;

public class CommonGoal7 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int count=0;
        int c=0, b=0, g=0, f=0, t=0, p=0;
        for (int i=0;i<ROW_NUMBER-1;i++){
            for(int j=0; j<COL_NUMBER-1;j++){
                if(matrix[i][j]==matrix[i+1][j]&&matrix[i][j]==matrix[i][j+1]&&matrix[i][j]==matrix[i+1][j+1]){
                    if(i==0&&j!=0){
                        if(matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i+1][j-1] && matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+2][j+1]&&matrix[i][j]!=matrix[i+1][j+2]&&matrix[i][j]!=matrix[i][j+2]){
                            if(matrix[i][j] == Tile.BOOK)  b++;
                            if(matrix[i][j] == Tile.CAT)   c++;
                            if(matrix[i][j] == Tile.GAME)  g++;
                            if(matrix[i][j] == Tile.FRAME) f++;
                            if(matrix[i][j] == Tile.TROPHY)t++;
                            if(matrix[i][j] == Tile.PLANT) p++;
                        }
                    } else if (i==0 && j==0) {
                        if(matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+2][j+1]&&matrix[i][j]!=matrix[i+1][j+2]&&matrix[i][j]!=matrix[i][j+2]){
                            if(matrix[i][j] == Tile.BOOK)  b++;
                            if(matrix[i][j] == Tile.CAT)   c++;
                            if(matrix[i][j] == Tile.GAME)  g++;
                            if(matrix[i][j] == Tile.FRAME) f++;
                            if(matrix[i][j] == Tile.TROPHY)t++;
                            if(matrix[i][j] == Tile.PLANT) p++;
                        }
                    } else if (j==0&&i!=0) {
                        if(matrix[i][j]!=matrix[i-1][j+1] && matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+2][j+1]&&matrix[i][j]!=matrix[i+1][j+2]&&matrix[i][j]!=matrix[i][j+2]){
                            if(matrix[i][j] == Tile.BOOK)  b++;
                            if(matrix[i][j] == Tile.CAT)   c++;
                            if(matrix[i][j] == Tile.GAME)  g++;
                            if(matrix[i][j] == Tile.FRAME) f++;
                            if(matrix[i][j] == Tile.TROPHY)t++;
                            if(matrix[i][j] == Tile.PLANT) p++;
                        }
                    } else if (i==ROW_NUMBER-2) {
                        if(matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i+1][j-1]&&matrix[i][j]!=matrix[i+1][j+2]&&matrix[i][j]!=matrix[i][j+2]){
                            if(matrix[i][j] == Tile.BOOK)  b++;
                            if(matrix[i][j] == Tile.CAT)   c++;
                            if(matrix[i][j] == Tile.GAME)  g++;
                            if(matrix[i][j] == Tile.FRAME) f++;
                            if(matrix[i][j] == Tile.TROPHY)t++;
                            if(matrix[i][j] == Tile.PLANT) p++;
                        }
                    } else if (j==COL_NUMBER-2) {
                        if(matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i+1][j-1] && matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+2][j+1]){
                            if(matrix[i][j] == Tile.BOOK)  b++;
                            if(matrix[i][j] == Tile.CAT)   c++;
                            if(matrix[i][j] == Tile.GAME)  g++;
                            if(matrix[i][j] == Tile.FRAME) f++;
                            if(matrix[i][j] == Tile.TROPHY)t++;
                            if(matrix[i][j] == Tile.PLANT) p++;
                        }
                    }
                    else{
                        if(matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j-1] && matrix[i][j]!=matrix[i+1][j-1] && matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+2][j+1]&&matrix[i][j]!=matrix[i+1][j+2]&&matrix[i][j]!=matrix[i][j+2]){
                            if(matrix[i][j] == Tile.BOOK)  b++;
                            if(matrix[i][j] == Tile.CAT)   c++;
                            if(matrix[i][j] == Tile.GAME)  g++;
                            if(matrix[i][j] == Tile.FRAME) f++;
                            if(matrix[i][j] == Tile.TROPHY)t++;
                            if(matrix[i][j] == Tile.PLANT) p++;
                        }
                    }
                }
            }
        }
        System.out.println("I VALORI SONO: B = "+b+" C = "+c+" G = "+g+" F = "+f+" T = "+t+" P = "+p);
        return b==2||c==2||g==2||f==2||t==2||p==2;
    }
}
