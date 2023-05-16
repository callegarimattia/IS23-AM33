package server.model.commonGoals;

import server.model.Tile;

public class CommonGoal4 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        Tile type1;
        Tile type2 = Tile.EMPTY;
        Tile type3 = Tile.EMPTY;
        int counter;
        int rowCount=0;

        for(int i=0; i<ROW_NUMBER;i++){
            type1 = matrix[0][i];
            counter = 1;
            for(int j=0; j<COL_NUMBER;j++){
                if(matrix[j][i]!=type1){
                    if(type2 == Tile.EMPTY){
                        type2 = matrix[j][i];
                        counter++;
                    } else if (type2 != Tile.EMPTY) {
                        if(matrix[j][i]!=type2){
                            if(type3 == Tile.EMPTY) {
                                type3 = matrix[j][i];
                                counter++;
                            } else if (type3 != Tile.EMPTY) {
                                if(type3 != matrix[j][i]){
                                    counter = ROW_NUMBER;
                                }
                            }
                        }
                    }
                    if(counter <= 3){
                        rowCount++;
                    }

                }
            }
        }
        return rowCount == 4;
    }
}
