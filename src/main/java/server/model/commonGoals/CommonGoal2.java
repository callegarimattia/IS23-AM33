package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal2 extends CommonGoal{
    public boolean isSolved(Tile[][] matrix) {
        if(matrix[0][0]==matrix[1][1]&&matrix[0][0]==matrix[2][2]&&matrix[0][0]==matrix[3][3]&&matrix[0][0]==matrix[4][4]){
            return true;
        }
        if(matrix[0][1]==matrix[1][2]&&matrix[0][1]==matrix[2][3]&&matrix[0][1]==matrix[3][4]&&matrix[4][5]==matrix[0][1]){
            return true;
        }
        if(matrix[4][0]==matrix[3][1]&&matrix[4][0]==matrix[2][2]&&matrix[1][3]==matrix[4][0]&&matrix[0][4]==matrix[4][0]){
            return true;
        }
        if(matrix[4][1]==matrix[3][2]&&matrix[4][1]==matrix[2][3]&&matrix[4][1]==matrix[1][4]&&matrix[4][1]==matrix[0][5]){
            return true;
        }
        return false;
    }

}
