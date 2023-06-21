package server.model.commonGoals;

import common.Tile;

public class CommonGoal1 extends CommonGoal {
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    int count=0;
    String description = "Two separate groups of 4 tiles of the same type forming a 2x2 square. The tiles in the two groups must be of the same type";
    @Override
    public boolean isSolved(Tile[][] matrix) {
        for (int i=0;i<ROW_NUMBER-1; i++){
            for(int j=0; j<COL_NUMBER-1;j++){
                if(matrix[i][j]==matrix[i][j+1]){
                    if(i==0 && j==0){
                        if(matrix[i][j]!=matrix[i][j+2]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+1][j]){//Controllo per Posizione 0,0 nella matrice
                            count++;
                        }
                    } else if (i == 0 && j != COL_NUMBER - 2){
                        if(matrix[i][j]!=matrix[i][j+2]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+1][j]&&matrix[i][j]!=matrix[i][j-1]){//controllo per tutti quelli che si trovano sulla prima riga
                            count++;
                        }
                    } else if (i!=0&&j==COL_NUMBER-2){
                        if(matrix[i][j]!=matrix[i][j-1]&&matrix[i][j]!=matrix[i+1][j]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i-1][j+1]){
                            count++;
                        }
                    } else if (i==0 && j==COL_NUMBER-2){
                        if(matrix[i][j]!=matrix[i][j-1]&&matrix[i][j]!=matrix[i+1][j]&&matrix[i][j]!=matrix[i+1][j+1]){
                            count++;
                        }
                    } else if (j==0) {
                        if(matrix[i][j]!=matrix[i][j+2]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+1][j]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i-1][j+1]){
                            count++;
                        }
                    } else {
                        if(matrix[i][j]!=matrix[i][j-1]&&matrix[i][j]!=matrix[i+1][j]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i-1][j+1]&&matrix[i][j]!=matrix[i][j+2]){
                            count++;
                        }
                    }
                } else if (matrix[i][j]==matrix[i+1][j]) {
                    if(i==0 && j==0){
                        if(matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+2][j]){//Controllo per Posizione 0,0 nella matrice
                            count++;
                        }
                    } else if (i == 0) {
                        if(matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+2][j]&&matrix[i][j]!=matrix[i+1][j-1]&&matrix[i][j]!=matrix[i][j-1]){
                            count++;
                        }
                    } else if (j==0 && i!=ROW_NUMBER-2) {
                        if(matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i+2][j]&&matrix[i-1][j]!=matrix[i][j]){
                            count++;
                        }
                    } else if (i==ROW_NUMBER-2&& j!=0) {
                        if(matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i+1][j-1]&&matrix[i][j]!=matrix[i][j-1]){
                            count++;
                        }
                    } else if (i==ROW_NUMBER-2 && j==0) {
                        if(matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i-1][j]){
                            count++;
                        }
                    }else{
                        if(matrix[i][j]!=matrix[i][j+1]&&matrix[i][j]!=matrix[i+1][j+1]&&matrix[i][j]!=matrix[i-1][j]&&matrix[i][j]!=matrix[i+1][j-1]&&matrix[i][j]!=matrix[i][j-1]&&matrix[i][j]!=matrix[i+2][j]){
                            count++;
                        }
                    }
                }
            }
        }
        return count==6;
    }

    public String getDescription() {
        return description;
    }
}

