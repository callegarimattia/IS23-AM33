package server.model.personalGoals;


import common.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalGoal {
    private Integer[] coordinates;  //  devono essere passati dal json
    private Tile[] values;     //  devono essere passati dal json
    /**
     * @param playerMatrix is the Matrix on which the method will iterate
     * @return the points obtained by the player from this Personal Goal
     */
    public int calcPoints(Tile[][] playerMatrix){
        int collected = 0, k =0;
        for (int i = 0; i < 6; i++){
            if (playerMatrix[coordinates[k]][coordinates[k+1]] == values[i]) collected++;
            k += 2;
        }
        switch (collected) {
            default:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 6;
            case 5:
                return 9;
            case 6:
                return 12;
        }
    }

    public List<Integer> getCoordinatesList() {
        List<Integer> lis =  Arrays.asList(coordinates);
        return lis;
    }

    public List<Integer> getValuesToIntList() {
        List<Integer> intValues = new ArrayList<>();
        for(int i = 0; i < 6; i++)
            intValues.add(values[i].toInt());
        return intValues;
    }
}
