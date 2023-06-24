package server.model.commonGoals;

import common.Tile;

public class CommonGoal2 extends CommonGoal{
    String description = "5 tiles of the same type forming a diagonal";
    public boolean isSolved(Tile[][] matrix) {
        if(matrix[0][0]==Tile.EMPTY && matrix [0][1] == Tile.EMPTY && matrix[4][0] == Tile.EMPTY && matrix[4][1] == Tile.EMPTY){
            return false;
        }
        if(matrix[0][0]==matrix[1][1]&&matrix[0][0]==matrix[2][2]&&matrix[0][0]==matrix[3][3]&&matrix[0][0]==matrix[4][4]){
            return true;
        }
        if(matrix[1][0]==matrix[2][1]&&matrix[1][0]==matrix[3][2]&&matrix[1][0]==matrix[4][3]&&matrix[5][4]==matrix[1][0]){
            return true;
        }
        if(matrix[4][0]==matrix[3][1]&&matrix[4][0]==matrix[2][2]&&matrix[1][3]==matrix[4][0]&&matrix[0][4]==matrix[4][0]){
            return true;
        }
        if(matrix[4][1]==matrix[3][2]&&matrix[4][1]==matrix[2][3]&&matrix[4][1]==matrix[1][4]&&matrix[4][1]==matrix[5][0]){
            return true;
        }
        return false;
    }

    public String getDescription() {
        return description;
    }
}
